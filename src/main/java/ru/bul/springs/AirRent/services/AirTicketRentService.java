package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.AirTicketRent;
import ru.bul.springs.AirRent.repository.AirTicketRentRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AirTicketRentService {
    private final AirTicketRentRepository airTicketRentRepository;

    private final CityService cityService;
    private final PersonService personService;

    public AirTicketRentService(AirTicketRentRepository airTicketRentRepository, CityService cityService, PersonService personService) {
        this.airTicketRentRepository = airTicketRentRepository;
        this.cityService = cityService;
        this.personService = personService;
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

    private int hours(int distance){

        double duration = distance / 800.0;
        return (int) Math.round(duration);
    }

    private String newHours(String timeFrom,int hours){
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

    public Optional<AirTicketRent> airById(int id){
        return airTicketRentRepository.findById(id);
    }

    @Transactional
    public void createRentTicket(String from,String to,String datechoi,String timechoi,int idPerson){
        int idfrom= Integer.parseInt(from);
        int idto= Integer.parseInt(to);
        timechoi=timechoi+":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(datechoi, formatter);
        LocalTime localTime = LocalTime.parse(timechoi, DateTimeFormatter.ofPattern("HH:mm:ss"));

        AirTicketRent airTicketRent=new AirTicketRent();
        airTicketRent.setPerson(personService.findPersonById(idPerson).get());
        airTicketRent.setTimeOfDeparture(localTime);
        airTicketRent.setRentFlyDate(dt);
        airTicketRent.setCityFrom(cityService.cityById(idfrom).get());
        airTicketRent.setCityTo(cityService.cityById(idto).get());

       double d= distance(cityService.cityById(idfrom).get().getLatitude(),cityService.cityById(idfrom).get().getLongitude(),
                cityService.cityById(idto).get().getLatitude(),
                cityService.cityById(idto).get().getLongitude()); //дистанция

        int distance = (int) Math.round(d); //Дист округление

        int price=distance*1540; //цена 1540 за 1 км


       int plusHours= hours(distance);


       String timeOfArriv= newHours(timechoi,plusHours);

        LocalTime localTimeArriv = LocalTime.parse(timeOfArriv, DateTimeFormatter.ofPattern("HH:mm:ss"));




        airTicketRent.setTimeOfArrival(localTimeArriv);
        airTicketRent.setDistance(distance);
        airTicketRent.setPrice(price);
        airTicketRentRepository.save(airTicketRent);



    }

    public Integer personLastRentId(int id){
        return airTicketRentRepository.getLastIdTicketRentByIdPerson(id);
    }

}
