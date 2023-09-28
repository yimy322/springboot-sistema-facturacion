/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Yimy
 */
public interface IClienteService {
    
    public List<Cliente> findAll();
    
    public Page<Cliente> findAll(Pageable pageable);
    
    public void save(Cliente cliente);
    
    public Cliente findOne(Long id);
    
    public Cliente fetchByIdWithFacturas(Long id);
    
    public void delete(Long id);
    
    public List<Producto> finByNombre(String term);
    
    public void saveFactura(Factura factura);
    
    public Producto findProductoById(Long id);
    
    public Factura findFacturaById(Long id);
    
    public void deleteFactura(Long id);
    
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id);
    
}
