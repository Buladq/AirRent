package ru.bul.springs.AirRent.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.AirRent.models.Image;
import ru.bul.springs.AirRent.models.Person;
import ru.bul.springs.AirRent.models.Pilot;
import ru.bul.springs.AirRent.repository.ImageRepository;
import ru.bul.springs.AirRent.repository.PeopleRepository;
import ru.bul.springs.AirRent.repository.PilotRepository;


import java.io.IOException;

@Service
public class PilotService {

    private final PilotRepository pilotRepository;

    private final PeopleRepository peopleRepository;

    private final ImageRepository imageRepository;

    public PilotService(PilotRepository pilotRepository, PeopleRepository peopleRepository, ImageRepository imageRepository) {
        this.pilotRepository = pilotRepository;
        this.peopleRepository = peopleRepository;
        this.imageRepository = imageRepository;
    }
    @Transactional
    public void newPilot(MultipartFile file, int id,int age,int exp) throws IOException {
        Person person=peopleRepository.findById(id).get();
        Pilot pilot=new Pilot();
        Image image;
        if (file.getSize() != 0) {
            image = toImageEntity(file);
            imageRepository.save(image);
            pilot.setAvatar_id(image);
        }
        pilot.setExperience(exp);
        pilot.setAge(age);
        pilot.setPerson(person);
        pilotRepository.save(pilot);

    }
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }




        }

