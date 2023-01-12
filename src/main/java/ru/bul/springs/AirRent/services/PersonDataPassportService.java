package ru.bul.springs.AirRent.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.models.PersonDataPassport;
import ru.bul.springs.AirRent.repository.PersonDataPassportRepository;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    public void updatePassport(int id,PersonDataPassport updatedPassport,int idper){
        PersonDataPassport passToBeUpdated=personDataPassportRepository.findById(id).get();
        updatedPassport.setId(passToBeUpdated.getId());
        Person person=personService.findPersonById(idper).get();
        updatedPassport.setPerson(person);
        personDataPassportRepository.save(updatedPassport);

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

    public Optional<PersonDataPassport> findById(int id){
        return personDataPassportRepository.findById(id);
    }
}
