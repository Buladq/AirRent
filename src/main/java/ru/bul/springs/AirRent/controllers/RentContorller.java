package ru.bul.springs.AirRent.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.AirTicketRentService;
import ru.bul.springs.AirRent.services.CityService;

import java.time.LocalDate;

@Controller
@RequestMapping("/rent")
public class RentContorller {

    private final CityService cityService;

    private final AirTicketRentService airTicketRentService;

    public RentContorller(CityService cityService, AirTicketRentService airTicketRentService) {
        this.cityService = cityService;
        this.airTicketRentService = airTicketRentService;
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


        return "fly/rentair";
    }

    @GetMapping("/{id}")
    public String infoPage(@PathVariable("id")int id, Model model){
        model.addAttribute("fly",airTicketRentService.airById(id));
        return "fly/info";
    }
}
