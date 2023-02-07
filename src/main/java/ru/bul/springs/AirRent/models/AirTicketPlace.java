package ru.bul.springs.AirRent.models;

import javax.persistence.*;

@Entity
@Table(name = "AirTicketPlace")
public class AirTicketPlace {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "flight_id",referencedColumnName = "id")
    private Flight flight;

    @Column(name = "numberOfPlace")
    private int numberOfPlace;

    @Column(name = "confData")
    private boolean confData;

    @Column(name = "bankData")
    private boolean bankData;

    @Column(name = "paid")
    private boolean paid;

    public AirTicketPlace(Person person, Flight flight, int numberOfPlace, boolean confData, boolean bankData, boolean paid) {
        this.person = person;
        this.flight = flight;
        this.numberOfPlace = numberOfPlace;
        this.confData = confData;
        this.bankData = bankData;
        this.paid = paid;
    }

    public AirTicketPlace() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getNumberOfPlace() {
        return numberOfPlace;
    }

    public void setNumberOfPlace(int numberOfPlace) {
        this.numberOfPlace = numberOfPlace;
    }

    public boolean isConfData() {
        return confData;
    }

    public void setConfData(boolean confData) {
        this.confData = confData;
    }

    public boolean isBankData() {
        return bankData;
    }

    public void setBankData(boolean bankData) {
        this.bankData = bankData;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
