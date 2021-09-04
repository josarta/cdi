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
import edu.sena.facade.cdi.BodegaFacadeLocal;
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
    private Part archivoCarga;

    /**
     * Creates a new instance of BodegaView
     */
    public BodegaView() {
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
                        direccion= nextline[1] 
                        telefono= nextline[2] 
                        Bodega bdg = new Bodega();
                        bdg.setBdgNombre(nextline[0]);
                        bdg.setBdgDireccion(nextline[1]);
                        bdg.setBdgTelefono(Integer.parseInt(nextline[2]));  
                        bodegaFacadeLocal.create(bdg);*/

                        Bodega bogObj = bodegaFacadeLocal.validarSiExiste(nextline[0]);
                        if (bogObj == null) {
                            bodegaFacadeLocal.crearBodega(nextline[0], nextline[1], nextline[2]);
                        } else {        
                            bogObj.setBdgDireccion(nextline[1]);
                            bogObj.setBdgTelefono(Integer.parseInt(nextline[2]));
                            bodegaFacadeLocal.edit(bogObj);
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

    public Part getArchivoCarga() {
        return archivoCarga;
    }

    public void setArchivoCarga(Part archivoCarga) {
        this.archivoCarga = archivoCarga;
    }

}
