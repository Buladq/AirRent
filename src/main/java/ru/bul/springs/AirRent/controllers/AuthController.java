package ru.bul.springs.AirRent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            return "auth/registration";
        }

        registrationService.registred(person);



        return "redirect:auth/login";
    }


//    @GetMapping("/forget")
//    public String forgetPass(Model model, @RequestParam(value = "ema",required = false)String ema){
//        return "auth/forget";
//    }
//    @PostMapping("/forget")
//    public String forgetPasspage(Model model, @RequestParam(value = "ema",required = false)String ema){
//        if(ema!=null){
//            if(personService.getMailByMail(ema)!=null){
//                System.out.println(personService.getMailByMail(ema));
//                model.addAttribute("sent","sent");
//                personService.changePass(ema);
//            }
//            else {
//                model.addAttribute("notfoun","notfoun");
//            }
//
//        }
//        else if(ema==null) {
//            model.addAttribute("notwrite","notwrite");
//        }
//
//        return "auth/forget";
//    }
}
