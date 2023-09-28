/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bolsadeideas.springboot.app.models.dao;

import com.bolsadeideas.springboot.app.models.entity.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Yimy
 */
public interface IProductoDao extends CrudRepository<Producto, Long>{
    
    @Query("select p from Producto p where p.nombre like %?1%")//el ?1 es igual que term, lo reemplaza
    public List<Producto> findByNombre(String term);
    
    public List<Producto> findByNombreLikeIgnoreCase(String term);
    
}
