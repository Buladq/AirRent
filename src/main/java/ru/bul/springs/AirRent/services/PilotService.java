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
import java.util.List;
import java.util.Optional;

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

    public Optional<Pilot> getPilotById(int id){
        return pilotRepository.findById(id);
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

    public List<Pilot> allPilotsWithoutTeam(){
        return pilotRepository.allPilotsWithoutTeam();
    }

    @Transactional
    public void UpdPilot(Pilot toBeUpPilot,int id,MultipartFile file) throws IOException {
        Pilot pilot=pilotRepository.findById(id).get();
        pilot.setAge(toBeUpPilot.getAge());
        pilot.setExperience(toBeUpPilot.getExperience());
        Image image;
        if(!file.isEmpty()){
            if(pilot.getAvatar_id()!=null){
            imageRepository.deleteById(pilot.getAvatar_id().getId());
             }
            image = toImageEntity(file);
            imageRepository.save(image);
            pilot.setAvatar_id(image);

        }
        pilotRepository.save(pilot);
    }




        }

