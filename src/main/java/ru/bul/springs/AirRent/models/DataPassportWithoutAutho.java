package ru.bul.springs.AirRent.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "DataPassportWithoutAutho")
public class DataPassportWithoutAutho {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2,message = "Направильно имя")
    @Column(name = "name")
    private String name;


    @NotEmpty(message = "Фамилия не может быть пустым")
    @Size(min = 2,message = "Неправильная фамилия")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Отчество не может быть пустым")
    @Size(min = 2,message = "Неправильное отчество")
    @Column(name = "patronymic")
    private String patronymic;


    @NotNull
    @Column(name = "seriesOfPassport")
    private int seriesOfPassport;

    @NotNull
    @Column(name = "numberOfPassport")
    private int numberOfPassport;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    public DataPassportWithoutAutho() {
    }

    public DataPassportWithoutAutho(String name, String surname, String patronymic, int seriesOfPassport, int numberOfPassport, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.seriesOfPassport = seriesOfPassport;
        this.numberOfPassport = numberOfPassport;
        this.dateOfBirth = dateOfBirth;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getSeriesOfPassport() {
        return seriesOfPassport;
    }

    public void setSeriesOfPassport(int seriesOfPassport) {
        this.seriesOfPassport = seriesOfPassport;
    }

    public int getNumberOfPassport() {
        return numberOfPassport;
    }

    public void setNumberOfPassport(int numberOfPassport) {
        this.numberOfPassport = numberOfPassport;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
