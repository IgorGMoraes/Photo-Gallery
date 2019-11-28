package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.model.Publisher;
import com.photogallery.photogallery.repository.*;
import com.photogallery.photogallery.service.PaymentService;
import com.photogallery.photogallery.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.Base64;

@Controller
public class PhotoController {
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoStorageService photoStorageService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    AdRepository adRepository;

    @PostMapping("/publishersList/{idPublisher}/{idAlbum}/addPhoto")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("idPublisher") String idPublisher, @PathVariable("idAlbum") String idAlbum) {
        Publisher publisher = publisherRepository.findById(idPublisher).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + idPublisher));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));

        Tag tag = tagRepository.findByName(album.getTitle());
        if (tag == null){
            tag = new Tag(album.getTitle());
        }


        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("publisher", publisher);
        mv.addObject("album", album);
        for (MultipartFile file : Arrays.asList(files)) {
            photoStorageService.storeFile(file, album, tag);
        }

        return "redirect:/publishersList/{idPublisher}/{idAlbum}";
    }

    @GetMapping("/p/{id}")
    public ModelAndView showPhoto(@PathVariable("id") String id){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("photo", photo);

        int photoViews = photo.getViews();
        photo.setViews(photoViews + 1);

        int albumViews = photo.getAlbum().getViews();
        photo.getAlbum().setViews(albumViews + 1);

        if (photo.getAd() != null) {
            paymentService.payPublisher(photo.getAlbum().getPublisher(), photo.getAd().getPercentageToPublisher(), photo.getAd().getPrice());
            int adViews = photo.getAd().getViews();
            photo.getAd().setViews(adViews+1);
            adRepository.save(photo.getAd());
        }

        photoRepository.save(photo);
        albumRepository.save(photo.getAlbum());
        return mv;
    }

    @GetMapping("/pnv/{id}")
    public ModelAndView showPhotoNoViewCount(@PathVariable("id") String id){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("photo", photo);

        return mv;
    }

    @GetMapping("/deletePhoto/{idPublisher}/{idAlbum}/{idPhoto}")
    public String deletePhoto(@PathVariable("idPublisher") String idPublisher, @PathVariable("idAlbum") String idAlbum, @PathVariable("idPhoto") String idPhoto) {
        Photo photo = photoRepository.findById(idPhoto).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + idPhoto));
        photoRepository.delete(photo);
        return "redirect:/publishersList/{idPublisher}/{idAlbum}";
    }
}