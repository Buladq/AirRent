package ru.bul.springs.AirRent.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.repository.FlightRepository;
import ru.bul.springs.AirRent.util.FlightPriceComparator;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    private final CityService cityService;

    private final TeamOfPilotsService teamOfPilotsService;

    public FlightService(FlightRepository flightRepository, CityService cityService, TeamOfPilotsService teamOfPilotsService) {
        this.flightRepository = flightRepository;
        this.cityService = cityService;
        this.teamOfPilotsService = teamOfPilotsService;
    }

    public List<Flight> allFlights(){
        return flightRepository.findAll();
    }

    public String changedTimeOne(Flight flight){
       String now= String.valueOf(flight.getTimeOfArrival());
       String whneed=now.substring(0,5);
       return whneed;
    }
    public String changedTimeTwo(Flight flight){
        String now= String.valueOf(flight.getTimeOfDeparture());
        String whneed=now.substring(0,5);
        return whneed;
    }

    public Flight getById(int id){
        return flightRepository.findById(id).orElse(null);
    }


  public List<Flight> findFlight(String from, String to, String datechoi,boolean sortUp,boolean sortDown) throws ParseException {
        List<Flight> whatYouNeed=new ArrayList<>();
        int idfrom= Integer.parseInt(from);
        int idto= Integer.parseInt(to);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(datechoi, formatter);



        List<Flight> allf=flightRepository.findAll();
        for (var w:
             allf) {
            if(w.getCityFrom().getId()==idfrom&&w.getCityTo().getId()==idto
            &&w.getFlightDate().equals(dt)){

                whatYouNeed.add(w);
            }
        }
        if(sortUp){
            whatYouNeed.sort(new FlightPriceComparator().reversed());
        }
        if (sortDown){
            whatYouNeed.sort(new FlightPriceComparator());
        }

        return whatYouNeed;
    }

    @Transactional
    public void MinusPlace(int idFlight){
        Flight flight=flightRepository.findById(idFlight).get();
        flight.setFreePlaces(flight.getFreePlaces()-1);
        flightRepository.save(flight);
    }

    public Page<Flight> flightsByIDTeam(Pageable pageable, int idteam){
        return flightRepository.findByIDTeam(idteam,pageable);
    }

    public Page<Flight> allFlightsByIdTeam(int idTeam,Pageable pageable){
        return flightRepository.getAllFlightsByIdTeam(idTeam,pageable);
    }

    public List<Flight> getFlyByDate(String date,int idteam){
        List<Flight> flightList=new ArrayList<>();
        List<Flight> allflighlistbyid=flightRepository.getListFlightsByIdTeam(idteam);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(date, formatter);
        for (var i:allflighlistbyid){
            if(i.getFlightDate().equals(dt)){
                flightList.add(i);
            }
        }
        return flightList;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;

    }

    private int hours(int distance){ //?????????????? ?????????? ???????????? ??????????
        double duration = distance / 800.0;
        return (int) Math.round(duration);
    }


    private String newHours(String timeFrom,int hours){
        if(hours==0){
            hours=1;
        }
        String whatNeed=timeFrom.substring(0,2);
        int wherePlus= Integer.parseInt(whatNeed);

        int res= wherePlus+hours;
        if(res>=24){
            res= res-24;
        }

        String newSt= String.valueOf(res)+":"+timeFrom.substring(3);
        if(newSt.length()==7){
            newSt="0"+newSt;
            return newSt;
        }


        return newSt;

    }

    @Transactional
    public void applicationCreate(String from,String to,String timeOfDepart,String dateFly,int teamOfPilots){
        TeamOfPilots teamOfPilots1=teamOfPilotsService.getTeamById(teamOfPilots);
        int fromId= Integer.parseInt(from);
        int toId= Integer.parseInt(to);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(dateFly, formatter);
        timeOfDepart=timeOfDepart+":00";
        LocalTime localTimeOfDep = LocalTime.parse(timeOfDepart, DateTimeFormatter.ofPattern("HH:mm:ss"));




        double d= distance(cityService.cityById(fromId).get().getLatitude(),cityService.cityById(fromId).get().getLongitude(),
                cityService.cityById(toId).get().getLatitude(),
                cityService.cityById(toId).get().getLongitude()); //??????????????????
        int distance = (int) Math.round(d); //???????? ????????????????????

        int price=distance*300; //???????? 300 ???? 1 ????

        int howManyHours=hours(distance);

        String hoursArrivForParse=newHours(timeOfDepart,howManyHours);
        LocalTime localTimeOfArriv = LocalTime.parse(hoursArrivForParse, DateTimeFormatter.ofPattern("HH:mm:ss"));

        Flight flight=new Flight();

        flight.setFreePlaces(10);
        flight.setCityFrom(cityService.cityById(fromId).get());
        flight.setCityTo(cityService.cityById(toId).get());
        flight.setDistance(distance);
        flight.setFlightDate(dt);
        flight.setTimeOfDeparture(localTimeOfDep);
        flight.setTimeOfArrival(localTimeOfArriv);
        flight.setTeamOfPilots(teamOfPilots1);
        flight.setPrice(price);
        flightRepository.save(flight);
    }
}
