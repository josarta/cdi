/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Bodega;
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
public class BodegaFacade extends AbstractFacade<Bodega> implements BodegaFacadeLocal {

    @PersistenceContext(unitName = "up_cdi")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BodegaFacade() {
        super(Bodega.class);
    }
    
     @Override
    public List<Bodega> leertodos() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query qt = em.createQuery("SELECT b FROM Bodega b");
        return qt.getResultList();
    }

    @Override
    public boolean crearBodega(String bod_nombre, String bod_direccion, String bod_telefono) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_bodega (bdg_nombre,bdg_direccion,bdg_telefono) VALUES (?,?,?)");
            c.setParameter(1, bod_nombre);
            c.setParameter(2, bod_direccion );
            c.setParameter(3, bod_telefono );
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }    
    } 
    
    
    @Override
    public boolean eliminarBodega(int bod_id){
        try {
            Query c = em.createNativeQuery("DELETE FROM tbl_bodega WHERE (bdg_bodegaid = ?)");
            c.setParameter(1, bod_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    
    }
    
    
    
    
}
