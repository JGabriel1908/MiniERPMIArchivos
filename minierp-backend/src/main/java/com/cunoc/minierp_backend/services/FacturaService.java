package com.cunoc.minierp_backend.services;

import com.cunoc.minierp_backend.models.Sale;
import com.cunoc.minierp_backend.models.SaleDetails;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarFactura(Sale venta, List<SaleDetails> detalles) {
        try {
            Document documento = new Document(PageSize.LETTER, 40, 40, 50, 50);
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter.getInstance(documento, salida);
            documento.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC);
            Font etiquetaFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font encabezadoTablaFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);

            Paragraph titulo = new Paragraph("Mini ERP - Factura de Venta", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            Paragraph subtitulo = new Paragraph("Documento generado electrónicamente", subtituloFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(15);
            documento.add(subtitulo);

            PdfPTable datosTabla = new PdfPTable(2);
            datosTabla.setWidthPercentage(100);
            datosTabla.setWidths(new float[]{1, 1});

            datosTabla.addCell(celdaSinBorde("Factura No.: " + venta.getId(), etiquetaFont));
            datosTabla.addCell(celdaSinBorde("Fecha: " + venta.getFecha().format(FORMATO_FECHA), etiquetaFont));

            String nombreCliente = venta.getCliente().getNombre() + " " + venta.getCliente().getApellido();
            datosTabla.addCell(celdaSinBorde("Cliente: " + nombreCliente, normalFont));
            datosTabla.addCell(celdaSinBorde("NIT: " + venta.getCliente().getNit(), normalFont));

            String direccion = venta.getCliente().getDireccion();
            datosTabla.addCell(celdaSinBorde(direccion != null && !direccion.isBlank() ? "Dirección: " + direccion : "", normalFont));
            datosTabla.addCell(celdaSinBorde("Atendido por: " + venta.getUsuario().getUserName(), normalFont));

            datosTabla.setSpacingAfter(15);
            documento.add(datosTabla);

            Map<Integer, List<SaleDetails>> porProducto = detalles.stream()
                    .collect(Collectors.groupingBy(d -> d.getProducto().getId(), LinkedHashMap::new, Collectors.toList()));

            PdfPTable productosTabla = new PdfPTable(4);
            productosTabla.setWidthPercentage(100);
            productosTabla.setWidths(new float[]{4, 1.2f, 1.8f, 1.8f});

            agregarEncabezado(productosTabla, "Producto", encabezadoTablaFont);
            agregarEncabezado(productosTabla, "Cantidad", encabezadoTablaFont);
            agregarEncabezado(productosTabla, "Precio Unitario", encabezadoTablaFont);
            agregarEncabezado(productosTabla, "Subtotal", encabezadoTablaFont);

            for (List<SaleDetails> lineas : porProducto.values()) {
                SaleDetails primera = lineas.get(0);
                int cantidadTotal = lineas.stream().mapToInt(SaleDetails::getCantidad).sum();
                BigDecimal subtotalTotal = lineas.stream()
                        .map(SaleDetails::getSubtotalLinea)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                productosTabla.addCell(celda(primera.getProducto().getCodigo() + " - " + primera.getProducto().getNombre(), normalFont, Element.ALIGN_LEFT));
                productosTabla.addCell(celda(String.valueOf(cantidadTotal), normalFont, Element.ALIGN_CENTER));
                productosTabla.addCell(celda("Q " + primera.getPrecioUnitario(), normalFont, Element.ALIGN_RIGHT));
                productosTabla.addCell(celda("Q " + subtotalTotal, normalFont, Element.ALIGN_RIGHT));
            }

            productosTabla.setSpacingAfter(15);
            documento.add(productosTabla);

            PdfPTable totalesTabla = new PdfPTable(2);
            totalesTabla.setWidthPercentage(45);
            totalesTabla.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalesTabla.setWidths(new float[]{1, 1});

            totalesTabla.addCell(celdaSinBorde("Subtotal:", etiquetaFont));
            totalesTabla.addCell(celda("Q " + venta.getSubtotal(), normalFont, Element.ALIGN_RIGHT));
            totalesTabla.addCell(celdaSinBorde("IVA (12%):", etiquetaFont));
            totalesTabla.addCell(celda("Q " + venta.getIva(), normalFont, Element.ALIGN_RIGHT));
            totalesTabla.addCell(celdaSinBorde("TOTAL:", etiquetaFont));
            totalesTabla.addCell(celda("Q " + venta.getTotal(), etiquetaFont, Element.ALIGN_RIGHT));

            documento.add(totalesTabla);

            documento.close();
            return salida.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("No se pudo generar la factura en PDF", e);
        }
    }

    private PdfPCell celdaSinBorde(String texto, Font font) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setPaddingBottom(4);
        return celda;
    }

    private PdfPCell celda(String texto, Font font, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(5);
        return celda;
    }

    private void agregarEncabezado(PdfPTable tabla, String texto, Font font) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setBackgroundColor(new Color(52, 58, 64));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(6);
        tabla.addCell(celda);
    }
}
