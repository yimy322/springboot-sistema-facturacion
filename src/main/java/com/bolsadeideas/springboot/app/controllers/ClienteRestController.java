/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.view.xml.ClienteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yimy
 */
@RestController//El restcontroller es una combinacion entre controller y responsebody
@RequestMapping("/api/clientes")
public class ClienteRestController {
    
    @Autowired
    private IClienteService clienteService;
    
    @GetMapping("/listar")
    public ClienteList listar() {
        return new ClienteList(clienteService.findAll());
    }

    
}
