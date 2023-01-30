package ru.bul.springs.AirRent.models;

import javax.persistence.*;

@Entity
@Table(name = "TeamOfPilots")
public class TeamOfPilots {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "main_pilotID" ,referencedColumnName = "id",unique = true)
    private Pilot mainPilot;
    @OneToOne
    @JoinColumn(name = "second_pilotID" ,referencedColumnName = "id",unique = true)
    private Pilot secondPilot;

    public TeamOfPilots() {
    }

    public TeamOfPilots(Pilot mainPilot, Pilot secondPilot) {
        this.mainPilot = mainPilot;
        this.secondPilot = secondPilot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pilot getMainPilot() {
        return mainPilot;
    }

    public void setMainPilot(Pilot mainPilot) {
        this.mainPilot = mainPilot;
    }

    public Pilot getSecondPilot() {
        return secondPilot;
    }

    public void setSecondPilot(Pilot secondPilot) {
        this.secondPilot = secondPilot;
    }
}
