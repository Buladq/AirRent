package ru.bul.springs.AirRent.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.CityService;
import ru.bul.springs.AirRent.services.FlightService;
import ru.bul.springs.AirRent.services.PersonDataPassportService;
import ru.bul.springs.AirRent.services.PersonService;

import java.text.ParseException;
import java.time.LocalDate;

@Controller
@RequestMapping("/AirlineBusiness")
public class FlyController {

    private final CityService cityService;

    private final FlightService flightService;

    private final PersonService personService;

    private final PersonDataPassportService personDataPassportService;

    public FlyController(CityService cityService, FlightService flightService, PersonService personService, PersonDataPassportService personDataPassportService) {
        this.cityService = cityService;
        this.flightService = flightService;
        this.personService = personService;
        this.personDataPassportService = personDataPassportService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("cities",cityService.allCities());
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        return "fly/main";
    }

    @GetMapping("/{id}")
    public String infoPage(@PathVariable("id")int id,Model model){
        model.addAttribute("fly",flightService.getById(id));
        model.addAttribute("timeFrom",flightService.changedTimeTwo(flightService.getById(id)));
        model.addAttribute("timeTo",flightService.changedTimeOne(flightService.getById(id)));
        return "fly/info";
    }

    @GetMapping("/bying/{id}")
    public String info(@PathVariable("id")int id,Model model){
        System.out.println("покупка рейса под номером "+ id);
        model.addAttribute("idn",id);

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
        if(personDataPassportService.getPassportByIdPerson(idPerson)==null){
            model.addAttribute("notdata","notdata");
        }
        model.addAttribute("person",personService.findPersonById(idPerson).get());

        return "fly/confirm";
    }

    @GetMapping("/writepay/{id}")
    public String infoP(Model model,@PathVariable("id")int id){
        System.out.println("регистрация рейса под номером "+ id);
        return "for what";
    }

    @GetMapping("/find")
    public String findPage(Model model, @RequestParam(value = "from",required = false)String from,
                           @RequestParam(value = "to",required = false)String to,
                           @RequestParam(value = "date",required = false)String date,
                           @RequestParam(value = "expensive" ,required = false) boolean expensive,
                           @RequestParam(value = "cheap" ,required = false) boolean cheap) throws ParseException {
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        model.addAttribute("cities",cityService.allCities());

        if(!from.isEmpty()&&!to.isEmpty()&&!date.isEmpty()&&!from.equals(to)){
            model.addAttribute("fromForInput",cityService.getNameById(Integer.parseInt(from)));
            model.addAttribute("toForInput",cityService.getNameById(Integer.parseInt(to)));
            model.addAttribute("dateForInput",date);
            model.addAttribute("fromValueForInput",from);
            model.addAttribute("toValueForInput",to);
            model.addAttribute("good","good");
        }

        if(to.length()==0||date.length()==0||from.length()==0){
            model.addAttribute("theresEmpty","theresEmpty");
            return "fly/flight";

        }
        if(from.equals(to)){
            model.addAttribute("ones","ones");

            return "fly/flight";

        }
        if(flightService.findFlight(from,to,date,expensive,cheap).size()==0){
            model.addAttribute("notfly","notfly");
        }

        model.addAttribute("flightsc",flightService.findFlight(from,to,date,expensive,cheap));


        return "fly/flight";
    }


    @GetMapping("/rent")
    public String rentAir(Model model, @RequestParam(value = "from",required = false)String from,
                          @RequestParam(value = "to",required = false)String to,
                          @RequestParam(value = "date",required = false)String date,
                          @RequestParam(value = "time",required = false)String time){
        model.addAttribute("cities",cityService.allCities());
        return "fly/rentair";
    }


    @PostMapping("/rent")
    public String rentAirPage(Model model, @RequestParam(value = "from",required = false)String from,
                              @RequestParam(value = "to",required = false)String to,
                              @RequestParam(value = "date",required = false)String date,
                              @RequestParam(value = "time",required = false)String time){
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        model.addAttribute("cities",cityService.allCities());

        if(!from.isEmpty()&&!to.isEmpty()&&!date.isEmpty()&&!from.equals(to)&&!time.isEmpty()){
            System.out.println("оплата!");
        }

        if(to.length()==0||date.length()==0||from.length()==0||time.length()==0){
            model.addAttribute("theresEmpty","theresEmpty");
            return "fly/rentair";

        }

        if(from.equals(to)){
            model.addAttribute("ones","ones");

            return "fly/rentair";

        }

        return "fly/rentair";
    }
}
