package ru.bul.springs.AirRent.services;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.AirRent.models.Image;
import ru.bul.springs.AirRent.repository.ImageRepository;


@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image findImage(int id){
      return  imageRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Image image){
        imageRepository.save(image);
    }
    @Transactional
    public void deleteIm(int id){
        imageRepository.deleteById(id);
    }


}
