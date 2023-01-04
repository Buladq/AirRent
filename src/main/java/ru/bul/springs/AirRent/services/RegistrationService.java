package ru.bul.springs.AirRent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.repository.PeopleRepository;


@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void registred(Person person){
         String encodedPass=passwordEncoder.encode(person.getPassword());
         person.setPassword(encodedPass);
         person.setRole("ROLE_USER");
         person.setActivite(true);

        peopleRepository.save(person);
    }





}
