package ru.bul.springs.AirRent.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.PersonDataPassport;
import ru.bul.springs.AirRent.repository.PersonDataPassportRepository;

@Service
public class PersonDataPassportService {

    private final PersonDataPassportRepository personDataPassportRepository;

    public PersonDataPassportService(PersonDataPassportRepository personDataPassportRepository) {
        this.personDataPassportRepository = personDataPassportRepository;
    }

    @Transactional
    public void savePersonDataPass(PersonDataPassport personDataPassport){
        personDataPassportRepository.save(personDataPassport);
    }
}
