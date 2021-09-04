/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.controlador.cdi;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import edu.sena.entity.cdi.Bodega;
import edu.sena.entity.cdi.Categoria;
import edu.sena.facade.cdi.CategoriaFacadeLocal;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
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
    private Part archivoCarga;

    /**
     * Creates a new instance of CategoriaView
     */
    public CategoriaView() {
    }
    
    public void cargarInicialDatos() {
        if (archivoCarga != null) {
            if (archivoCarga.getSize() > 700000) {
                PrimeFaces.current().executeScript("Swal.fire({"
                        + "  title: 'El archivo !',"
                        + "  text: 'No se puede cargar por el tamaño !!!',"
                        + "  icon: 'error',"
                        + "  confirmButtonText: 'Ok'"
                        + "})");
            } else if (archivoCarga.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {
                
                try (InputStream is = archivoCarga.getInputStream()) {
                    File carpeta = new File("C:\\cdi\\administrador\\archivos");
                    if (!carpeta.exists()) {
                        carpeta.mkdirs();
                    }
                    Files.copy(is, (new File(carpeta, archivoCarga.getSubmittedFileName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    CSVParser conPuntoyComa = new CSVParserBuilder().withSeparator(';').build();
                    CSVReader reader = new CSVReaderBuilder(new FileReader("C:\\cdi\\administrador\\archivos\\" + archivoCarga.getSubmittedFileName())).withCSVParser(conPuntoyComa).build();
                    String[] nextline;
                    while ((nextline = reader.readNext()) != null) {
                        /*nombre = nextline[0] 
                        descripcion= nextline[1] 
                         */
                        
                        Categoria catObj = categoriaFacadeLocal.validarSiExiste(nextline[0]);
                        if (catObj == null) {
                            categoriaFacadeLocal.crearCategoria(nextline[0], nextline[1]);
                        } else {                            
                            catObj.setCatDescripcion(nextline[1]);
                            categoriaFacadeLocal.edit(catObj);
                        }
                        
                    }
                    reader.close();
                    
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
    
    public Part getArchivoCarga() {
        return archivoCarga;
    }
    
    public void setArchivoCarga(Part archivoCarga) {
        this.archivoCarga = archivoCarga;
    }
    
}
