/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.controllers.ClienteController;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Yimy
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    @Override
    public Resource load(String filename) {
        Path pathFoto = getPath(filename);
        Resource recurso = null;
        try {
            recurso = new UrlResource(pathFoto.toUri());
            if (!recurso.exists() || !recurso.isReadable()) {
                throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return recurso;
    }

    @Override
    public String copy(MultipartFile file) {
        //Le damos un nombre ramdon para que no se repita
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        //indicamos la ruta donde vamos a guardar las imagenes
        Path rootPath = getPath(uniqueFilename);
        try {//Aca vamos a crear y escribir la imagen
            Files.copy(file.getInputStream(), rootPath);
        } catch (IOException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();

        if (archivo.exists() && archivo.canRead()) {
            if (archivo.delete()) {
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get("uploads").resolve(filename).toAbsolutePath();
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get("uploads").toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get("uploads"));
    }

}
