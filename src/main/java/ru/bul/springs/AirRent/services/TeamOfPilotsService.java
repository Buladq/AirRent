package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import ru.bul.springs.AirRent.models.Flight;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.repository.TeamOfPilotsRepository;

import java.util.*;

@Service
public class TeamOfPilotsService {
    private final TeamOfPilotsRepository teamOfPilotsRepository;

    public TeamOfPilotsService(TeamOfPilotsRepository teamOfPilotsRepository) {
        this.teamOfPilotsRepository = teamOfPilotsRepository;
    }

    public List<TeamOfPilots> allTeams(){
        return teamOfPilotsRepository.findAll();
    }
    public int role(int pilot){
        List<TeamOfPilots> teamOfPilots=teamOfPilotsRepository.findAll();
        for (var i:
             teamOfPilots) {
            if(pilot==i.getMainPilot().getPerson().getId()){
                return 1;
            }

        }
        return 2;
    }
    public int inTeam(int personId){
        List<TeamOfPilots> teamOfPilots=teamOfPilotsRepository.findAll();
        Set<Integer> allIds=new HashSet<>();
        for (var w:
             teamOfPilots) {
            allIds.add(w.getMainPilot().getPerson().getId());
            allIds.add(w.getSecondPilot().getPerson().getId());
        }

        for (var p:
             allIds) {
            if(p==personId){
                return 1;
            }
        }
        return 0;
    }
    public int idTeamPilotByPilot(int idper){
        List<TeamOfPilots> allpil=teamOfPilotsRepository.findAll();
        for (var m:
             allpil) {
            if(m.getMainPilot().getPerson().getId()==idper){
                return m.getId();
            }
            if(m.getSecondPilot().getPerson().getId()==idper){
                return m.getId();
            }
        }
        return 0;
    }

    public TeamOfPilots getTeamById(int id){
      return  teamOfPilotsRepository.findById(id).get();
    }
    public TeamOfPilots getTeamByIdOpt(int id){
        return  teamOfPilotsRepository.findById(id).orElse(null);
    }
    public List<Flight> getAllFlightsByIdTeam(int idteam){
        return getTeamById(idteam).getTeams();
    }
}
