package ru.bul.springs.AirRent.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.repository.AirTicketPlaceRepository;
import ru.bul.springs.AirRent.util.AirPlaceIdComparator;
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

   public Page<AirTicketPlace> listBought(int idPer, Pageable pageable){
    return airTicketPlaceRepository.AirTicketPlaceBought(idPer,pageable);


    }

    public int getLastIdTicketByIdPerson(int idPerson){
        return airTicketPlaceRepository.getLastIdTicketByIdPerson(idPerson);
    }

    public List<AirTicketPlace> getAllPaidTicketsByIdFly(int flyid){
        return airTicketPlaceRepository.getAllTicketPlacesByIdFLy(flyid);
    }

    public Optional<AirTicketPlace> getById(int id){
        return airTicketPlaceRepository.findById(id);
    }
    public String geInfotById(int id){
       AirTicketPlace airTicketPlace= airTicketPlaceRepository.findById(id).get();
        String from=airTicketPlace.getFlight().getCityFrom().getName();
        String to=airTicketPlace.getFlight().getCityTo().getName();

        String fromAir=airTicketPlace.getFlight().getCityFrom().getAirportName();
        String toAir=airTicketPlace.getFlight().getCityTo().getAirportName();
       String s="ID ticket:= "+airTicketPlace.getId()+"\n"
               +"ID FLY: "+airTicketPlace.getFlight().getId()+"\n "
               +"Passenger name: "+airTicketPlace.getPerson().getPersonDataPassport().getName()+"\n "
               +"Departure city: "+from+"\n"
               +"airport from: "+fromAir+"\n"
                +"Arrival city: "+to+"\n"
               +"airport to: "+toAir+"\n"
                +"Seat on the plane: "+airTicketPlace.getNumberOfPlace()+"\n"
                +"Departure date: "+ airTicketPlace.getFlight().getFlightDate()+"\n"
                +"Departure time: "+airTicketPlace.getFlight().getTimeOfDeparture()+"\n"+
                "Arrival time: "+airTicketPlace.getFlight().getTimeOfArrival()+"\n"+
                "Distance: "+airTicketPlace.getFlight().getDistance()+"\n";
       return s;
    }



}
