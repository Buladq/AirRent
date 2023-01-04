package ru.bul.springs.AirRent.secutiry;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.bul.springs.AirRent.models.Person;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //ROLE_ADMIN,ROLE_USER,ROLE_PILOT
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));


    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //акк не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //не заблокан
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //пароль не просрок
    }

    @Override
    public boolean isEnabled() {
        return this.person.isActivite(); // акк включен
    }

    //метод для получения данных аутентифицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}
