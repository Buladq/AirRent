package ru.bul.springs.AirRent.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.models.PersonDataPassport;
import ru.bul.springs.AirRent.repository.PersonDataPassportRepository;

import java.util.List;

@Service
public class PersonDataPassportService {

    private final PersonDataPassportRepository personDataPassportRepository;

    private final PersonService personService;

    public PersonDataPassportService(PersonDataPassportRepository personDataPassportRepository, PersonService personService) {
        this.personDataPassportRepository = personDataPassportRepository;
        this.personService = personService;
    }

    @Transactional
    public void savePersonDataPass(PersonDataPassport personDataPassport, Person person){
        PersonDataPassport personDataPassport1=personDataPassport;
        personDataPassport1.setPerson(person);
        personDataPassportRepository.save(personDataPassport1);
    }

    public PersonDataPassport getPassportByIdPerson(int id){
        List<Person> allPersons=personService.allPersons();
        for (var p:
             allPersons) {
            if(p.getId()==id){
                return p.getPersonDataPassport();
            }
        }
        return null;
    }
}
