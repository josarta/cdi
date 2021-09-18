/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.controlador.cdi;

import edu.sena.entity.cdi.Categoria;
import edu.sena.entity.cdi.Producto;
import edu.sena.facade.cdi.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Josarta
 */
@Named(value = "clienteView")
@ViewScoped
public class ClienteView implements Serializable {

    @EJB
    CategoriaFacadeLocal categoriaFacadeLocal;

    /**
     * Creates a new instance of ClienteView
     */
    private Producto productoTemporal = new Producto();
    private List<Producto> listaProductosCarrito = new ArrayList<>();
    private List<Categoria> listaCategorias = new ArrayList<>();
    private int cantidadProductos = 0;

    int fk_vendedor;
    int fk_cliente;
    private int fac_totalproductos = 0;
    private double fac_valortotal = 0;
    private double fac_impuestos = 0;
    private String fac_dirrecionentrega = "";
    private String fac_telefono = "";
    private String fac_ciudad = "";

    public ClienteView() {
    }

    @PostConstruct
    public void cargaInicial() {
        productoTemporal = new Producto();
        productoTemporal.setPdtTotal(0);
        fac_valortotal = 0;
        fac_totalproductos = 0;
        listaCategorias.addAll(categoriaFacadeLocal.leertodos());
    }

    public void guardarTemporal(Producto ptIn) {
        this.productoTemporal = ptIn;
        cantidadProductos = 0;
    }

    public void cargaCarrito() {

        productoTemporal.setPdtTotal(productoTemporal.getPdtTotal() - cantidadProductos);
        Producto ptIn = productoTemporal;
        ptIn.setPdtTotal(cantidadProductos);
        listaProductosCarrito.add(ptIn);
        fac_valortotal = fac_valortotal + (ptIn.getPdtValorventa() * cantidadProductos);
        fac_totalproductos = fac_totalproductos +cantidadProductos;
        
    }

    public Producto getProductoTemporal() {
        return productoTemporal;
    }

    public void setProductoTemporal(Producto productoTemporal) {
        this.productoTemporal = productoTemporal;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public List<Producto> getListaProductosCarrito() {
        return listaProductosCarrito;
    }

    public void setListaProductosCarrito(List<Producto> listaProductosCarrito) {
        this.listaProductosCarrito = listaProductosCarrito;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public int getFac_totalproductos() {
        return fac_totalproductos;
    }

    public void setFac_totalproductos(int fac_totalproductos) {
        this.fac_totalproductos = fac_totalproductos;
    }

    public double getFac_valortotal() {
        return fac_valortotal;
    }

    public void setFac_valortotal(double fac_valortotal) {
        this.fac_valortotal = fac_valortotal;
    }

    public double getFac_impuestos() {
        return fac_impuestos;
    }

    public void setFac_impuestos(double fac_impuestos) {
        this.fac_impuestos = fac_impuestos;
    }

    public String getFac_dirrecionentrega() {
        return fac_dirrecionentrega;
    }

    public void setFac_dirrecionentrega(String fac_dirrecionentrega) {
        this.fac_dirrecionentrega = fac_dirrecionentrega;
    }

    public String getFac_telefono() {
        return fac_telefono;
    }

    public void setFac_telefono(String fac_telefono) {
        this.fac_telefono = fac_telefono;
    }

    public String getFac_ciudad() {
        return fac_ciudad;
    }

    public void setFac_ciudad(String fac_ciudad) {
        this.fac_ciudad = fac_ciudad;
    }

}
