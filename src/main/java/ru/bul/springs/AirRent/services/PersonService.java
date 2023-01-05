package ru.bul.springs.AirRent.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.repository.PeopleRepository;
import ru.bul.springs.AirRent.util.MailSender;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class PersonService {
    private final PeopleRepository peopleRepository;



    private final MailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    public PersonService(PeopleRepository peopleRepository, MailSender mailSender, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> allPersons(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void savePerson(Person person){
        peopleRepository.save(person);
    }

    public Optional<Person> findPersonById(int id){
        return peopleRepository.findById(id);
    }

    public Optional<Person> getUserByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }



    public Integer getPersonByMail(String mail){ //проверка на имеющегося пользователя
       List<Person> allp=peopleRepository.findAll();
       for (var wx:
             allp) {
            if(wx.getEmail().equals(mail)){
                return 1;
            }
        }
        return null;
    }

    @Transactional
    public void changePass(String emailTo){
        String encodedPassn=String.valueOf(generatePswd());
        System.out.println(encodedPassn);
        Person person=getPersonByMailNow(emailTo);
        person.setPassword(passwordEncoder.encode(encodedPassn));

        mailSender.SendMail(emailTo,"Новый пароль", person.getUsername()+",ваш новый пароль " +encodedPassn);

    }

    private char[] generatePswd() { //для генерации нового пароля
        String passSymbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        char[] password = new char[12];

        for (int i = 0; i < 12; i++) {
            password[i] = passSymbols.charAt(random.nextInt(passSymbols.length()));


        }
        return password;
    }

    public Person getPersonByMailNow(String mail){
        List<Person> allp=peopleRepository.findAll();
        for (var wx:
                allp) {
            if(Objects.equals(wx.getEmail(), mail)){
                return wx;
            }
        }
        return null;
    }


}
