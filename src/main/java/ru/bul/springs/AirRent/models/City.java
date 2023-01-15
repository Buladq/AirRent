package ru.bul.springs.AirRent.models;


import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "airportName")
    private String airportName;

    @Column(name = "factor")
    private int factor;

    public City(String name, String airportName, int factor) {
        this.name = name;
        this.airportName = airportName;
        this.factor = factor;
    }

    public City() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
}
