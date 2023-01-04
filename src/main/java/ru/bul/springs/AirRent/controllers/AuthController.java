package ru.bul.springs.AirRent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.services.PersonService;
import ru.bul.springs.AirRent.services.RegistrationService;
import ru.bul.springs.AirRent.util.MailSender;
import ru.bul.springs.AirRent.util.PersonValidator;


import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;

    private final PersonService personService;

    private final MailSender mailSender;

    public AuthController(PersonValidator personValidator, RegistrationService registrationService, PersonService personService, MailSender mailSender) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personService = personService;
        this.mailSender = mailSender;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";

    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person")@Valid Person person,
                                      BindingResult bindingResult,Model model){
        personValidator.validate(person,bindingResult);

        if(personService.getPersonByMail(person.getEmail())!=null){
            model.addAttribute("er","er");
            return "auth/registration";
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("erik","erik");
            return "auth/registration";
        }

        registrationService.registred(person);



        return "redirect:auth/login";
    }


    @GetMapping("/forget")
    public String forgetPass(Model model, @RequestParam(value = "ema",required = false)String ema){
        return "auth/forgot";
    }
    @PostMapping("/forget")
    public String forgetPasspage(Model model, @RequestParam(value = "ema",required = false)String ema){

        model.addAttribute("ema",ema);
        if(ema.length()!=0){
            if(personService.getPersonByMail(ema)!=null){
                model.addAttribute("sent","sent");

            }
            else {
                model.addAttribute("notfoun","notfoun");

            }

        }
        else if(ema.length()==0||ema.equals(" ")){
            model.addAttribute("notwrite","notwrite");

        }

        return "auth/forgot";
    }
}
