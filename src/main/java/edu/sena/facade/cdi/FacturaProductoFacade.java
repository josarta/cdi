/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.FacturaProducto;
import edu.sena.entity.cdi.Producto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Josarta
 */
@Stateless
public class FacturaProductoFacade extends AbstractFacade<FacturaProducto> implements FacturaProductoFacadeLocal {

    @PersistenceContext(unitName = "up_cdi")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaProductoFacade() {
        super(FacturaProducto.class);
    }

    public boolean addProdutoFactura(int fk_factura, Producto productoIn) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_factura_producto (fk_factura , fk_producto, fpt_valorunidad, fpt_cantidad) VALUES (?, ?, ?, ?)");
            c.setParameter(1, fk_factura);
            c.setParameter(2, productoIn.getPdtProductoid());
            c.setParameter(3, productoIn.getPdtValorventa());
            c.setParameter(4, productoIn.getPdtTotal());

            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
