package ru.bul.springs.AirRent.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.repository.AirTicketPlaceRepository;
import ru.bul.springs.AirRent.util.TicketBuy;

import java.util.List;

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



}
