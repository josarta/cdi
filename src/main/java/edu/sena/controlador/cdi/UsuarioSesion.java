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
import edu.sena.entity.cdi.Usuario;
import edu.sena.facade.cdi.UsuarioFacadeLocal;
import edu.sena.utilidades.cdi.Mail;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author Josarta
 */
@Named(value = "usuarioSesion")
@SessionScoped
public class UsuarioSesion implements Serializable {

    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;

    @Resource(lookup = "java:app/dbs_cdi")
    DataSource dataSource;

    private String correoIn;
    private String claveIn;
    private Usuario usuReg = new Usuario();
    private Usuario usuLog = new Usuario();
    private Usuario usuTemporal = new Usuario();
    private Part archivoFoto;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private Part archivoCarga;

    public UsuarioSesion() {
    }

    @PostConstruct
    public void cargaInicial() {

    }

    public void generarArchivo(String tipoArchivo, String documentoIn) throws JRException, IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        String nombreReporte = "";
        String nombreReporteDescarga = "";
        
        try {
            Map parametros = new HashMap();
            if (!documentoIn.isEmpty()) {
                parametros.put("documentoIn", documentoIn);
                nombreReporte = "diploma";
                nombreReporteDescarga = "Mi Diploma";
            } else {
                nombreReporte = "usuarios";
                nombreReporteDescarga = "Lista Usuarios";        
            }

            File jasper = new File(context.getRealPath("/reportes/"+nombreReporte+".jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, dataSource.getConnection());
            switch (tipoArchivo) {
                case "pdf":
                    response.setContentType("application/pdf");
                    response.addHeader("Content-disposition", "attachment; filename="+nombreReporte+".pdf");
                    OutputStream os = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(jp, os);
                    os.flush();
                    os.close();
                    break;

                case "xlsx":
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.xlsx");

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
                    response.addHeader("Content-disposition", "attachment; filename=Lista usuarios.docx");

                    JRDocxExporter exporterDoc = new JRDocxExporter(); // initialize exporter 
                    exporterDoc.setExporterInput(new SimpleExporterInput(jp)); // set compiled report as input
                    exporterDoc.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

                    SimpleDocxExporterConfiguration configurationDoc = new SimpleDocxExporterConfiguration();
                    configurationDoc.setMetadataAuthor("Jose Luis Sarta A."); // setup configuration
                    configurationDoc.setMetadataTitle("Reporte de usuarios");
                    configurationDoc.setMetadataSubject("Listado de usuarios");

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
                        /*tipoDocumento = nextline[0] 
                        numeroDocumento= nextline[1] 
                        nombres= nextline[2] 
                        apellidos = nextline[3] 
                        correo = nextline[4] 
                        clave = nextline[5] 
                        telefono = nextline[6] 
                        direccion = nextline[7] 
                        estado = nextline[8] 
                        foto = nextline[9] 
                        ;*/

                        Usuario usuObj = usuarioFacadeLocal.validarSiExiste(nextline[4]);
                        if (usuObj == null) {
                            Usuario objNew = new Usuario();
                            objNew.setUsuTipodocumento(nextline[0]);
                            objNew.setUsuNumerodocumento(BigInteger.valueOf(Long.parseLong(nextline[1])));
                            objNew.setUsuNombres(nextline[2]);
                            objNew.setUsuApellidos(nextline[3]);
                            objNew.setUsuCorreo(nextline[4]);
                            objNew.setUsuClave(nextline[5]);
                            objNew.setUsuTelefono(nextline[6]);
                            objNew.setUsuDireccion(nextline[7]);
                            objNew.setUsuEstado(Short.parseShort(nextline[8]));
                            objNew.setUsuFoto(nextline[9]);
                            usuarioFacadeLocal.crearUsuario(objNew);

                        } else {
                            usuObj.setUsuTipodocumento(nextline[0]);
                            usuObj.setUsuNumerodocumento(BigInteger.valueOf(Long.parseLong(nextline[1])));
                            usuObj.setUsuNombres(nextline[2]);
                            usuObj.setUsuApellidos(nextline[3]);
                            usuObj.setUsuCorreo(nextline[4]);
                            usuObj.setUsuClave(nextline[5]);
                            usuObj.setUsuTelefono(nextline[6]);
                            usuObj.setUsuDireccion(nextline[7]);
                            usuObj.setUsuEstado(Short.parseShort(nextline[8]));
                            usuObj.setUsuFoto(nextline[9]);
                            usuarioFacadeLocal.edit(usuObj);
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

                Mail.recuperarClaves(usuReg.getUsuNombres() + usuReg.getUsuApellidos(), usuReg.getUsuCorreo(), usuReg.getUsuClave());
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

    public void actualizarmisdatos() {
        try {
            usuarioFacadeLocal.edit(usuLog);
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

    public void cargarFotoPerfil() {
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
                    File carpeta = new File("C:\\cdi\\usuarios\\fotoperfil");
                    if (!carpeta.exists()) {
                        carpeta.mkdirs();
                    }
                    Calendar hoy = Calendar.getInstance();
                    String nuevoNombre = sdf.format(hoy.getTime()) + ".";
                    nuevoNombre += FilenameUtils.getExtension(archivoFoto.getSubmittedFileName());
                    Files.copy(is, (new File(carpeta, nuevoNombre)).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    usuLog.setUsuFoto(nuevoNombre);
                    usuarioFacadeLocal.edit(usuLog);

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

    public Part getArchivoFoto() {
        return archivoFoto;
    }

    public void setArchivoFoto(Part archivoFoto) {
        this.archivoFoto = archivoFoto;
    }

    public Part getArchivoCarga() {
        return archivoCarga;
    }

    public void setArchivoCarga(Part archivoCarga) {
        this.archivoCarga = archivoCarga;
    }

}
