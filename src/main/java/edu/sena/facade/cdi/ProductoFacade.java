/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Bodega;
import edu.sena.entity.cdi.Producto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Josarta
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {

    @PersistenceContext(unitName = "up_cdi")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public List<Producto> leertodos() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query qt = em.createQuery("SELECT b FROM Producto b");
        return qt.getResultList();
    }

    @Override
    public Producto leerPorId(int proId){
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT p FROM Producto p WHERE p.pdtProductoid = :id");
            q.setParameter("id", proId);
            return  (Producto) q.getSingleResult();            
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public boolean crearProducto(Producto productoIn, int fk_categoria) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_producto (pdt_nombre, pdt_descripcion, pdt_valorventa, fk_categoria, pdt_total) VALUES (?, ?, ?, ?, ?)");
            c.setParameter(1, productoIn.getPdtNombre());
            c.setParameter(2, productoIn.getPdtDescripcion());
            c.setParameter(3, productoIn.getPdtValorventa());
            c.setParameter(4, fk_categoria);
            c.setParameter(5, productoIn.getPdtTotal());

            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(int bod_id) {
        try {
            Query c = em.createNativeQuery("DELETE FROM tbl_producto WHERE (pdt_productoid = ?)");
            c.setParameter(1, bod_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean ingresarFoto(int fk_productoid, int fk_fotoid) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_producto_has_tbl_foto (fk_productoid,fk_fotoid) VALUES (?,?)");
            c.setParameter(1, fk_productoid);
            c.setParameter(2, fk_fotoid);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removerFoto(int fk_productoid, int fk_fotoid) {
        try {
            Query c = em.createNativeQuery("DELETE FROM tbl_producto_has_tbl_foto  WHERE fk_productoid = ? AND fk_fotoid = ?");
            c.setParameter(1, fk_productoid);
            c.setParameter(2, fk_fotoid);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean actualizarProducto(Producto productoIn, int fk_categoria) {
        try {
            Query c = em.createNativeQuery("UPDATE tbl_producto SET pdt_nombre = ?, pdt_descripcion = ?, pdt_valorventa = ? , fk_categoria = ?, pdt_total = ? WHERE (pdt_productoid = ?)");
            c.setParameter(1, productoIn.getPdtNombre());
            c.setParameter(2, productoIn.getPdtDescripcion());
            c.setParameter(3, productoIn.getPdtValorventa());
            c.setParameter(4, fk_categoria);
            c.setParameter(5, productoIn.getPdtTotal());
            c.setParameter(6, productoIn.getPdtProductoid());
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
