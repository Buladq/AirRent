package ru.bul.springs.AirRent.models;


import javax.persistence.*;
import java.util.List;

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

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @OneToMany(mappedBy = "cityFrom")
    private List<Flight> citFrom;

    @OneToMany(mappedBy = "cityTo")
    private List<Flight> citTO;

    @OneToMany(mappedBy = "cityFrom")
    private List<AirTicketRent> cityFromRent;

    @OneToMany(mappedBy = "cityTo")
    private List<AirTicketRent> citToRent;



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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Flight> getCitFrom() {
        return citFrom;
    }

    public void setCitFrom(List<Flight> citFrom) {
        this.citFrom = citFrom;
    }

    public List<Flight> getCitTO() {
        return citTO;
    }

    public void setCitTO(List<Flight> citTO) {
        this.citTO = citTO;
    }

    public List<AirTicketRent> getCityFromRent() {
        return cityFromRent;
    }

    public void setCityFromRent(List<AirTicketRent> cityFromRent) {
        this.cityFromRent = cityFromRent;
    }

    public List<AirTicketRent> getCitToRent() {
        return citToRent;
    }

    public void setCitToRent(List<AirTicketRent> citToRent) {
        this.citToRent = citToRent;
    }
}
