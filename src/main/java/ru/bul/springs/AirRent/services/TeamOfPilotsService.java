package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.repository.TeamOfPilotsRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
