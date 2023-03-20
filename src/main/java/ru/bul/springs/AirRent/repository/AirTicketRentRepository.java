package ru.bul.springs.AirRent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.AirTicketRent;

import java.util.List;

@Repository
public interface AirTicketRentRepository extends JpaRepository<AirTicketRent,Integer> {

    @Query(value = "SELECT id FROM air_ticket_rent where person_id= :up ORDER BY id DESC LIMIT 1 ;",nativeQuery = true)
    public int getLastIdTicketRentByIdPerson(@Param("up") int up);

    @Query(value = "select * from aircurs.air_ticket_rent where person_id=:per and paid=true order by id DESC",nativeQuery = true)
    public Page<AirTicketRent> AirTicketRentBought(@Param("per") int per, Pageable pageable);

    @Query(value = "SELECT * FROM aircurs.air_ticket_rent where paid=true and team_pilots_id=:team ",nativeQuery = true)
    public List<AirTicketRent> getRentFlByIdTeam(@Param("team") int team);
    @Query(value = "SELECT * FROM aircurs.air_ticket_rent where paid=true and team_pilots_id=:team ",nativeQuery = true)
    public Page<AirTicketRent> getAllRentFlyByIdTeam(@Param("team") int team,Pageable pageable);

    @Query(value = "SELECT * FROM aircurs.air_ticket_rent where paid=true and team_pilots_id is not null",nativeQuery = true)
    public Page<AirTicketRent> getAllRentsPage(Pageable pageable);

    @Query(value = "SELECT * FROM aircurs.air_ticket_rent where paid=true and id=:id ",nativeQuery = true)
    public AirTicketRent getByIdAndPaid(@Param("id") int id);

    @Query(value = "SELECT * FROM aircurs.air_ticket_rent where paid=true and team_pilots_id is null" ,nativeQuery = true)
    public List<AirTicketRent> getAllRentsWithoutPilot();
}
