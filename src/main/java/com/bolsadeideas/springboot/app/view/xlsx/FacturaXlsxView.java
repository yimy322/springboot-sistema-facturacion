/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.view.xlsx;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;


/**
 *
 * @author Yimy
 */
@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //Cambiar nombre del archivo
        response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
        
        //factura porque asi se pasa en el model de factura/ver
        Factura factura = (Factura) model.get("factura"); 
        Sheet sheet = workbook.createSheet("Factura Spring");
        
        //Para el cambio de idioma
        MessageSourceAccessor mensajes = getMessageSourceAccessor();
        
        //Filas
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));
        
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getEmail());
        
        sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura"));
        sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.folio") + ": " + factura.getId());
        sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.descripcion") + ": "+ factura.getDescripcion());
        sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.fecha") + ": "+ factura.getCreateAt());
        //estilos
        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);
        
        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.producto"));
        header.createCell(1).setCellValue(mensajes.getMessage("text.factura.ver.datos.precio"));
        header.createCell(2).setCellValue(mensajes.getMessage("text.factura.ver.datos.cantidad"));
        header.createCell(3).setCellValue(mensajes.getMessage("text.factura.ver.datos.total"));
        
        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);
        
        int rownum = 10;
        //estilos
        for (ItemFactura item:factura.getItems()) {
            Row fila = sheet.createRow(rownum++);
            
            cell = fila.createCell(0);
            cell.setCellValue(item.getProducto().getNombre());
            cell.setCellStyle(tbodyStyle);
            
            cell = fila.createCell(1);
            cell.setCellValue(item.getProducto().getPrecio());
            cell.setCellStyle(tbodyStyle);
            
            cell = fila.createCell(2);
            cell.setCellValue(item.getCantidad());
            cell.setCellStyle(tbodyStyle);
            
            cell = fila.createCell(3);
            cell.setCellValue(item.calcularImporte());
            cell.setCellStyle(tbodyStyle);
            
        }
        
        Row filatotal = sheet.createRow(rownum);
        cell = filatotal.createCell(2);
        cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.total"));
        cell.setCellStyle(tbodyStyle);
        
        cell = filatotal.createCell(3);
        filatotal.createCell(3).setCellValue(factura.getTotal());
        cell.setCellStyle(tbodyStyle);
        
    }
    
}
