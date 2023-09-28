/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.view.xml;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

/**
 *
 * @author Yimy
 */
//Para el xml
@Component("listar.xml")
public class ClienteListXmlView  extends MarshallingView{

    @Autowired
    public ClienteListXmlView(Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        model.remove("titulo");       
        model.remove("page");  
        
        //antes de borrar los clientes tenemos que obtenerlo para hacer la conversion
        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
        
        model.remove("clientes");  
        
        model.put("clienteList", new ClienteList(clientes.getContent()));
        
        super.renderMergedOutputModel(model, request, response); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
}
