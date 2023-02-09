package ru.bul.springs.AirRent.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.repository.AirTicketPlaceRepository;
import ru.bul.springs.AirRent.util.TicketBuy;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AirTicketPlaceService implements TicketBuy {

    private final AirTicketPlaceRepository airTicketPlaceRepository;

    private final FlightService flightService;

    private final PersonService personService;

    public AirTicketPlaceService(AirTicketPlaceRepository airTicketPlaceRepository, FlightService flightService, PersonService personService) {
        this.airTicketPlaceRepository = airTicketPlaceRepository;
        this.flightService = flightService;
        this.personService = personService;
    }

    @Override
    @Transactional
    public void CreateTicket(int idPer, int idFl) {
        AirTicketPlace airTicketPlace=new AirTicketPlace();
        Flight flight=flightService.getById(idFl);
        Person person=personService.findPersonById(idPer).get();
        airTicketPlace.setFlight(flight);
        airTicketPlace.setPerson(person);
        airTicketPlace.setConfData(true);
        airTicketPlaceRepository.save(airTicketPlace);
    }

    @Override
    @Transactional
    public void UpdateTicketInputBank(int idPer, int idTicket) {
        AirTicketPlace airTicketPlace=airTicketPlaceRepository.findById(idTicket).get();
        airTicketPlace.setBankData(true);
        airTicketPlaceRepository.save(airTicketPlace);

    }

    @Override
    @Transactional
    public void BuyTicketAndConfThreeSec(int idTick) {
        AirTicketPlace airTicketPlace=airTicketPlaceRepository.findById(idTick).get();
        airTicketPlace.setPaid(true);
        airTicketPlace.setNumberOfPlace(airTicketPlace.getFlight().getFreePlaces());
        flightService.MinusPlace(airTicketPlace.getFlight().getId());
        airTicketPlaceRepository.save(airTicketPlace);
    }

   public List<AirTicketPlace> listBought(int idPer){
        return airTicketPlaceRepository.AirTicketPlaceBought(idPer);
    }

    public int getLastIdTicketByIdPerson(int idPerson){
        return airTicketPlaceRepository.getLastIdTicketByIdPerson(idPerson);
    }

    public Optional<AirTicketPlace> getById(int id){
        return airTicketPlaceRepository.findById(id);
    }
    public String geInfotById(int id){
       AirTicketPlace airTicketPlace= airTicketPlaceRepository.findById(id).get();
        String from=airTicketPlace.getFlight().getCityFrom().getName();
        String to=airTicketPlace.getFlight().getCityFrom().getName();

        String fromAir=airTicketPlace.getFlight().getCityFrom().getAirportName();
        String toAir=airTicketPlace.getFlight().getCityFrom().getAirportName();
       String s=airTicketPlace.getId()+"\n"
               +"Passenger name: "+airTicketPlace.getPerson().getUsername()+"\n "
               +"Departure city: "+from+"\n"
                +"Arrival city: "+to+"\n"
                +"Seat on the plane: "+airTicketPlace.getNumberOfPlace()+"\n"
                +"Departure date: "+ airTicketPlace.getFlight().getFlightDate()+"\n"
                +"Departure time: "+airTicketPlace.getFlight().getTimeOfDeparture()+"\n"+
                "Arrival time: "+airTicketPlace.getFlight().getTimeOfArrival()+"\n"+
                "Distance: "+airTicketPlace.getFlight().getDistance()+"\n";
       return s;
    }



}
