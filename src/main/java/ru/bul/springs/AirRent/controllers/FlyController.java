package ru.bul.springs.AirRent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bul.springs.AirRent.services.CityService;

import java.time.LocalDate;

@Controller
@RequestMapping("/AirlineBusiness")
public class FlyController {

    private final CityService cityService;

    public FlyController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/main")
    public String mainPage(Model model){
        model.addAttribute("cities",cityService.allCities());
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        return "fly/main";
    }

    @GetMapping("/find")
    public String findPage(Model model, @RequestParam(value = "from",required = false)String from,
                           @RequestParam(value = "to",required = false)String to,
                           @RequestParam(value = "date",required = false)String date){
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        model.addAttribute("cities",cityService.allCities());

        if(!from.isEmpty()||!to.isEmpty()||!date.isEmpty()){
            model.addAttribute("fromForInput",cityService.getNameById(Integer.parseInt(from)));
            model.addAttribute("toForInput",cityService.getNameById(Integer.parseInt(to)));
            model.addAttribute("dateForInput",date);

            model.addAttribute("fromValueForInput",from);
            model.addAttribute("toValueForInput",to);
        }

        if(from.isEmpty()||to.isEmpty()||date.isEmpty()){
            System.out.println("пусто");
            model.addAttribute("theresEmpty","theresEmpty");
            return "fly/flight";

        }
        if(from.equals(to)){
            System.out.println("Одинаковые значения");
            model.addAttribute("ones","ones");
            return "fly/flight";

        }
        System.out.println(from);
        System.out.println(to);
        System.out.println(date);
        return "fly/flight";
    }
}
