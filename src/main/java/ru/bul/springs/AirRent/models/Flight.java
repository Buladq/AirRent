package ru.bul.springs.AirRent.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Flight")
public class Flight {
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


    @ManyToOne
    @JoinColumn(name = "teamOfPilots",referencedColumnName = "id")
    private TeamOfPilots teamOfPilots;


    @OneToMany(mappedBy = "flight")
    private List<AirTicketPlace> airTicketPlaces;

    @Column(name = "flightDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate flightDate;


    @Temporal(TemporalType.TIME)
    private Date timeOfDeparture;

    @Temporal(TemporalType.TIME)
    private Date timeOfArrival;

    @Column(name = "distance")
    private int distance;

    @Column(name = "price")
    private int price;

    @Column(name = "freeplaces")
    private int freePlaces;


    @Column(name = "acceptedByAdmin")
    private boolean acceptedByAdmin;

    public Flight() {
    }

    public Flight(City cityFrom, City cityTo, TeamOfPilots teamOfPilots, LocalDate flightDate, Date timeOfDeparture, Date timeOfArrival, int distance, int price, int freePlaces) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.teamOfPilots = teamOfPilots;
        this.flightDate = flightDate;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        this.distance = distance;
        this.price = price;
        this.freePlaces = freePlaces;
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

    public TeamOfPilots getTeamOfPilots() {
        return teamOfPilots;
    }

    public void setTeamOfPilots(TeamOfPilots teamOfPilots) {
        this.teamOfPilots = teamOfPilots;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }
}
