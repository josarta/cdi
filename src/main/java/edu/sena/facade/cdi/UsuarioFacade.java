/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "up_cdi")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario validarUsuario(String correoIn, String claveIn) {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT u FROM Usuario u WHERE u.usuCorreo = :correoIn AND u.usuClave = :claveIn");
            qt.setParameter("correoIn", correoIn);
            qt.setParameter("claveIn", claveIn);
            return (Usuario) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Usuario recuperarClave(String correoIn) {
        try {
            Query qt = em.createQuery("SELECT u FROM Usuario u WHERE u.usuCorreo = :correoIn");
            qt.setParameter("correoIn", correoIn);
            return (Usuario) qt.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean crearUsuario(Usuario usuIn) {
        try {
            Query q = em.createNativeQuery("INSERT INTO tbl_usuario (usu_tipodocumento, usu_numerodocumento, usu_nombres, usu_apellidos, usu_correo, usu_clave, usu_estado) VALUES (?, ?, ?, ?, ?, ?, '1')");
            q.setParameter(1, usuIn.getUsuTipodocumento());
            q.setParameter(2, usuIn.getUsuNumerodocumento());
            q.setParameter(3, usuIn.getUsuNombres());
            q.setParameter(4, usuIn.getUsuApellidos());
            q.setParameter(5, usuIn.getUsuCorreo());
            q.setParameter(6, usuIn.getUsuClave());
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Usuario> leerTodos() {
        try {
            em.getEntityManagerFactory().getCache().evictAll();
            Query qt = em.createQuery("SELECT u FROM Usuario u");
            return qt.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
