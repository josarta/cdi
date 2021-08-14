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
@Table(name = "tbl_ordencompra_producto")
@NamedQueries({
    @NamedQuery(name = "OrdencompraProducto.findAll", query = "SELECT o FROM OrdencompraProducto o")})
public class OrdencompraProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orp_ordencompraproductoid")
    private Integer orpOrdencompraproductoid;
    @Column(name = "orp_cantidad")
    private Integer orpCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "orp_valorcompra")
    private Double orpValorcompra;
    @JoinColumn(name = "fk_bodega", referencedColumnName = "bdg_bodegaid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bodega fkBodega;
    @JoinColumn(name = "fk_ordencompra", referencedColumnName = "odc_ordencompraid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ordencompra fkOrdencompra;
    @JoinColumn(name = "fk_producto", referencedColumnName = "pdt_productoid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto fkProducto;
    @JoinColumn(name = "fk_proveedor", referencedColumnName = "usu_usuarioid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario fkProveedor;

    public OrdencompraProducto() {
    }

    public OrdencompraProducto(Integer orpOrdencompraproductoid) {
        this.orpOrdencompraproductoid = orpOrdencompraproductoid;
    }

    public Integer getOrpOrdencompraproductoid() {
        return orpOrdencompraproductoid;
    }

    public void setOrpOrdencompraproductoid(Integer orpOrdencompraproductoid) {
        this.orpOrdencompraproductoid = orpOrdencompraproductoid;
    }

    public Integer getOrpCantidad() {
        return orpCantidad;
    }

    public void setOrpCantidad(Integer orpCantidad) {
        this.orpCantidad = orpCantidad;
    }

    public Double getOrpValorcompra() {
        return orpValorcompra;
    }

    public void setOrpValorcompra(Double orpValorcompra) {
        this.orpValorcompra = orpValorcompra;
    }

    public Bodega getFkBodega() {
        return fkBodega;
    }

    public void setFkBodega(Bodega fkBodega) {
        this.fkBodega = fkBodega;
    }

    public Ordencompra getFkOrdencompra() {
        return fkOrdencompra;
    }

    public void setFkOrdencompra(Ordencompra fkOrdencompra) {
        this.fkOrdencompra = fkOrdencompra;
    }

    public Producto getFkProducto() {
        return fkProducto;
    }

    public void setFkProducto(Producto fkProducto) {
        this.fkProducto = fkProducto;
    }

    public Usuario getFkProveedor() {
        return fkProveedor;
    }

    public void setFkProveedor(Usuario fkProveedor) {
        this.fkProveedor = fkProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orpOrdencompraproductoid != null ? orpOrdencompraproductoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdencompraProducto)) {
            return false;
        }
        OrdencompraProducto other = (OrdencompraProducto) object;
        if ((this.orpOrdencompraproductoid == null && other.orpOrdencompraproductoid != null) || (this.orpOrdencompraproductoid != null && !this.orpOrdencompraproductoid.equals(other.orpOrdencompraproductoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.sena.entity.cdi.OrdencompraProducto[ orpOrdencompraproductoid=" + orpOrdencompraproductoid + " ]";
    }
    
}
