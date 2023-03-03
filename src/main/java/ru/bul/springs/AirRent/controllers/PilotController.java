package ru.bul.springs.AirRent.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.secutiry.PersonDetails;
import ru.bul.springs.AirRent.services.CityService;
import ru.bul.springs.AirRent.services.FlightService;
import ru.bul.springs.AirRent.services.TeamOfPilotsService;
import ru.bul.springs.AirRent.services.TimingOfPilotsService;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/pilot")
public class PilotController {

    private final TeamOfPilotsService teamOfPilotsService;

    private final TimingOfPilotsService timingOfPilotsService;

    private final CityService cityService;

    private final FlightService flightService;

    public PilotController(TeamOfPilotsService teamOfPilotsService, TimingOfPilotsService timingOfPilotsService, CityService cityService, FlightService flightService) {
        this.teamOfPilotsService = teamOfPilotsService;
        this.timingOfPilotsService = timingOfPilotsService;
        this.cityService = cityService;
        this.flightService = flightService;
    }

    @GetMapping
    public String pilotPanel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if (teamOfPilotsService.role(personDetails.getPerson().getId()) == 2) {
            model.addAttribute("second", "second");
        }
        if (teamOfPilotsService.inTeam(personDetails.getPerson().getId()) == 0) {
            model.addAttribute("noTeam", "noTeam");
        }
        int IDTEAM = teamOfPilotsService.idTeamPilotByPilot(personDetails.getPerson().getId());
        if (IDTEAM != 0) {
            timingOfPilotsService.BusyDateByPilot(IDTEAM);
        }

        return "pilot/panelpilot";
    }

    @GetMapping("/application")
    public String appPage(Model model, @RequestParam(value = "from", required = false) String from,
                          @RequestParam(value = "to", required = false) String to,
                          @RequestParam(value = "date", required = false) String date,
                          @RequestParam(value = "time", required = false) String time) {
        model.addAttribute("cities", cityService.allCities());
        LocalDate localDate = LocalDate.now();
        String nowDate = localDate.toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("nowMin", nowDate);
        return "pilot/application";
    }

    @PostMapping("/application")
    public String app(Model model, @RequestParam(value = "from", required = false) String from,
                      @RequestParam(value = "to", required = false) String to,
                      @RequestParam(value = "date", required = false) String date,
                      @RequestParam(value = "time", required = false) String time) throws ParseException {
        model.addAttribute("cities", cityService.allCities());
        LocalDate localDate = LocalDate.now();
        String nowDate = localDate.toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("nowMin", nowDate);
        if (nowDate.equals(date)) {
            model.addAttribute("datetoday", "datetoday");
            return "pilot/application";
        }

        if (to.length() == 0 || date.length() == 0 || from.length() == 0 || time.length() == 0) {
            model.addAttribute("theresEmpty", "theresEmpty");
            return "pilot/application";
        }
        if (from.equals(to)) {
            model.addAttribute("equ", "equ");
            return "pilot/application";
        }


        int idTeam = teamOfPilotsService.idTeamPilotByPilot(personDetails.getPerson().getId());
        if (!timingOfPilotsService.busOrNo(idTeam, date)) {
            model.addAttribute("busy", "busy");
            return "pilot/application";
        }
        flightService.applicationCreate(from, to, time, date, idTeam);
        model.addAttribute("approved", "approved");

        return "pilot/application";
    }

    @GetMapping("/infoteam")
    public String infoAboutTeam(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int idPerson = personDetails.getPerson().getId();
        int IdTeam = teamOfPilotsService.idTeamPilotByPilot(idPerson);
        model.addAttribute("first", teamOfPilotsService.getTeamById(IdTeam).getMainPilot());
        model.addAttribute("second", teamOfPilotsService.getTeamById(IdTeam).getSecondPilot());
        return "pilot/infoaboutteam";
    }

    @GetMapping("/allflights")
    public String allFlights(Model model, @RequestParam(value = "datek", required = false) String datek,
                             @RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int idPerson = personDetails.getPerson().getId();
        int IdTeam = teamOfPilotsService.idTeamPilotByPilot(idPerson);
        TeamOfPilots teamOfPilots = teamOfPilotsService.getTeamById(IdTeam);


        if (teamOfPilots.getTeams().size() == 0) {
            model.addAttribute("noFl", "noFl");
        }
        else if((teamOfPilots.getTeams().size() != 0&&datek==null)||datek.isEmpty()){
            model.addAttribute("fl", "fl");
            Pageable pageable= PageRequest.of(page,5);
            Page<Flight> flights=flightService.allFlightsByIdTeam(IdTeam,pageable);
            List<Flight> flightList=flights.getContent();
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", flights.getTotalPages());
            model.addAttribute("fly",flightList);
        }
        else {
            model.addAttribute("search","search");
            model.addAttribute("flysear",flightService.getFlyByDate(datek,IdTeam));
        }



        return "pilot/allflights";
    }
}
