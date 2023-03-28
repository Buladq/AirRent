package ru.bul.springs.AirRent.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.AirRent.models.AirTicketRent;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonService personService;

    private final TimingOfPilotsService timingOfPilotsService;

    private final FlightService flightService;

    private final CityService cityService;

    private final TeamOfPilotsService teamOfPilotsService;

    private final AirTicketRentService airTicketRentService;

    private final PilotService pilotService;

    public AdminController(PersonService personService, TimingOfPilotsService timingOfPilotsService, FlightService flightService, CityService cityService, TeamOfPilotsService teamOfPilotsService, AirTicketRentService airTicketRentService, PilotService pilotService) {
        this.personService = personService;
        this.timingOfPilotsService = timingOfPilotsService;
        this.flightService = flightService;
        this.cityService = cityService;
        this.teamOfPilotsService = teamOfPilotsService;
        this.airTicketRentService = airTicketRentService;
        this.pilotService = pilotService;
    }

    @GetMapping()
    public String adminPage(Model model) {
        return "admin/adminpage";
    }

    @GetMapping("/users")
    public String allUsers(Model model, @RequestParam(value = "email", required = false) String email,
                           @RequestParam(defaultValue = "0") int page) {


        if (email != null && personService.getPersonWithMailString(email).isPresent()) {
            model.addAttribute("emailWrited", email);
            model.addAttribute("emailIs", "emailIs");
            model.addAttribute("personByMail", personService.getPersonWithMailString(email).get());

        } else if (email == null || email.equals("")) {
            model.addAttribute("allUs", "allUs");
            Pageable pageable = PageRequest.of(page, 8);
            Page<Person> allUsers = personService.pageUsers(pageable);
            List<Person> allUsersList = allUsers.getContent();

            model.addAttribute("currentPage", page);

            model.addAttribute("totalPages", allUsers.getTotalPages());


            model.addAttribute("users", allUsersList);


        } else {
            model.addAttribute("thereEmpty", "thereEmpty");
        }


        return "admin/allusers";
    }


    @PatchMapping("/ban/{id}")
    public String banUser(@PathVariable("id") int id) {
        personService.banUser(id);
        return "redirect:/admin/users";
    }


    @GetMapping("/pillappli")
    public String pilotsApplication(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<Flight> flightPageForAppl = flightService.flyForApp(pageable);
        List<Flight> allFlForApp = flightPageForAppl.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flightPageForAppl.getTotalPages());

        model.addAttribute("fly", allFlForApp);
        return "admin/pilotaplications";
    }

    @DeleteMapping("/del/fly/{id}")
    public String negativуFlight(@PathVariable("id") int id) {
        flightService.delFly(id);
        return "redirect:/admin/pillappli";
    }

    @PatchMapping("/accept/{id}")
    public String acceptFly(@PathVariable("id") int id) {
        flightService.acceptFly(id);
        return "redirect:/admin/pillappli";
    }

    @GetMapping("/rent/{id}")
    public String pageRentAirAdm(@PathVariable("id") int id, Model model) {
        model.addAttribute("rent", airTicketRentService.airById(id).get());
        if (airTicketRentService.getTeamPilotsByIdRent(id) == null) {
            model.addAttribute("without", "without");
            model.addAttribute("allpilots", teamOfPilotsService.allTeams());
        } else {
            model.addAttribute("notwit", "notwit");
            model.addAttribute("team", airTicketRentService.getTeamPilotsByIdRent(id));
        }

        model.addAttribute("user", airTicketRentService.getPersonByIdRent(id));


        return "admin/rentinfo";
    }

    @GetMapping("/fly/{id}")
    public String pageFlyAirAdm(@PathVariable("id") int id, Model model) {
        model.addAttribute("fly", flightService.getById(id));
        model.addAttribute("team", flightService.getPilotByIdFlight(id));
        model.addAttribute("users", flightService.getPersonsAndPlacesByIdFlight(id));
        return "admin/flyinfo";
    }

    @GetMapping("/allflyghts")
    public String pageAllFly(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(value = "idw", required = false) Integer idw) {

        if (idw != null && flightService.getById(idw) != null) {
            model.addAttribute("idwrited", idw);
            model.addAttribute("ids", "ids");
            model.addAttribute("flyById", flightService.getById(idw));

        } else if (idw == null || idw.equals("")) {
            model.addAttribute("allF", "allF");
            Pageable pageable = PageRequest.of(page, 8);
            Page<Flight> allFlights = flightService.flightPage(pageable);
            List<Flight> allFLightList = allFlights.getContent();

            model.addAttribute("currentPage", page);

            model.addAttribute("totalPages", allFlights.getTotalPages());


            model.addAttribute("fly", allFLightList);


        } else if (idw != null && flightService.getById(idw) == null) {
            model.addAttribute("thereEmpty", "thereEmpty");
        } else {
            model.addAttribute("thereEmpty", "thereEmpty");
        }


        return "admin/allfly";
    }

    @GetMapping("/allrents")
    public String pageAllrents(Model model, @RequestParam(defaultValue = "0") int page,
                               @RequestParam(value = "idw", required = false) Integer idw) {

        if (idw != null && airTicketRentService.getByIdAndPaid(idw) != null) {
            model.addAttribute("idwrited", idw);
            model.addAttribute("ids", "ids");
            model.addAttribute("rentById", airTicketRentService.airById(idw).get());

        } else if (idw == null || idw.equals("")) {
            model.addAttribute("allF", "allF");
            Pageable pageable = PageRequest.of(page, 8);
            Page<AirTicketRent> allRents = airTicketRentService.getAllPage(pageable);
            List<AirTicketRent> allFLightList = allRents.getContent();

            model.addAttribute("currentPage", page);

            model.addAttribute("totalPages", allRents.getTotalPages());


            model.addAttribute("rents", allFLightList);


        } else if (idw != null && airTicketRentService.getByIdAndPaid(idw) == null) {
            model.addAttribute("thereEmpty", "thereEmpty");
        } else {
            model.addAttribute("thereEmpty", "thereEmpty");
        }


        return "admin/allrent";
    }

    @GetMapping("/rentwitpilot")
    public String pageWithoutPilots(Model model) {
        model.addAttribute("rentsWithout", airTicketRentService.getAllWithoutPilot());
        return "admin/rentapplication";
    }


    @PostMapping("/test/{id}/dat/{date}")
    public String pageAllrents(Model model,@PathVariable("id")int id,
                               @PathVariable(value = "date") String date,
                               @RequestParam(value = "pilots", required = false) Integer pilots) {

        airTicketRentService.inputPilotByIdRent(id,pilots);
        timingOfPilotsService.CreateNewTimingByString(pilots,date);

        return "redirect:/admin/rent/{id}";
    }


    @GetMapping("/createfly")
    public String createFlyPage(Model model,@RequestParam(value ="idteam" ,required = false)Integer idteam,
                                @RequestParam(value = "from",required = false)String from,
                                @RequestParam(value ="to" ,required = false)String to,
                                @RequestParam(value = "time",required = false)String time,
                                @RequestParam(value = "date",required = false)String date){
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        model.addAttribute("cities",cityService.allCities());
        model.addAttribute("pilots",teamOfPilotsService.allTeams());
        return "admin/createfly";
    }


    @PostMapping("/createfly")
    public String createFly(Model model,@RequestParam(value ="idteam" ,required = false)Integer idteam,
                                @RequestParam(value = "from",required = false)String from,
                                @RequestParam(value ="to" ,required = false)String to,
                                @RequestParam(value = "time",required = false)String time,
                                @RequestParam(value = "date",required = false)String date){
        LocalDate localDate=LocalDate.now();
        String nowDate=localDate.toString();
        model.addAttribute("nowMin",nowDate);
        model.addAttribute("cities",cityService.allCities());
        model.addAttribute("pilots",teamOfPilotsService.allTeams());
        if(from.equals(to)){
            model.addAttribute("equ","equ");
            return "admin/createfly";
        }
        if(from.isEmpty()||to.isEmpty()||time.isEmpty()||date.isEmpty()||idteam==null){
            model.addAttribute("theresEmpty","theresEmpty");
            return "admin/createfly";
        }
        flightService.flyCreate(from,to,time,date,idteam);
        timingOfPilotsService.CreateNewTimingByString(idteam,date);
        model.addAttribute("approved","approved");

        return "admin/createfly";
    }


    @GetMapping("/allpilots")
    public String allPilotsPage(Model model,@RequestParam(value = "idTeam",required = false)Integer idTeam){

        if(idTeam!=null&&teamOfPilotsService.getTeamByIdOpt(idTeam)!=null){
            model.addAttribute("idTeam",idTeam);
            model.addAttribute("idIs","idIs");
            model.addAttribute("thatTeam",teamOfPilotsService.getTeamById(idTeam));
        }
        else if(idTeam==null){
            model.addAttribute("notThat","notThat");
            model.addAttribute("pilots",teamOfPilotsService.allTeams());
        }
        else {
            model.addAttribute("thereEmpty","thereEmpty");
        }

        return "admin/allpilots";
    }

    @GetMapping("/team/{id}")
    public String teamPage(@PathVariable("id")int id,Model model){
        model.addAttribute("team",teamOfPilotsService.getTeamById(id));
        model.addAttribute("pil1",teamOfPilotsService.getTeamById(id).getMainPilot());
        model.addAttribute("pil2",teamOfPilotsService.getTeamById(id).getSecondPilot());
        return "admin/infoaboutteambyadmin";
    }

    @GetMapping("/newpilot")
    public String createPilotPage(Model model,@RequestParam(value = "age",required = false)Integer age,
                                  @RequestParam(value = "exper",required = false)Integer exper,
                                  @RequestParam(value = "file",required = false) MultipartFile file,
                                  @RequestParam(value = "idUser",required = false)Integer idUser){
        model.addAttribute("users",personService.personListUser());
        return "admin/newpilot";
    }

    @PostMapping("/newpilot")
    public String createPilot(Model model,@RequestParam(value = "age",required = false)Integer age,
                                  @RequestParam(value = "exper",required = false)Integer exper,
                                  @RequestParam(value = "file",required = false) MultipartFile file,
                                  @RequestParam(value = "idUser",required = false)Integer idUser) throws IOException {
        model.addAttribute("users",personService.personListUser());
        if(age==null||exper==null||file.isEmpty()||idUser==null){
            model.addAttribute("thereEmpty","thereEmpty");
            return "admin/newpilot";
        }
        personService.installStatusPulot(idUser);
        pilotService.newPilot(file,idUser,age,exper);
        model.addAttribute("approved","approved");
        return "admin/newpilot";
    }
}




