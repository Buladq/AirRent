package ru.bul.springs.AirRent.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pilot")
public class PilotController {

    @GetMapping
    public String pil(){
        return "pilot/pilpage";
    }
}
