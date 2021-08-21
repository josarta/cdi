/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.controlador.cdi;

import edu.sena.entity.cdi.Categoria;
import edu.sena.facade.cdi.CategoriaFacadeLocal;
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
@Named(value = "categoriaView")
@ViewScoped
public class CategoriaView implements Serializable {

    @EJB
    CategoriaFacadeLocal categoriaFacadeLocal;
    private Categoria cat_temporal = new Categoria();
    private String c_nombre = "";
    private String c_descrip = "";

    /**
     * Creates a new instance of CategoriaView
     */
    public CategoriaView() {
    }

    public void crearCategoria() {
        try {
            boolean salida = categoriaFacadeLocal.crearCategoria(c_nombre, c_descrip);
            if (salida) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Categoria !',"
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

    public void guardarTemporal(Categoria cat_in) {
        cat_temporal = cat_in;
    }

    public void editarCategoria() {
        try {
            categoriaFacadeLocal.edit(cat_temporal);
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

    public void eliminarCategoria(Categoria cat_In) {
        try {
            if (categoriaFacadeLocal.eliminarCategoria(cat_In.getCatCategoriaid())) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Categoria !',"
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

    public List<Categoria> leertodos() {
        return categoriaFacadeLocal.leertodos();
    }

    public String getC_nombre() {
        return c_nombre;
    }

    public void setC_nombre(String c_nombre) {
        this.c_nombre = c_nombre;
    }

    public String getC_descrip() {
        return c_descrip;
    }

    public void setC_descrip(String c_descrip) {
        this.c_descrip = c_descrip;
    }

    public Categoria getCat_temporal() {
        return cat_temporal;
    }

    public void setCat_temporal(Categoria cat_temporal) {
        this.cat_temporal = cat_temporal;
    }

}
