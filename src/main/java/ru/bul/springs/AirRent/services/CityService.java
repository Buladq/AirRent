package ru.bul.springs.AirRent.services;


import org.springframework.stereotype.Service;
import ru.bul.springs.AirRent.models.City;
import ru.bul.springs.AirRent.repository.CityRepository;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> allCities(){
        return cityRepository.findAll();
    }

    public String getNameById(int id){
        List<City> allci=cityRepository.findAll();
        for (var c:
             allci) {
            if(c.getId()==id){
                return c.getName();
            }
        }
        return null;
    }


}
