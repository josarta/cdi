/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Producto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josarta
 */
@Local
public interface ProductoFacadeLocal {

    void create(Producto producto);

    void edit(Producto producto);

    void remove(Producto producto);

    Producto find(Object id);

    List<Producto> findAll();

    List<Producto> findRange(int[] range);

    int count();

    public List<Producto> leertodos();

    public boolean removerFoto(int fk_productoid, int fk_fotoid);

    public boolean ingresarFoto(int fk_productoid, int fk_fotoid);

    public boolean eliminarProducto(int bod_id);

    public boolean crearProducto(Producto productoIn, int fk_categoria);

    public boolean actualizarProducto(Producto productoIn, int fk_categoria);

    public Producto leerPorId(int proId);
    
}
