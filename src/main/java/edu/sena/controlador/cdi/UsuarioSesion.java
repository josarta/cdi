/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.controlador.cdi;

import edu.sena.entity.cdi.Usuario;
import edu.sena.facade.cdi.UsuarioFacadeLocal;
import edu.sena.utilidades.cdi.Mail;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Josarta
 */
@Named(value = "usuarioSesion")
@SessionScoped
public class UsuarioSesion implements Serializable {

    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;

    private String correoIn;
    private String claveIn;
    private Usuario usuReg = new Usuario();
    private Usuario usuLog = new Usuario();
    private Usuario usuTemporal = new Usuario();

    public UsuarioSesion() {
    }

    @PostConstruct
    public void cargaInicial() {

    }

    public void cerrarSesion() throws IOException {
        usuLog = null;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getExternalContext().invalidateSession();
        fc.getExternalContext().redirect("../index.xhtml");
    }

    public void validarUsuarioSesion() throws IOException {

        if (usuLog == null || usuLog.getUsuCorreo() == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().invalidateSession();
            fc.getExternalContext().redirect("../index.xhtml");
        } 
    }

    public void validarUsuario() {
        try {
            usuLog = usuarioFacadeLocal.validarUsuario(correoIn, claveIn);
            if (usuLog != null) {

                if (usuLog.getUsuEstado() == Short.parseShort("1")) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().redirect("usuario/index.xhtml");
                } else {
                    PrimeFaces.current().executeScript("Swal.fire({"
                            + "  title: 'Usuario !',"
                            + "  text: 'Inactivo ....',"
                            + "  icon: 'info',"
                            + "  confirmButtonText: 'Comuniquese con el administrador !!!!'"
                            + "})");
                }
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Usuario !',"
                        + "  text: 'No encontrado ....',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'valide sus datos !!!!'"
                        + "})");
            }

        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Problemas !',"
                    + "  text: 'No se puede realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        }

    }

    public void recuperarClave() {
        try {
            usuReg = usuarioFacadeLocal.recuperarClave(correoIn);
            if (usuReg != null) {
                
                Mail.recuperarClaves(usuReg.getUsuNombres() + usuReg.getUsuApellidos()  ,usuReg.getUsuCorreo()  , usuReg.getUsuClave() );
              PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Correo de recuperacion  !',"
                        + "  text: 'enviado correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
              
              
              
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Correo electronico !',"
                        + "  text: 'No encontrado ....',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Valide su correo'"
                        + "})");
            }

        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Problemas !',"
                    + "  text: 'No se puede realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        }

    }

    public void crearUsuario() {
        try {
            if (usuarioFacadeLocal.crearUsuario(usuReg)) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Usuario !',"
                        + "  text: 'Creado correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
                usuReg = new Usuario();

            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Problemas !',"
                        + "  text: 'No se puede realizar esta accion',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            }
        } catch (Exception e) {
            System.out.println("UsuarioSesion.crearUsuario() " + e.getMessage());
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Problemas !',"
                    + "  text: 'No se puede realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        }
    }

    public void cambiarEstado(Usuario usuIn) {
        if (usuIn.getUsuEstado() == Short.parseShort("0")) {
            usuIn.setUsuEstado(Short.parseShort("1"));
        } else {
            usuIn.setUsuEstado(Short.parseShort("0"));
        }
        usuarioFacadeLocal.edit(usuIn);
    }

    public void guardarTemporal(Usuario usuIn) {
        usuTemporal = usuIn;
    }

    public void actualizarUsuario() {
        try {
            usuarioFacadeLocal.edit(usuTemporal);
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Usuario !',"
                    + "  text: 'Actualizado correctamente',"
                    + "  icon: 'success',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Problemas !',"
                    + "  text: 'No se puede realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        }

    }

    public List<Usuario> leerTodos() {
        return usuarioFacadeLocal.leerTodos();
    }

    public String getCorreoIn() {
        return correoIn;
    }

    public void setCorreoIn(String correoIn) {
        this.correoIn = correoIn;
    }

    public String getClaveIn() {
        return claveIn;
    }

    public void setClaveIn(String claveIn) {
        this.claveIn = claveIn;
    }

    public Usuario getUsuReg() {
        return usuReg;
    }

    public void setUsuReg(Usuario usuReg) {
        this.usuReg = usuReg;
    }

    public Usuario getUsuLog() {
        return usuLog;
    }

    public void setUsuLog(Usuario usuLog) {
        this.usuLog = usuLog;
    }

    public Usuario getUsuTemporal() {
        return usuTemporal;
    }

    public void setUsuTemporal(Usuario usuTemporal) {
        this.usuTemporal = usuTemporal;
    }

}
