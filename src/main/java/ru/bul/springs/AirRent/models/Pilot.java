package ru.bul.springs.AirRent.models;


import javax.persistence.*;

@Entity
@Table(name = "pilot")
public class Pilot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "experience")
    private int experience;

    @Column(name = "age")
    private int age;

    @OneToOne
    @JoinColumn(name = "person_id" ,referencedColumnName = "id")
    private Person person;

    @OneToOne()
    @JoinColumn(name = "avatar_id",referencedColumnName = "id")
    private Image avatar_id;


    @OneToOne(mappedBy = "mainPilot")
    private TeamOfPilots teamOfPilots;

    @OneToOne(mappedBy = "secondPilot")
    private TeamOfPilots teamOfPilots1;

    public Pilot() {
    }

    public Pilot(int experience, int age, Person person, Image avatar_id) {
        this.experience = experience;
        this.age = age;
        this.person = person;
        this.avatar_id = avatar_id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Image getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(Image avatar_id) {
        this.avatar_id = avatar_id;
    }

    public TeamOfPilots getTeamOfPilots() {
        return teamOfPilots;
    }

    public void setTeamOfPilots(TeamOfPilots teamOfPilots) {
        this.teamOfPilots = teamOfPilots;
    }

    public TeamOfPilots getTeamOfPilots1() {
        return teamOfPilots1;
    }

    public void setTeamOfPilots1(TeamOfPilots teamOfPilots1) {
        this.teamOfPilots1 = teamOfPilots1;
    }
}
