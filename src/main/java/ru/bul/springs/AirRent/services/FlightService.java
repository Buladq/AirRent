package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.repository.FlightRepository;
import ru.bul.springs.AirRent.util.FlightPriceComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public String changedTimeOne(Flight flight){
       String now= String.valueOf(flight.getTimeOfArrival());
       String whneed=now.substring(0,5);
        System.out.println(whneed);
       return whneed;
    }
    public String changedTimeTwo(Flight flight){
        String now= String.valueOf(flight.getTimeOfDeparture());
        String whneed=now.substring(0,5);
        System.out.println(whneed);
        return whneed;
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
}
