/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Categoria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josarta
 */
@Local
public interface CategoriaFacadeLocal {

    void create(Categoria categoria);

    void edit(Categoria categoria);

    void remove(Categoria categoria);

    Categoria find(Object id);

    List<Categoria> findAll();

    List<Categoria> findRange(int[] range);

    int count();

    public List<Categoria> leertodos();

    public boolean crearCategoria(String cat_nombre, String cat_descripcion);

    public boolean eliminarCategoria(int cat_id);

    public Categoria validarSiExiste(String nombreIn);
    
}
