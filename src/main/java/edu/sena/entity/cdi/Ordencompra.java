/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.entity.cdi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_ordencompra")
@NamedQueries({
    @NamedQuery(name = "Ordencompra.findAll", query = "SELECT o FROM Ordencompra o")})
public class Ordencompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "odc_ordencompraid")
    private Integer odcOrdencompraid;
    @Size(max = 45)
    @Column(name = "odc_ordennumero")
    private String odcOrdennumero;
    @Column(name = "odc_catidad")
    private Integer odcCatidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "odc_valorcompra")
    private Double odcValorcompra;
    @Size(max = 255)
    @Column(name = "odc_novedad")
    private String odcNovedad;
    @Column(name = "odc_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date odcFechaIngreso;
    @JoinColumn(name = "fk_operario", referencedColumnName = "usu_usuarioid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario fkOperario;
    @OneToMany(mappedBy = "fkOrdencompra", fetch = FetchType.LAZY)
    private Collection<OrdencompraProducto> ordencompraProductoCollection;

    public Ordencompra() {
    }

    public Ordencompra(Integer odcOrdencompraid) {
        this.odcOrdencompraid = odcOrdencompraid;
    }

    public Integer getOdcOrdencompraid() {
        return odcOrdencompraid;
    }

    public void setOdcOrdencompraid(Integer odcOrdencompraid) {
        this.odcOrdencompraid = odcOrdencompraid;
    }

    public String getOdcOrdennumero() {
        return odcOrdennumero;
    }

    public void setOdcOrdennumero(String odcOrdennumero) {
        this.odcOrdennumero = odcOrdennumero;
    }

    public Integer getOdcCatidad() {
        return odcCatidad;
    }

    public void setOdcCatidad(Integer odcCatidad) {
        this.odcCatidad = odcCatidad;
    }

    public Double getOdcValorcompra() {
        return odcValorcompra;
    }

    public void setOdcValorcompra(Double odcValorcompra) {
        this.odcValorcompra = odcValorcompra;
    }

    public String getOdcNovedad() {
        return odcNovedad;
    }

    public void setOdcNovedad(String odcNovedad) {
        this.odcNovedad = odcNovedad;
    }

    public Date getOdcFechaIngreso() {
        return odcFechaIngreso;
    }

    public void setOdcFechaIngreso(Date odcFechaIngreso) {
        this.odcFechaIngreso = odcFechaIngreso;
    }

    public Usuario getFkOperario() {
        return fkOperario;
    }

    public void setFkOperario(Usuario fkOperario) {
        this.fkOperario = fkOperario;
    }

    public Collection<OrdencompraProducto> getOrdencompraProductoCollection() {
        return ordencompraProductoCollection;
    }

    public void setOrdencompraProductoCollection(Collection<OrdencompraProducto> ordencompraProductoCollection) {
        this.ordencompraProductoCollection = ordencompraProductoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (odcOrdencompraid != null ? odcOrdencompraid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ordencompra)) {
            return false;
        }
        Ordencompra other = (Ordencompra) object;
        if ((this.odcOrdencompraid == null && other.odcOrdencompraid != null) || (this.odcOrdencompraid != null && !this.odcOrdencompraid.equals(other.odcOrdencompraid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.entity.cdi.Ordencompra[ odcOrdencompraid=" + odcOrdencompraid + " ]";
    }
    
}
