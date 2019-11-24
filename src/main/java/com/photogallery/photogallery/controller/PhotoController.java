package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PhotoRepository;
import com.photogallery.photogallery.repository.TagRepository;
import com.photogallery.photogallery.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoStorageService photoStorageService;

    @Autowired
    TagRepository tagRepository;


    @PostMapping("/publishersList/{idUser}/{idAlbum}/addPhoto")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUser));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));

        Tag tag = tagRepository.findByName(album.getTitle());
        if (tag == null){
            tag = new Tag(album.getTitle());
        }


        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("user", user);
        mv.addObject("album", album);
        for (MultipartFile file : Arrays.asList(files)) {
            photoStorageService.storeFile(file, album, tag);
        }

        return "redirect:/publishersList/{idUser}/{idAlbum}";
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

        photoRepository.save(photo);
        albumRepository.save(photo.getAlbum());
        return mv;
    }

    @GetMapping("/deletePhoto/{idUser}/{idAlbum}/{idPhoto}")
    public String deletePhoto(@PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum, @PathVariable("idPhoto") String idPhoto) {
        Photo photo = photoRepository.findById(idPhoto).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idPhoto));
        photoRepository.delete(photo);
        return "redirect:/publishersList/{idUser}/{idAlbum}";
    }
}