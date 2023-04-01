package ru.bul.springs.AirRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.Pilot;

import java.util.List;

@Repository
public interface PilotRepository extends JpaRepository<Pilot,Integer> {

    @Query(value = "SELECT *\n" +
            "FROM aircurs.pilot\n" +
            "WHERE id NOT IN (\n" +
            "  SELECT main_pilotid\n" +
            "  FROM team_of_pilots\n" +
            "  UNION\n" +
            "  SELECT second_pilotid\n" +
            "  FROM team_of_pilots\n" +
            ")",nativeQuery = true)
    public List<Pilot> allPilotsWithoutTeam();
}
