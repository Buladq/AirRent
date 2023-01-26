package ru.bul.springs.AirRent.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 2, max = 60, message = "Имя пользователя должно состоять от 2 до 60 символов")
    @Column(name = "username")
    private String username;


    @Column(name = "password")
    @Size(min = 8,max = 60,message = "Пароль должен состоять от 8 до 60 символов")
    private String password;


    @Column(name = "role")
    private String role;

    @Column(name = "email")
    @NotEmpty(message = "Поле почты не должно быть пустым")
    @Email(message = "Неверный формат почты")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;



    @Column(name = "activite")
    private boolean activite;

    @OneToOne(mappedBy = "person")
    private PersonDataPassport personDataPassport;


    @OneToOne(mappedBy = "person")
    private Pilot pilot;

    public Person() {
    }

    public Person(String username, String role, String email, String phoneNumber) {
        this.username = username;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActivite() {
        return activite;
    }

    public void setActivite(boolean activite) {
        this.activite = activite;
    }

    public PersonDataPassport getPersonDataPassport() {
        return personDataPassport;
    }

    public void setPersonDataPassport(PersonDataPassport personDataPassport) {
        this.personDataPassport = personDataPassport;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }
}