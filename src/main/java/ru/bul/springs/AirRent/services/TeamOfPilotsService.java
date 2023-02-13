package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.repository.TeamOfPilotsRepository;

import java.util.List;

@Service
public class TeamOfPilotsService {
    private final TeamOfPilotsRepository teamOfPilotsRepository;

    public TeamOfPilotsService(TeamOfPilotsRepository teamOfPilotsRepository) {
        this.teamOfPilotsRepository = teamOfPilotsRepository;
    }

    public List<TeamOfPilots> allTeams(){
        return teamOfPilotsRepository.findAll();
    }
}
