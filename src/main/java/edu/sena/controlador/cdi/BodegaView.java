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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
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
    
        @Resource(lookup = "java:app/dbs_cdi")
    DataSource dataSource;

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

    public void generarArchivo(String tipoArchivo) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        File jasper = new File(context.getRealPath("/reportes/bodegas.jasper"));
        try {
            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), new HashMap(), dataSource.getConnection());
            switch (tipoArchivo) {
                case "pdf":
                    response.setContentType("application/pdf");
                    response.addHeader("Content-disposition", "attachment; filename=Lista bodegas.pdf");
                    OutputStream os = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, os);
                    os.flush();
                    os.close();
                    break;

                case "xlsx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.addHeader("Content-disposition", "attachment; filename=Lista bodegas.xlsx");

                    JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter 
                    exporter.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                    configuration.setOnePagePerSheet(true); // setup configuration
                    configuration.setDetectCellType(true);
                    configuration.setSheetNames(new String[]{"Bodegas"});
                    exporter.setConfiguration(configuration); // set configuration    
                    exporter.exportReport();
                    break;

                case "docx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    response.addHeader("Content-disposition", "attachment; filename=Lista bodegas.docx");

                    JRDocxExporter exporterDoc = new JRDocxExporter(); // initialize exporter 
                    exporterDoc.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporterDoc.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleDocxExporterConfiguration configurationDoc = new SimpleDocxExporterConfiguration();
                    configurationDoc.setMetadataAuthor("Jose Luis Sarta A."); // setup configuration
                    configurationDoc.setMetadataTitle("Reporte de categorias");
                    configurationDoc.setMetadataSubject("Listado de categorias");

                    exporterDoc.setConfiguration(configurationDoc); // set configuration    
                    exporterDoc.exportReport();
                    break;

                default:
                    System.err.println(" No se encontro este caso :: CategoriaView::generarArchivo");
                    break;

            }
            facesContext.responseComplete();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaView.class.getName()).log(Level.SEVERE, null, ex);
        }

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
