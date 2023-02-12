package ru.bul.springs.AirRent.controllers;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.AirTicketRentService;
import ru.bul.springs.AirRent.services.CityService;
import ru.bul.springs.AirRent.services.PersonDataPassportService;
import ru.bul.springs.AirRent.services.PersonService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/rent")
public class RentContorller {

    private final CityService cityService;

    private final AirTicketRentService airTicketRentService;

    private final PersonDataPassportService personDataPassportService;

    private final PersonService personService;

    public RentContorller(CityService cityService, AirTicketRentService airTicketRentService, PersonDataPassportService personDataPassportService, PersonService personService) {
        this.cityService = cityService;
        this.airTicketRentService = airTicketRentService;
        this.personDataPassportService = personDataPassportService;
        this.personService = personService;
    }

    @GetMapping()
    public String rentAir(Model model, @RequestParam(value = "from",required = false)String from,
                          @RequestParam(value = "to",required = false)String to,
                          @RequestParam(value = "date",required = false)String date,
                          @RequestParam(value = "time",required = false)String time){
        model.addAttribute("cities",cityService.allCities());
        return "fly/rentair";
    }


    @PostMapping()
    public String rentAirPage(Model model, @RequestParam(value = "from",required = false)String from,
                              @RequestParam(value = "to",required = false)String to,
                              @RequestParam(value = "date",required = false)String date,
                              @RequestParam(value = "time",required = false)String time){
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        model.addAttribute("cities",cityService.allCities());



        if(to.length()==0||date.length()==0||from.length()==0||time.length()==0){
            model.addAttribute("theresEmpty","theresEmpty");
            return "fly/rentair";

        }

        if(from.equals(to)){
            model.addAttribute("ones","ones");

            return "fly/rentair";

        }
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPer=personDetails.getPerson().getId();
        airTicketRentService.createRentTicket(from,to,date,time,idPer);
        model.addAttribute("idNow",airTicketRentService.personLastRentId(idPer));


        return "redirect:/rent/"+airTicketRentService.personLastRentId(idPer);
    }

    @GetMapping("/{id}")
    public String infoPage(@PathVariable("id")int id, Model model){
        model.addAttribute("fly",airTicketRentService.airById(id).get());
        return "rent/info";
    }



    @GetMapping("/bying/{id}")
    public String BuyPage(@PathVariable("id")int id,Model model){
        model.addAttribute("idn",id);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
        if(personDataPassportService.getPassportByIdPerson(idPerson)==null){
            model.addAttribute("notdata","notdata");
        }
        model.addAttribute("person",personService.findPersonById(idPerson).get());


        return "rent/confirm";
    }

    @PostMapping("/bying/{id}")
    public String Buy(@PathVariable("id")int id,Model model){
        model.addAttribute("idn",id);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
        airTicketRentService.CreateTicket(idPerson,id);

        return "redirect:/rent/writepay/{id}";
    }



    @GetMapping("/writepay/{id}")
    public String writeBankDataPage(Model model,@PathVariable("id")int id,@RequestParam(value = "whohave",required = false)String whohave,
                                    @RequestParam(value = "mm",required = false)String mm,
                                    @RequestParam(value = "gg",required = false)String gg,
                                    @RequestParam(value = "cvv",required = false)String cvv,
                                    @RequestParam(value = "clientInputCardnum",required = false)String clientInputCardnum){

        model.addAttribute("idn",id);
        return "rent/bankdata";
    }
    @PatchMapping("/writepay/{id}")
    public String writeBankData(Model model,@PathVariable("id")int id,@RequestParam(value = "whohave",required = false)String whohave,
                                @RequestParam(value = "mm",required = false)String mm,
                                @RequestParam(value = "gg",required = false)String gg,
                                @RequestParam(value = "cvv",required = false)String cvv,
                                @RequestParam(value = "clientInputCardnum",required = false)String clientInputCardnum){
        if (whohave.isEmpty()||clientInputCardnum.length()==0||mm.length()==0||
                gg.length()==0||cvv.length()==0){
            model.addAttribute("notfull","notfull");
            model.addAttribute("idn",id);
            return "rent/bankdata";
        }

        model.addAttribute("idn",id);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
        airTicketRentService.UpdateTicketInputBank(idPerson,id);
        return "redirect:/rent/secureWrite/{id}";
    }




    @GetMapping("/secureWrite/{id}")
    public String secureWritePage(Model model,@PathVariable("id")int id,
                                  @RequestParam(value = "card-holder",required = false)Integer cvv){
        model.addAttribute("idn",id);
        return "rent/threed";
    }

    @PatchMapping("/secureWrite/{id}")
    public String secureWrite(Model model,@PathVariable("id")int id,
                              @RequestParam(value = "card-holder",required = false)String cvv){
        if(cvv.length()==0){
            model.addAttribute("idn",id);
            model.addAttribute("didntwrite","didntwrite");
            return "rent/threed";
        }

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();

        airTicketRentService.BuyTicketAndConfThreeSec(id);

        model.addAttribute("idn",id);

        return "redirect:/rent/Ticket/" + id ;
    }

    @GetMapping("/Ticket/{idTick}")
    public String AirTicketPlaceInfo(@PathVariable("idTick")int id,Model model){
        model.addAttribute("ticket", airTicketRentService.airById(id).get());
        model.addAttribute("idford",id);
        return "rent/ticketinfo";
    }
    @GetMapping("/download/{id}")
    public void downloadPDF(HttpServletResponse response,
                            @PathVariable("id")int id) throws IOException, DocumentException {
        // Set the response headers
        String t= airTicketRentService.getInfoRentById(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InfoTicketRent.pdf");
        // Create the PDF document
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(baos));
        Document document = new Document(pdf);
        PdfFont font2= PdfFontFactory.createFont("c:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//         Add some content to the document
        document.add(new Paragraph(t).setFont(font2));


        // Close the document
        document.close();

        // Write the PDF data to the response output stream
        response.getOutputStream().write(baos.toByteArray());
    }
}
