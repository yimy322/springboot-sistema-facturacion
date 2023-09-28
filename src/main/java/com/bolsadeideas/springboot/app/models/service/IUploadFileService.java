/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bolsadeideas.springboot.app.models.service;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Yimy
 */
public interface IUploadFileService {
    
    public Resource load(String filename);
    public String copy(MultipartFile file);
    public boolean delete(String filename);
    public void deleteAll();
    public void init() throws IOException;
    
}
