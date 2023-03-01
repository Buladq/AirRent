package ru.bul.springs.AirRent.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.AirTicketRent;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.models.PersonDataPassport;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.AirTicketPlaceService;
import ru.bul.springs.AirRent.services.AirTicketRentService;
import ru.bul.springs.AirRent.services.PersonDataPassportService;
import ru.bul.springs.AirRent.services.PersonService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonDataPassportService personDataPassportService;

    private final PersonService personService;

    private final AirTicketPlaceService airTicketPlaceService;

    private final AirTicketRentService airTicketRentService;

    private final PasswordEncoder passwordEncoder;

    public PersonController(PersonDataPassportService personDataPassportService, PersonService personService, AirTicketPlaceService airTicketPlaceService, AirTicketRentService airTicketRentService, PasswordEncoder passwordEncoder) {
        this.personDataPassportService = personDataPassportService;
        this.personService = personService;
        this.airTicketPlaceService = airTicketPlaceService;
        this.airTicketRentService = airTicketRentService;
        this.passwordEncoder = passwordEncoder;
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

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!login.equals("anonymousUser")&&personDetails.getPerson().getRole().equals("ROLE_PILOT")){

            model.addAttribute("pilotPanel","pilotPanel");

        }


        if(personDataPassportService.getPassportByIdPerson(id)==null){
           model.addAttribute("emptys","emptys");
        }
        if (personService.findPersonById(id).get().getPhoneNumber()==null){
            model.addAttribute("notPhone","notPhone");
        }
        model.addAttribute("person",personService.findPersonById(id).get());
        return "person/account";
    }

    @GetMapping("/editPassport")
    public String editPass(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idper=personDetails.getPerson().getId();
        Person person=personService.findPersonById(idper).get();
        int idpass=person.getPersonDataPassport().getId();
        model.addAttribute("passport",personDataPassportService.findById(idpass));
        return "person/changePassport";
    }

    @PatchMapping("/editPassport")
    public String updatePass(Model model,@ModelAttribute("passport")@Valid PersonDataPassport passport,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "person/changePassport";
        }
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idper=personDetails.getPerson().getId();
        Person person=personService.findPersonById(idper).get();
        int idpass1=person.getPersonDataPassport().getId();


        personDataPassportService.updatePassport(idpass1,passport,idper);
        return "redirect:/person/personal";
    }


    @GetMapping("/addnumber")
    public String addPhonePage(Model model,@RequestParam(value = "numberPhone",required = false)String numberPhone){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Person personNeed=personService.findPersonById(personDetails.getPerson().getId()).get();

        return "person/addnumber";
    }

    @PostMapping("/addnumber")
    public String addPhone(Model model,
                           @RequestParam(value = "numberPhone",required = false)String numberPhone){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Person personNeed=personService.findPersonById(personDetails.getPerson().getId()).get();

        if(numberPhone.isEmpty()||numberPhone==null){
            model.addAttribute("mis","mis");
            return "person/addnumber";
        }
        personService.addAndChangeNumber(numberPhone,personNeed.getId());
        return "redirect:/person/personal";
    }



    @GetMapping("/changenumber")
    public String changePhonePage(Model model,@RequestParam(value = "numberPhone",required = false)String numberPhone){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Person personNeed=personService.findPersonById(personDetails.getPerson().getId()).get();

        model.addAttribute("phon",personNeed.getPhoneNumber());

        return "person/changenumber";
    }

    @PatchMapping("/changenumber")
    public String changePhone(Model model,@RequestParam(value = "numberPhone",required = false)String numberPhone){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Person personNeed=personService.findPersonById(personDetails.getPerson().getId()).get();

        model.addAttribute("phon",personNeed.getPhoneNumber());
        if(numberPhone.isEmpty()||numberPhone==null){
            model.addAttribute("mis","mis");
            return "person/changenumber";
        }
        personService.addAndChangeNumber(numberPhone,personNeed.getId());

        return "redirect:/person/personal";
    }


    @GetMapping("/changemail")
    public String changeMailPage(Model model,@RequestParam(value = "email",required = false)String email){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Person personNeed=personService.findPersonById(personDetails.getPerson().getId()).get();

        model.addAttribute("ma",personNeed.getEmail());

        return "person/changeemail";
    }

    @PatchMapping("/changemail")
    public String changeMail(Model model,@RequestParam(value = "email",required = false)String email){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Person personNeed=personService.findPersonById(personDetails.getPerson().getId()).get();
        model.addAttribute("ma",personNeed.getEmail());

        if(email.isEmpty()||email==null||personService.getPersonByMailTwo(email)==1){
            model.addAttribute("mis","mis");
            return "person/changeemail";
        }

        personService.changeEmail(email,personNeed.getId());

        return "redirect:/person/personal";
    }

    @GetMapping("/changepassword")
    public String changePassPage(Model model,@RequestParam(value = "oldpass",required = false)String oldpass,
                                 @RequestParam(value = "newpass",required = false)String newpass){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        return "person/changepassword";
    }

    @PatchMapping("/changepassword")
    public String changePass(Model model,@RequestParam(value = "oldpass",required = false)String oldpass,
                                 @RequestParam(value = "newpass",required = false)String newpass){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();

        String passNow= personDetails.getPerson().getPassword();

        if (!passwordEncoder.matches(oldpass,passNow)){
            model.addAttribute("badsolve","badsolve");
            return "person/changepassword";

        }

        if(newpass.isEmpty()||newpass==null){
            model.addAttribute("pasisnul","pasisnul");
            return "person/changepassword";
        }
        personService.changePassword(newpass,personDetails.getPerson().getId());
        return "redirect:/person/personal";
    }


    @GetMapping("/alltickets")
    public String allTickets(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int perId=personDetails.getPerson().getId();
        List<AirTicketPlace> airTicketPlaceList=airTicketPlaceService.listBought(perId);
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!login.equals("anonymousUser")&&personDetails.getPerson().getRole().equals("ROLE_PILOT")){

            model.addAttribute("pilotPanel","pilotPanel");

        }

        if(airTicketPlaceList.size()==0){
            model.addAttribute("didn","didn");
        }
        model.addAttribute("ticket",airTicketPlaceList);

        return "person/tickets";
    }

    @GetMapping("/allrents")
    public String allRents(Model model,@RequestParam(defaultValue = "0") int page){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!login.equals("anonymousUser")&&personDetails.getPerson().getRole().equals("ROLE_PILOT")){

            model.addAttribute("pilotPanel","pilotPanel");

        }
        Pageable pageable = PageRequest.of(page, 3);
        int perId=personDetails.getPerson().getId();

        Page<AirTicketRent> airTicketRentPage = airTicketRentService.allRentedTickets(perId, pageable);
        List<AirTicketRent> airTicketRentList = airTicketRentPage.getContent();


        if(airTicketRentList.size()==0){
            model.addAttribute("didn","didn");
        }
        model.addAttribute("ticket",airTicketRentList);

        model.addAttribute("currentPage", page);

        model.addAttribute("totalPages", airTicketRentPage.getTotalPages());

        return "person/rents";
    }

}
