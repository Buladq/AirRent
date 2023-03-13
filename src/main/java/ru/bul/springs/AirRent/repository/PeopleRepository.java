package ru.bul.springs.AirRent.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByUsername(String username);

    Optional<Person> findByemail(String email);

    @Query(value = "SELECT * FROM aircurs.person", nativeQuery = true)
    Page<Person> personsPage(Pageable pageable);
}
