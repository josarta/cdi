/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.entity.cdi;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author Josarta
 */
@Entity
@Table(name = "tbl_factura_producto")
@NamedQueries({
    @NamedQuery(name = "FacturaProducto.findAll", query = "SELECT f FROM FacturaProducto f")})
public class FacturaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fpt_facturaproductoid")
    private Integer fptFacturaproductoid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fpt_valorunidad")
    private Double fptValorunidad;
    @Column(name = "fpt_cantidad")
    private Integer fptCantidad;
    @JoinColumn(name = "fk_factura", referencedColumnName = "fac_facturaid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Factura fkFactura;
    @JoinColumn(name = "fk_producto", referencedColumnName = "pdt_productoid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto fkProducto;

    public FacturaProducto() {
    }

    public FacturaProducto(Integer fptFacturaproductoid) {
        this.fptFacturaproductoid = fptFacturaproductoid;
    }

    public Integer getFptFacturaproductoid() {
        return fptFacturaproductoid;
    }

    public void setFptFacturaproductoid(Integer fptFacturaproductoid) {
        this.fptFacturaproductoid = fptFacturaproductoid;
    }

    public Double getFptValorunidad() {
        return fptValorunidad;
    }

    public void setFptValorunidad(Double fptValorunidad) {
        this.fptValorunidad = fptValorunidad;
    }

    public Integer getFptCantidad() {
        return fptCantidad;
    }

    public void setFptCantidad(Integer fptCantidad) {
        this.fptCantidad = fptCantidad;
    }

    public Factura getFkFactura() {
        return fkFactura;
    }

    public void setFkFactura(Factura fkFactura) {
        this.fkFactura = fkFactura;
    }

    public Producto getFkProducto() {
        return fkProducto;
    }

    public void setFkProducto(Producto fkProducto) {
        this.fkProducto = fkProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fptFacturaproductoid != null ? fptFacturaproductoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaProducto)) {
            return false;
        }
        FacturaProducto other = (FacturaProducto) object;
        if ((this.fptFacturaproductoid == null && other.fptFacturaproductoid != null) || (this.fptFacturaproductoid != null && !this.fptFacturaproductoid.equals(other.fptFacturaproductoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.entity.cdi.FacturaProducto[ fptFacturaproductoid=" + fptFacturaproductoid + " ]";
    }
    
}
