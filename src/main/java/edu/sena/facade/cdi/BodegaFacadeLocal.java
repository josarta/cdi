/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.facade.cdi;

import edu.sena.entity.cdi.Bodega;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Josarta
 */
@Local
public interface BodegaFacadeLocal {

    void create(Bodega bodega);

    void edit(Bodega bodega);

    void remove(Bodega bodega);

    Bodega find(Object id);

    List<Bodega> findAll();

    List<Bodega> findRange(int[] range);

    int count();

    public List<Bodega> leertodos();

    public boolean crearBodega(String bod_nombre, String bod_direccion, String bod_telefono);

    public boolean eliminarBodega(int bod_id);

    public Bodega validarSiExiste(String nombreIn);
    
}
