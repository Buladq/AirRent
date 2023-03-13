package ru.bul.springs.AirRent.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.services.PersonService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonService personService;

    public AdminController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String adminPage(Model model){
        return "admin/adminpage";
    }

    @GetMapping("/users")
    public String allUsers(Model model, @RequestParam(value = "email",required = false)String email,
            @RequestParam(defaultValue = "0") int page){



        if(email != null&&personService.getPersonWithMailString(email).isPresent()){
            model.addAttribute("emailWrited",email);
            model.addAttribute("emailIs","emailIs");
            model.addAttribute("personByMail",personService.getPersonWithMailString(email).get());

        }
        else if(email==null||email.equals("")){
            model.addAttribute("allUs","allUs");
            Pageable pageable= PageRequest.of(page,8);
            Page<Person> allUsers=personService.pageUsers(pageable);
            List<Person> allUsersList=allUsers.getContent();

            model.addAttribute("currentPage", page);

            model.addAttribute("totalPages", allUsers.getTotalPages());


            model.addAttribute("users",allUsersList);


        }
        else {
           model.addAttribute("thereEmpty","thereEmpty");
        }


        return "admin/allusers";
    }


    @PatchMapping("/ban/{id}")
    public String banUser(@PathVariable("id") int id) {
        personService.banUser(id);
        return "redirect:/admin/users";
    }


}
