package ru.bul.springs.AirRent.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TimingOfPilots") //бд для проверки в какой день какой пилот занят
public class TimingOfPilots {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "teamOfPilots",referencedColumnName = "id")
    private TeamOfPilots teamOfPilots;


    @Column(name = "busyOfDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate busyOfDate;

    public TimingOfPilots(TeamOfPilots teamOfPilots, LocalDate busyOfDate) {
        this.teamOfPilots = teamOfPilots;
        this.busyOfDate = busyOfDate;
    }

    public TimingOfPilots() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TeamOfPilots getTeamOfPilots() {
        return teamOfPilots;
    }

    public void setTeamOfPilots(TeamOfPilots teamOfPilots) {
        this.teamOfPilots = teamOfPilots;
    }

    public LocalDate getBusyOfDate() {
        return busyOfDate;
    }

    public void setBusyOfDate(LocalDate busyOfDate) {
        this.busyOfDate = busyOfDate;
    }
}
