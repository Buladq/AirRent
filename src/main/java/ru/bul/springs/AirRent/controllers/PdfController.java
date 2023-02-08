package ru.bul.springs.AirRent.controllers;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PdfController {
    @GetMapping("/download")
    public void downloadPDF(HttpServletResponse response) throws IOException {
        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=HelloWorld.pdf");

        // Create the PDF document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdf);

        // Add some content to the document
        document.add(new Paragraph("Hello World"));


        // Close the document
        document.close();

        // Write the PDF data to the response output stream
        response.getOutputStream().write(baos.toByteArray());
    }
}
