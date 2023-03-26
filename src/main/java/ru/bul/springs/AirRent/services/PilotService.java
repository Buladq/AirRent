package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.models.PersonDataPassport;
import ru.bul.springs.AirRent.models.Pilot;
import ru.bul.springs.AirRent.repository.PeopleRepository;
import ru.bul.springs.AirRent.repository.PilotRepository;

import java.util.List;

@Service
public class PilotService {

    private final PilotRepository pilotRepository;

    private final PeopleRepository peopleRepository;

    public PilotService(PilotRepository pilotRepository, PeopleRepository peopleRepository) {
        this.pilotRepository = pilotRepository;
        this.peopleRepository = peopleRepository;
    }




        }

