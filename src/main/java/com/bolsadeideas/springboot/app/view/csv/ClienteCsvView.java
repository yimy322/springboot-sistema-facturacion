/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.view.csv;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author Yimy
 */
//clase para la creacion de csv, archivos planos
@Component("listar.csv")//mismo nombre que la vista con la extencion, no olvidar ponerlo en el properties
public class ClienteCsvView extends AbstractView{

    public ClienteCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
        response.setContentType(getContentType());
        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
        
        //convertiremos la lista de clientes en un archivo plano
        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
        
        String[] header = {"id", "nombre", "apellido", "email", "createat"};
        
        beanWriter.writeHeader( header);
        
        for(Cliente cliente:clientes){
            beanWriter.write(cliente, header);
        }
        
        beanWriter.close();
        
    }
    
}
