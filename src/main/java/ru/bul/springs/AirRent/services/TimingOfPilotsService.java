package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.models.TimingOfPilots;
import ru.bul.springs.AirRent.repository.TimingOfPilotsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimingOfPilotsService {
    private final TimingOfPilotsRepository timingOfPilotsRepository;

    public TimingOfPilotsService(TimingOfPilotsRepository timingOfPilotsRepository) {
        this.timingOfPilotsRepository = timingOfPilotsRepository;
    }

    public List<TimingOfPilots> all(){
        return timingOfPilotsRepository.findAll();
    }

    @Transactional
    public void CreateNewTiming(TeamOfPilots teamOfPilots, LocalDate localDate){

        TimingOfPilots timingOfPilots=new TimingOfPilots();
        timingOfPilots.setBusyOfDate(localDate);
        timingOfPilots.setTeamOfPilots(teamOfPilots);
        timingOfPilotsRepository.save(timingOfPilots);
    }
}
