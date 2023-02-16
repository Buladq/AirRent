package ru.bul.springs.AirRent.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.AirTicketPlace;

import java.util.List;

@Repository
public interface AirTicketPlaceRepository extends JpaRepository<AirTicketPlace,Integer> {

    @Query(value = "SELECT id FROM air_ticket_place where person_id= :up ORDER BY id DESC LIMIT 1 ;",nativeQuery = true)
    public int getLastIdTicketByIdPerson(@Param("up") int up);

    @Query(value = "select*from air_ticket_place where person_id=:denty and paid=true;",nativeQuery = true)
    public List<AirTicketPlace> AirTicketPlaceBought(@Param("denty") int denty);



}
