/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Factura;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Josarta
 */
@Stateless
public class FacturaFacade extends AbstractFacade<Factura> implements FacturaFacadeLocal {

    @PersistenceContext(unitName = "up_cdi")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
     @Override
    public boolean crearFactura(int fk_vendedor, int fk_cliente,int fac_totalproductos, double fac_valortotal, double fac_impuestos, String fac_dirrecionentrega, String fac_telefono, String fac_ciudad) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_factura (fk_vendedor, fk_cliente, fac_totalproductos, fac_valortotal, fac_impuestos, fac_dirrecionentrega, fac_telefono, fac_ciudad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            c.setParameter(1, fk_vendedor);
            c.setParameter(2, fk_cliente);
            c.setParameter(3, fac_totalproductos);
            c.setParameter(4, fac_valortotal);
            c.setParameter(5, fac_impuestos);
            c.setParameter(6, fac_dirrecionentrega);
            c.setParameter(7, fac_telefono);
            c.setParameter(8, fac_ciudad);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    

    
}
