package ru.bul.springs.AirRent.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.PersonService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/test")
public class TestController {

    private final PersonService personService;

    public TestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/check")
    public String fik(){
        List<Person> allp=personService.allPersons();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
       Optional<Person> personwhat= personService.findPersonById(personDetails.getPerson().getId());
        if(personwhat.get().getPersonDataPassport()!=null){
            return "redirect:/test";
        }
        return "for who";
    }

    @GetMapping()
    public String hola(){
        return "for what";
    }
}
