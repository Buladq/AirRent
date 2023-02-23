package ru.bul.springs.AirRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.AirTicketPlace;
import ru.bul.springs.AirRent.models.TimingOfPilots;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public interface TimingOfPilotsRepository extends JpaRepository<TimingOfPilots,Integer> {
    @Query(value = "SELECT busy_of_date FROM timing_of_pilots where team_of_pilots=:idteam",nativeQuery = true)
    public List<Date> BusyDatesByIdTeam(@Param("idteam") int idteam);
}
