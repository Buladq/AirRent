package ru.bul.springs.AirRent.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.services.PersonService;


@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personService.getUserByUsername(person.getUsername()).isPresent())
            errors.rejectValue("username", "", "Человек с таким именем уже существует"+'\n'
                    +"придумайте новое");
    }
}
