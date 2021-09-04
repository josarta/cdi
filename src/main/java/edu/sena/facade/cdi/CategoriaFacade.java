/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Categoria;
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
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "up_cdi")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public List<Categoria> leertodos() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query qt = em.createQuery("SELECT c FROM Categoria c");
        return qt.getResultList();
    }

    @Override
    public boolean crearCategoria(String cat_nombre, String cat_descripcion) {
        try {
            Query c = em.createNativeQuery("INSERT INTO tbl_categoria (cat_nombre,cat_descripcion) VALUES (?,?)");
            c.setParameter(1, cat_nombre );
            c.setParameter(2, cat_descripcion );
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }    
    } 
    
    
    @Override
    public boolean eliminarCategoria(int cat_id){
        try {
            Query c = em.createQuery("DELETE FROM Categoria c WHERE c.catCategoriaid = :cat_id");
            c.setParameter("cat_id", cat_id);
            c.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    
    }
    
    @Override
    public Categoria validarSiExiste(String nombreIn) {
        try {
            Query q = em.createQuery("SELECT c FROM Categoria c WHERE c.catNombre  LIKE CONCAT('%',:nombreIn,'%')");
            q.setParameter("nombreIn", nombreIn);
            return (Categoria) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
