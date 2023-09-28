/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bolsadeideas.springboot.app.models.dao;

import com.bolsadeideas.springboot.app.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Yimy
 */
public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
    
    public Usuario findByUsername(String username);
    
}
