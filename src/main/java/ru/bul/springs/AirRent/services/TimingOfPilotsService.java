package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.TeamOfPilots;
import ru.bul.springs.AirRent.models.TimingOfPilots;
import ru.bul.springs.AirRent.repository.TimingOfPilotsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public List<Date> BusyDateByPilot(int idTeam){
        return timingOfPilotsRepository.BusyDatesByIdTeam(idTeam);
    }
    public boolean busOrNo(int idTeam,String date) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse(date, formatter);
        Date datefor = java.sql.Date.valueOf(dt);
        List<Date> dateListByTeam=timingOfPilotsRepository.BusyDatesByIdTeam(idTeam);
        for (var i:
             dateListByTeam) {
            if(datefor.equals(i)){
                return false;
            }
        }
        return true;

    }
}
