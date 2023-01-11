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
@RequestMapping("/check")
public class CheckController {

    private final PersonService personService;

    public CheckController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/verif")
    public String fik(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
       Optional<Person> personwhat= personService.findPersonById(personDetails.getPerson().getId());
        if(personwhat.get().getPersonDataPassport()!=null){
            return "redirect:/person/personal";
        }
        return "redirect:/person/writepassport";
    }


}
