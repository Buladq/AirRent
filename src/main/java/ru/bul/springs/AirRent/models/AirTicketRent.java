package ru.bul.springs.AirRent.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "AirTicketRent")
public class AirTicketRent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "cityFrom",referencedColumnName = "id")
    private City cityFrom;

    @ManyToOne
    @JoinColumn(name = "cityTo",referencedColumnName = "id")
    private City cityTo;

    @Column(name = "RentFlyDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate RentFlyDate;


    @Temporal(TemporalType.TIME)
    private Date timeOfDeparture;

    @Temporal(TemporalType.TIME)
    private Date timeOfArrival; //само

    @Column(name = "distance")
    private int distance; //само

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;


    @ManyToOne
    @JoinColumn(name = "team_pilots_id",referencedColumnName = "id")
    private TeamOfPilots teamOfPilots;


    @Column(name = "price")
    private int price; //само

    @Column(name = "confData")
    private boolean confData;

    @Column(name = "bankData")
    private boolean bankData;

    @Column(name = "paid")
    private boolean paid;

    public AirTicketRent() {
    }

    public AirTicketRent(City cityFrom, City cityTo, LocalDate rentFlyDate, Date timeOfDeparture, Date timeOfArrival, int distance, Person person, TeamOfPilots teamOfPilots, int price, boolean confData, boolean bankData, boolean paid) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        RentFlyDate = rentFlyDate;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        this.distance = distance;
        this.person = person;
        this.teamOfPilots = teamOfPilots;
        this.price = price;
        this.confData = confData;
        this.bankData = bankData;
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    public City getCityTo() {
        return cityTo;
    }

    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }

    public LocalDate getRentFlyDate() {
        return RentFlyDate;
    }

    public void setRentFlyDate(LocalDate rentFlyDate) {
        RentFlyDate = rentFlyDate;
    }

    public Date getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(Date timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public Date getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(Date timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TeamOfPilots getTeamOfPilots() {
        return teamOfPilots;
    }

    public void setTeamOfPilots(TeamOfPilots teamOfPilots) {
        this.teamOfPilots = teamOfPilots;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
