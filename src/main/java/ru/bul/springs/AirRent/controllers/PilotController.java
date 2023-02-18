package ru.bul.springs.AirRent.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.TeamOfPilotsService;

@Controller
@RequestMapping("/pilot")
public class PilotController {

    private final TeamOfPilotsService teamOfPilotsService;

    public PilotController(TeamOfPilotsService teamOfPilotsService) {
        this.teamOfPilotsService = teamOfPilotsService;
    }

    @GetMapping
    public String pilotPanel(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        System.out.println(teamOfPilotsService.role(personDetails.getPerson().getId()));
        if(teamOfPilotsService.role(personDetails.getPerson().getId())==2){
            model.addAttribute("second","second");
        }
        if (teamOfPilotsService.inTeam(personDetails.getPerson().getId())==0){
            model.addAttribute("noTeam","noTeam");
        }

        return "pilot/panelpilot";
    }
}
