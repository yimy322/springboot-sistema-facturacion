/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Yimy
 */
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, Principal principal, RedirectAttributes flash){//principal nos permite validar
        //si es distinto de null significa que ya se logeo, asi que para que no vuelva a mostrar el form login lo redigiremos
        //para evitar que se logee dos veces
        if (principal != null) {
            flash.addFlashAttribute("info", "Ya ha iniciado sesion anteriormente");
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrectas");
        }
        if (logout != null) {
            model.addAttribute("success", "Ha cerrado sesion con exito");
        }
        return "login";
    }
    
}
