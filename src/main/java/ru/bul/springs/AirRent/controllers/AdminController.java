package ru.bul.springs.AirRent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public String youAre(){
        return "admin/page";
    }
}
