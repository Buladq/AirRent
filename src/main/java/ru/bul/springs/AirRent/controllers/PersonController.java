package ru.bul.springs.AirRent.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bul.springs.AirRent.models.PersonDataPassport;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.PersonDataPassportService;
import ru.bul.springs.AirRent.services.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonDataPassportService personDataPassportService;

    private final PersonService personService;

    public PersonController(PersonDataPassportService personDataPassportService, PersonService personService) {
        this.personDataPassportService = personDataPassportService;
        this.personService = personService;
    }

    @GetMapping("/writepassport")
    public String writePassport(Model model, @ModelAttribute("passport") PersonDataPassport passport){
        return "person/passport";
    }

    @PostMapping("/writepassport")
    public String getWritePassport(Model model, @ModelAttribute("passport")@Valid PersonDataPassport passport,
                                   BindingResult bindingResult){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();

        if(bindingResult.hasErrors()){
            return "person/passport";
        }
        personDataPassportService.savePersonDataPass(passport,personDetails.getPerson());

        return "redirect:/person/personal";
    }

    @GetMapping("/personal") //личный кабинет
    public String getPersonAccount(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int id=personDetails.getPerson().getId();


        if(personDataPassportService.getPassportByIdPerson(id)==null){
           model.addAttribute("emptys","emptys");
        }
        if (personService.findPersonById(id).get().getPhoneNumber()==null){
            model.addAttribute("notPhone","notPhone");
        }
        model.addAttribute("person",personService.findPersonById(id).get());
        return "person/account";
    }
}
