package ru.bul.springs.AirRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.bul.springs.AirRent.models.AirTicketRent;

@Repository
public interface AirTicketRentRepository extends JpaRepository<AirTicketRent,Integer> {

    @Query(value = "SELECT id FROM air_ticket_rent where person_id= :up ORDER BY id DESC LIMIT 1 ;",nativeQuery = true)
    public int getLastIdTicketRentByIdPerson(@Param("up") int up);
}
