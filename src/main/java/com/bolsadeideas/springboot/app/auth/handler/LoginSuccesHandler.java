/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

/**
 *
 * @author Yimy
 */
@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        //para enviar el mensaje cuando se logea algun usuario
        FlashMap flashMap = new FlashMap();
        flashMap.put("success", "Hola "+authentication.getName()+", haz iniciado sesion con exito!");
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        
        if (authentication != null) {
            logger.info("'El usuario '"+authentication.getName()+"' ha iniciado sesion con exito'");
        }
        
        super.onAuthenticationSuccess(request, response, authentication); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
