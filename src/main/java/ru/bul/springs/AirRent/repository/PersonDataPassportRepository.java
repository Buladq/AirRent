package ru.bul.springs.AirRent.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.models.PersonDataPassport;

import java.util.Optional;

@Repository
public interface PersonDataPassportRepository extends JpaRepository<PersonDataPassport, Integer> {

}
