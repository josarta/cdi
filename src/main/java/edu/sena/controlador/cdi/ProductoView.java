/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.controlador.cdi;

import edu.sena.entity.cdi.Foto;
import edu.sena.entity.cdi.Producto;
import edu.sena.facade.cdi.FotoFacadeLocal;
import edu.sena.facade.cdi.ProductoFacadeLocal;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author Josarta
 */
@Named(value = "productoView")
@ViewScoped
public class ProductoView implements Serializable {

    @EJB
    ProductoFacadeLocal productoFacadeLocal;
    @EJB
    FotoFacadeLocal fotoFacadeLocal;

    private Producto proNew = new Producto();
    private Producto proTem = new Producto();
    private int fk_cat;
    private Part archivoFoto;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * Creates a new instance of ProductoView
     */
    public ProductoView() {
    }

    public void crearProducto() {
        try {
            boolean salida = productoFacadeLocal.crearProducto(proNew, fk_cat);
            if (salida) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Producto !',"
                        + "  text: 'Creado correctamente',"
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

    public void eliminarProducto(Producto producto_In) {
        try {
            if (productoFacadeLocal.eliminarProducto(producto_In.getPdtProductoid())) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'Producto !',"
                        + "  text: 'Removido correctamente',"
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

    public void guardarTemporal(Producto pro_in) {
        proTem = pro_in;
        fk_cat = pro_in.getFkCategoria().getCatCategoriaid();
    }

    public void editarProduto() {
        try {
            productoFacadeLocal.actualizarProducto(proTem, fk_cat);
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Producto !',"
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

    public void removerFotoProducto(int fotoIt) {
        try {
            productoFacadeLocal.removerFoto(proTem.getPdtProductoid(), fotoIt);
            proTem = productoFacadeLocal.leerPorId(proTem.getPdtProductoid());
        } catch (Exception e) {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Problemas !',"
                    + "  text: 'No se puede realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        }

    }

    public void cargarFotoProducto() {
        if (archivoFoto != null) {
            if (archivoFoto.getSize() > 700000 || archivoFoto.getSize() < 10000) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'El archivo !',"
                        + "  text: 'No se puede cargar por el tamaño !!!',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            } else if (archivoFoto.getContentType().equalsIgnoreCase("image/png") || archivoFoto.getContentType().equalsIgnoreCase("image/jpeg")) {

                try (InputStream is = archivoFoto.getInputStream()) {
                    File carpeta = new File("C:\\cdi\\productos");
                    if (!carpeta.exists()) {
                        carpeta.mkdirs();
                    }
                    Calendar hoy = Calendar.getInstance();
                    String nuevoNombre = sdf.format(hoy.getTime()) + ".";
                    nuevoNombre += FilenameUtils.getExtension(archivoFoto.getSubmittedFileName());
                    Files.copy(is, (new File(carpeta, nuevoNombre)).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Foto fotoNueva = new Foto();
                    fotoNueva.setFotNombre(nuevoNombre);
                    fotoNueva.setFotDescripcion(archivoFoto.getSubmittedFileName());
                    fotoFacadeLocal.create(fotoNueva);
                    productoFacadeLocal.ingresarFoto(proTem.getPdtProductoid(), fotoNueva.getFotFotoid());
                    proTem = productoFacadeLocal.leerPorId(proTem.getPdtProductoid());
                } catch (Exception e) {
                    PrimeFaces.current().executeScript("Swal.fire({"
                            + "  title: 'Problemas !',"
                            + "  text: 'No se puede realizar esta accion',"
                            + "  icon: 'error',"
                            + "  confirmButtonText: 'Ok'"
                            + "})");
                }
            } else {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'El archivo !',"
                        + "  text: 'No es una imagen .png o .jpeg !!!',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            }

        } else {
            PrimeFaces.current().executeScript("Swal.fire({"
                    + "  title: 'Problemas !',"
                    + "  text: 'No se puede realizar esta accion',"
                    + "  icon: 'error',"
                    + "  confirmButtonText: 'Ok'"
                    + "})");
        }

        PrimeFaces.current().executeScript("document.getElementById('resetform').click()");

    }

    public List<Producto> leertodos() {
        return productoFacadeLocal.leertodos();
    }

    public Producto getProNew() {
        return proNew;
    }

    public void setProNew(Producto proNew) {
        this.proNew = proNew;
    }

    public Producto getProTem() {
        return proTem;
    }

    public void setProTem(Producto proTem) {
        this.proTem = proTem;
    }

    public int getFk_cat() {
        return fk_cat;
    }

    public void setFk_cat(int fk_cat) {
        this.fk_cat = fk_cat;
    }

    public Part getArchivoFoto() {
        return archivoFoto;
    }

    public void setArchivoFoto(Part archivoFoto) {
        this.archivoFoto = archivoFoto;
    }

}
