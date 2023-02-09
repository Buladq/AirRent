package ru.bul.springs.AirRent.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.*;

import java.text.ParseException;
import java.time.LocalDate;

@Controller
@RequestMapping("/AirlineBusiness")
public class FlyController {

    private final CityService cityService;

    private final FlightService flightService;

    private final PersonService personService;

    private final AirTicketPlaceService airTicketPlaceService;

    private final PersonDataPassportService personDataPassportService;

    public FlyController(CityService cityService, FlightService flightService, PersonService personService, AirTicketPlaceService airTicketPlaceService, PersonDataPassportService personDataPassportService) {
        this.cityService = cityService;
        this.flightService = flightService;
        this.personService = personService;
        this.airTicketPlaceService = airTicketPlaceService;
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

    @GetMapping ("/job")
    public String showFormForJobPage(Model model,@RequestParam(value = "fullname",required = false)String fio,@RequestParam(value = "email",required = false)String ema,
                                     @RequestParam(value = "tel",required = false)String tel,@RequestParam(value = "message",required = false)String about){

        return "fly/jobform";
    }
    @PostMapping ("/job")
    public String showFormForJob(Model model,@RequestParam(value = "fullname",required = false)String fio,@RequestParam(value = "email",required = false)String ema,
                                 @RequestParam(value = "tel",required = false)String tel,@RequestParam(value = "message",required = false)String about){
        if(fio.length()==0||ema.length()==0||tel.length()==0||about.length()==0){
            model.addAttribute("didnt","didnt");
            return "fly/jobform";
        }
        model.addAttribute("wrote","wrote");
        return "fly/jobform";
    }

    @GetMapping("/{id}")
    public String infoPage(@PathVariable("id")int id,Model model){
        model.addAttribute("fly",flightService.getById(id));
        model.addAttribute("timeFrom",flightService.changedTimeTwo(flightService.getById(id)));
        model.addAttribute("timeTo",flightService.changedTimeOne(flightService.getById(id)));
        model.addAttribute("places",flightService.getById(id).getFreePlaces());
        return "fly/info";
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


        return "fly/confirm";
    }

    @PostMapping("/bying/{id}")
    public String Buy(@PathVariable("id")int id,Model model){
        model.addAttribute("idn",id);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
        airTicketPlaceService.CreateTicket(idPerson,id);

        return "redirect:/AirlineBusiness/writepay/{id}";
    }

    @GetMapping("/writepay/{id}")
    public String writeBankDataPage(Model model,@PathVariable("id")int id,@RequestParam(value = "whohave",required = false)String whohave,
                        @RequestParam(value = "mm",required = false)String mm,
                        @RequestParam(value = "gg",required = false)String gg,
                        @RequestParam(value = "cvv",required = false)String cvv,
                        @RequestParam(value = "clientInputCardnum",required = false)String clientInputCardnum){

        model.addAttribute("idn",id);
        return "fly/bankdata";
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
            return "fly/bankdata";
        }

        model.addAttribute("idn",id);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
       int idTick= airTicketPlaceService.getLastIdTicketByIdPerson(idPerson);
        airTicketPlaceService.UpdateTicketInputBank(idPerson,idTick);
        return "redirect:/AirlineBusiness/secureWrite/{id}";
    }

    @GetMapping("/secureWrite/{id}")
    public String secureWritePage(Model model,@PathVariable("id")int id,
            @RequestParam(value = "card-holder",required = false)Integer cvv){
        model.addAttribute("idn",id);
        return "fly/threed";
    }

    @PatchMapping("/secureWrite/{id}")
    public String secureWrite(Model model,@PathVariable("id")int id,
            @RequestParam(value = "card-holder",required = false)String cvv){
        if(cvv.length()==0){
            model.addAttribute("idn",id);
            model.addAttribute("didntwrite","didntwrite");
            return "fly/threed";
        }

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idPerson=personDetails.getPerson().getId();
        int idTick= airTicketPlaceService.getLastIdTicketByIdPerson(idPerson);
        airTicketPlaceService.BuyTicketAndConfThreeSec(idTick);
        model.addAttribute("tickId",idTick);
        model.addAttribute("idn",id);
        return "fly/threed";
    }

//    @GetMapping("/Ticket/{id}")
//    public String AirTicketPlaceInfo(@PathVariable("id")int id,Model model){
//
//    }

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
            model.addAttribute("flightsc",flightService.findFlight(from,to,date,expensive,cheap));
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
