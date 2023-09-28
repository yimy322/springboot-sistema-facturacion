/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;
import com.bolsadeideas.springboot.app.view.xml.ClienteList;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Yimy
 */
@Controller
@SessionAttributes("cliente")
public class ClienteController {

    //un logger
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    //Para el idioma
    @Autowired
    private MessageSource messageSource;

    @Secured("ROLE_USER")
    @GetMapping(value = "/uploads/{filename:.+}")//Para el cargado de imagen
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = uploadFileService.load(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/ver/{id}")//el redirectattributes es para los mensajes
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = clienteService.fetchByIdWithFacturas(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }
    
    //para mostrar en json
    @GetMapping("/listar-rest")
    public @ResponseBody ClienteList listarRest() {//el responsebody significa que el listado de clientes se va a almacenar en el cuerpo de la respuesta 
        return new ClienteList(clienteService.findAll());
    }

    @GetMapping({"/listar", "/"})
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Locale locale) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
        }

        //Es para hacer la paginacion
        Pageable pageRequest = PageRequest.of(page, 4);

        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        //-----------
        model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);

        //ESTO ES PARA QUE TE MUESTRE TODA LA LISTA DE CLIENTES EN EL XML, LUEGO
        //IR A LA CLASE DONDE HACES LA VISTA XML Y USAR el findAll sin el Pageable
//        if (format.equals("html")) {
//            Pageable pageRequest = PageRequest.of(page, 4);
//
//            Page<Cliente> clientes = clienteService.findAll(pageRequest);
//
//            PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);
//            model.addAttribute("clientes", clientes);
//            model.addAttribute("page", pageRender);
//        } else {
//            model.addAttribute("clientes", clienteService.findAll());
//        }

        return "listar";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de Cliente");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = null;
        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El ID del cliente no existe en la base de datos");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult results, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        if (results.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }
        //Validamos que foto no sea vacio
        if (!foto.isEmpty()) {

            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
                uploadFileService.delete(cliente.getFoto());
            }

            String uniqueFilename = uploadFileService.copy(foto);

            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
            //Guardamos la foto en el registro Cliente
            cliente.setFoto(uniqueFilename);

        }
        //Para enviar el mensaje si es editado o creado
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito!";
        logger.info(cliente);
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:listar";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);

            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con exito!");

            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito|");
            }

        }
        return "redirect:/listar";
    }

    //Para validar el rol desde aca, el controller
    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }
        Authentication auth = context.getAuthentication();
        if (auth == null) {
            return false;
        }
        //toda clase rol implemente la interfaz GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        //Lo mismo que lo que esta comentado pero mas corto, retorna un booleano
        return authorities.contains(new SimpleGrantedAuthority(role));
//        for(GrantedAuthority authority: authorities){
//            if (role.equals(authority.getAuthority())) {
//                logger.info("Hola usuario ".concat(auth.getName()).concat("tu rol es ".concat(authority.getAuthority())));
//                return true;
//            }
//        }
//        return false;
    }

}
