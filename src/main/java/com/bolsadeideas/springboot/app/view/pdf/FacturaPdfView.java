/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bolsadeideas.springboot.app.view.pdf;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

/**
 *
 * @author Yimy
 */
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {
//Para el cambio de idioma
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //factura porque asi se pasa en el model de factura/ver
        Factura factura = (Factura) model.get("factura");
        
        //Para el idioma
        Locale locale = localeResolver.resolveLocale(request);

        //creamos las tablas
        PdfPTable tabla = new PdfPTable(1);//el 1 es para indicar el numero de columnas
        tabla.setSpacingAfter(20);

        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);

        tabla.addCell(cell);
        tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        tabla.addCell(factura.getCliente().getEmail());

        PdfPTable tabla2 = new PdfPTable(1);
        tabla2.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);

        tabla2.addCell(cell);
        tabla2.addCell("Folio: " + factura.getId());
        tabla2.addCell("Descripcion: " + factura.getDescripcion());
        tabla2.addCell("Fecha: " + factura.getCreateAt());

        document.add(tabla);
        document.add(tabla2);

        PdfPTable tabla3 = new PdfPTable(4);
        //cambiar tamaño
        tabla3.setWidths(new float[]{2.5f, 1, 1, 1});

        tabla3.addCell("Producto");
        tabla3.addCell("Precio");
        tabla3.addCell("Cantidad");
        tabla3.addCell("Total");

        for (ItemFactura item : factura.getItems()) {
            tabla3.addCell(item.getProducto().getNombre());
            tabla3.addCell(item.getProducto().getPrecio().toString());

            cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);//para centrar

            tabla3.addCell(cell);
            tabla3.addCell(item.calcularImporte().toString());
        }

        cell = new PdfPCell(new Phrase("Total: "));
        cell.setColspan(3);//para que ocupe 3 columnas, espacio
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        tabla3.addCell(cell);
        tabla3.addCell(factura.getTotal().toString());

        document.add(tabla3);

    }

}
