package ru.bul.springs.AirRent.controllers;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.services.AirTicketPlaceService;

@Controller
public class PdfController {

    private final AirTicketPlaceService airTicketPlaceService;

    public PdfController(AirTicketPlaceService airTicketPlaceService) {
        this.airTicketPlaceService = airTicketPlaceService;
    }

    @GetMapping("/download")
    public void downloadPDF(HttpServletResponse response) throws IOException {
        // Set the response headers

        String t= airTicketPlaceService.geInfotById(9);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InfoTicket.pdf");
        response.setCharacterEncoding("UTF-8");



        // Create the PDF document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdf);


        // Add some content to the document
        document.add(new Paragraph("тестим"));


        // Close the document
        document.close();

        // Write the PDF data to the response output stream
        response.getOutputStream().write(baos.toByteArray());
    }
}
