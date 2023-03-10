package ru.bul.springs.AirRent.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "PersonDataPassport")
public class PersonDataPassport implements Serializable {
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

    @NotEmpty(message = "Серия паспорта не может быть пустой ")
    @Column(name = "seriesOfPassport")
    private String seriesOfPassport;

    @NotEmpty(message = "Номер паспорта не может быть пустым")
    @Column(name = "numberOfPassport")
    private String numberOfPassport;

    @NotNull(message ="Дата не может быть пустой" )
    @Column(name = "dateOfBirth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "person_id" ,referencedColumnName = "id",unique = true)
    private Person person;

    public PersonDataPassport() {
    }

    public PersonDataPassport(String name, String surname, String patronymic, String seriesOfPassport, String numberOfPassport, LocalDate dateOfBirth, Person person) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.seriesOfPassport = seriesOfPassport;
        this.numberOfPassport = numberOfPassport;
        this.dateOfBirth = dateOfBirth;
        this.person = person;
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



    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSeriesOfPassport() {
        return seriesOfPassport;
    }

    public void setSeriesOfPassport(String seriesOfPassport) {
        this.seriesOfPassport = seriesOfPassport;
    }

    public String getNumberOfPassport() {
        return numberOfPassport;
    }

    public void setNumberOfPassport(String numberOfPassport) {
        this.numberOfPassport = numberOfPassport;
    }
}
