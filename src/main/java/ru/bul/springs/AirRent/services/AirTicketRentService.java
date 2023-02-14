package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.*;
import ru.bul.springs.AirRent.repository.AirTicketRentRepository;
import ru.bul.springs.AirRent.util.TicketBuy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AirTicketRentService implements TicketBuy {
    private final AirTicketRentRepository airTicketRentRepository;

    private final CityService cityService;
    private final PersonService personService;

    private final FlightService flightService;

    private final TeamOfPilotsService teamOfPilotsService;

    private final TimingOfPilotsService timingOfPilotsService;

    public AirTicketRentService(AirTicketRentRepository airTicketRentRepository, CityService cityService, PersonService personService, FlightService flightService, TeamOfPilotsService teamOfPilotsService, TimingOfPilotsService timingOfPilotsService) {
        this.airTicketRentRepository = airTicketRentRepository;
        this.cityService = cityService;
        this.personService = personService;
        this.flightService = flightService;
        this.teamOfPilotsService = teamOfPilotsService;
        this.timingOfPilotsService = timingOfPilotsService;
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

    @Override
    @Transactional
    public void CreateTicket(int idPer, int idFl) {
        AirTicketRent airTicketRent=airTicketRentRepository.findById(idFl).get();
        airTicketRent.setConfData(true);
       airTicketRentRepository.save(airTicketRent);
    }

    @Override
    @Transactional
    public void UpdateTicketInputBank(int idPer, int idTicket) {
        AirTicketRent airTicketRent=airTicketRentRepository.findById(idTicket).get();
        airTicketRent.setBankData(true);
        airTicketRentRepository.save(airTicketRent);
    }

    @Override
    @Transactional
    public void BuyTicketAndConfThreeSec(int tick) {
        AirTicketRent airTicketRent=airTicketRentRepository.findById(tick).get();
        airTicketRent.setPaid(true);
        airTicketRent.setTeamOfPilots(getTeam(tick));
        airTicketRentRepository.save(airTicketRent);
        if(getTeam(tick)!=null){
            timingOfPilotsService.CreateNewTiming(airTicketRent.getTeamOfPilots(),airTicketRent.getRentFlyDate());
        }

    }

   public TeamOfPilots getTeam(int idAirTicket){
        AirTicketRent airTicketRent=airTicketRentRepository.findById(idAirTicket).get();
        List<TimingOfPilots> timingOfPilots=timingOfPilotsService.all();
        for (var w:timingOfPilots){
           if(airTicketRent.getRentFlyDate().equals(w.getBusyOfDate())){
               return null;
           }
        }
       for (var w1:timingOfPilots){
           if(!airTicketRent.getRentFlyDate().equals(w1.getBusyOfDate())){
               return w1.getTeamOfPilots();
           }
       }

        return null;
    }

    public String getInfoRentById(int id){
        AirTicketRent airTicketRent= airTicketRentRepository.findById(id).get();
        String from=airTicketRent.getCityFrom().getName();
        String to=airTicketRent.getCityTo().getName();

        String fromAir=airTicketRent.getCityFrom().getAirportName();
        String toAir=airTicketRent.getCityTo().getAirportName();
        String s="ID:= "+airTicketRent.getId()+"\n"
                +"Passenger name: "+airTicketRent.getPerson().getUsername()+"\n "
                +"Status: aircraft rental"+"\n "
                +"Departure city: "+from+"\n"
                +"airport from: "+fromAir+"\n"
                +"Arrival city: "+to+"\n"
                +"airport to: "+toAir+"\n"
                +"Departure date: "+ airTicketRent.getDistance()+"\n"
                +"Departure time: "+airTicketRent.getTimeOfDeparture()+"\n"+
                "Arrival time: "+airTicketRent.getTimeOfArrival()+"\n"+
                "Distance: "+airTicketRent.getDistance()+"\n";
        return s;
    }

    public List<AirTicketRent> allRentedTickets(int person){
        return airTicketRentRepository.AirTicketRentBought(person);
    }
}
