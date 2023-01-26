package ru.bul.springs.AirRent.controllers;



import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.bul.springs.AirRent.models.Image;
import ru.bul.springs.AirRent.services.ImageService;


import java.io.ByteArrayInputStream;

@RestController
public class ImageContoroller {
    private final ImageService imageService;

    public ImageContoroller(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable("id") int id)
    {
        Image image=imageService.findImage(id);
        return ResponseEntity.ok()
                .header("fileName",image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
