/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.controlador.cdi;

import edu.sena.entity.cdi.Bodega;
import edu.sena.facade.cdi.BodegaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Josarta
 */
@Named(value = "bodegaView")
@ViewScoped
public class BodegaView implements Serializable {
    
    @EJB
    BodegaFacadeLocal bodegaFacadeLocal;
    private Bodega bog_temporal;
    private Bodega bog_nueva;
    private String bod_nombre;
    private String bod_direccion;
    private String bod_telefono;

    /**
     * Creates a new instance of BodegaView
     */
    public BodegaView() {
    }
    
    public List<Bodega> leertodos() {
        return bodegaFacadeLocal.leertodos();
    }
    
    public void crearBodega() {
        try {
            boolean salida = bodegaFacadeLocal.crearBodega(bod_nombre, bod_direccion, bod_telefono);
            if (salida) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Bodega !',"
                        + "  text: 'Creada correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Problemas !',"
                        + "  text: 'No se puede realizar esta accion',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
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
    
    public void eliminarBodega(Bodega bodega_In) {
        try {
            if (bodegaFacadeLocal.eliminarBodega(bodega_In.getBdgBodegaid())) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Bodega !',"
                        + "  text: 'Removida correctamente',"
                        + "  icon: 'success',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Problemas !',"
                        + "  text: 'No se puede realizar esta accion',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
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
    
    public void guardarTemporal(Bodega bod_in) {
        bog_temporal = bod_in;
    }
    
    public void editarBodega() {
        try {
            bodegaFacadeLocal.edit(bog_temporal);
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Categoria !',"
                    + "  text: 'Actualizada correctamente',"
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
    
    public Bodega getBog_temporal() {
        return bog_temporal;
    }
    
    public void setBog_temporal(Bodega bog_temporal) {
        this.bog_temporal = bog_temporal;
    }
    
    public Bodega getBog_nueva() {
        return bog_nueva;
    }
    
    public void setBog_nueva(Bodega bog_nueva) {
        this.bog_nueva = bog_nueva;
    }

    public String getBod_nombre() {
        return bod_nombre;
    }

    public void setBod_nombre(String bod_nombre) {
        this.bod_nombre = bod_nombre;
    }

    public String getBod_direccion() {
        return bod_direccion;
    }

    public void setBod_direccion(String bod_direccion) {
        this.bod_direccion = bod_direccion;
    }

    public String getBod_telefono() {
        return bod_telefono;
    }

    public void setBod_telefono(String bod_telefono) {
        this.bod_telefono = bod_telefono;
    }
    
}
