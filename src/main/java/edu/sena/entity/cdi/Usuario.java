/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.entity.cdi;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_usuarioid")
    private Integer usuUsuarioid;
    @Size(max = 45)
    @Column(name = "usu_tipodocumento")
    private String usuTipodocumento;
    @Column(name = "usu_numerodocumento")
    private BigInteger usuNumerodocumento;
    @Size(max = 45)
    @Column(name = "usu_nombres")
    private String usuNombres;
    @Size(max = 45)
    @Column(name = "usu_apellidos")
    private String usuApellidos;
    @Size(max = 45)
    @Column(name = "usu_correo")
    private String usuCorreo;
    @Size(max = 45)
    @Column(name = "usu_clave")
    private String usuClave;
    @Size(max = 45)
    @Column(name = "usu_telefono")
    private String usuTelefono;
    @Size(max = 45)
    @Column(name = "usu_direccion")
    private String usuDireccion;
    @Column(name = "usu_estado")
    private Short usuEstado;
    @ManyToMany(mappedBy = "usuarioCollection", fetch = FetchType.LAZY)
    private Collection<Rol> rolCollection;
    @OneToMany(mappedBy = "fkOperario", fetch = FetchType.LAZY)
    private Collection<Ordencompra> ordencompraCollection;
    @OneToMany(mappedBy = "fkProveedor", fetch = FetchType.LAZY)
    private Collection<OrdencompraProducto> ordencompraProductoCollection;
    @OneToMany(mappedBy = "fkCliente", fetch = FetchType.LAZY)
    private Collection<Factura> facturaCollection;
    @OneToMany(mappedBy = "fkVendedor", fetch = FetchType.LAZY)
    private Collection<Factura> facturaCollection1;

    public Usuario() {
    }

    public Usuario(Integer usuUsuarioid) {
        this.usuUsuarioid = usuUsuarioid;
    }

    public Integer getUsuUsuarioid() {
        return usuUsuarioid;
    }

    public void setUsuUsuarioid(Integer usuUsuarioid) {
        this.usuUsuarioid = usuUsuarioid;
    }

    public String getUsuTipodocumento() {
        return usuTipodocumento;
    }

    public void setUsuTipodocumento(String usuTipodocumento) {
        this.usuTipodocumento = usuTipodocumento;
    }

    public BigInteger getUsuNumerodocumento() {
        return usuNumerodocumento;
    }

    public void setUsuNumerodocumento(BigInteger usuNumerodocumento) {
        this.usuNumerodocumento = usuNumerodocumento;
    }

    public String getUsuNombres() {
        return usuNombres;
    }

    public void setUsuNombres(String usuNombres) {
        this.usuNombres = usuNombres;
    }

    public String getUsuApellidos() {
        return usuApellidos;
    }

    public void setUsuApellidos(String usuApellidos) {
        this.usuApellidos = usuApellidos;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    public String getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(String usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }

    public Short getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(Short usuEstado) {
        this.usuEstado = usuEstado;
    }

    public Collection<Rol> getRolCollection() {
        return rolCollection;
    }

    public void setRolCollection(Collection<Rol> rolCollection) {
        this.rolCollection = rolCollection;
    }

    public Collection<Ordencompra> getOrdencompraCollection() {
        return ordencompraCollection;
    }

    public void setOrdencompraCollection(Collection<Ordencompra> ordencompraCollection) {
        this.ordencompraCollection = ordencompraCollection;
    }

    public Collection<OrdencompraProducto> getOrdencompraProductoCollection() {
        return ordencompraProductoCollection;
    }

    public void setOrdencompraProductoCollection(Collection<OrdencompraProducto> ordencompraProductoCollection) {
        this.ordencompraProductoCollection = ordencompraProductoCollection;
    }

    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    public Collection<Factura> getFacturaCollection1() {
        return facturaCollection1;
    }

    public void setFacturaCollection1(Collection<Factura> facturaCollection1) {
        this.facturaCollection1 = facturaCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuUsuarioid != null ? usuUsuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuUsuarioid == null && other.usuUsuarioid != null) || (this.usuUsuarioid != null && !this.usuUsuarioid.equals(other.usuUsuarioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.entity.cdi.Usuario[ usuUsuarioid=" + usuUsuarioid + " ]";
    }
    
}
