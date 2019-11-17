package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PhotoRepository;
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

    @PostMapping("/publishersList/{idUser}/{idAlbum}/addPhoto")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUser));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));


        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("user", user);
        mv.addObject("album", album);
        Arrays.asList(files)
                .stream()
//                .forEach(file -> uploadFile(file));
                .forEach(file -> photoStorageService.storeFile(file, album));

        return "redirect:/publishersList/{idUser}/{idAlbum}";
    }

    @RequestMapping("/addView")
    public String addView(String id){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        int views = photo.getViews();
        photo.setViews(views + 1);
        photoRepository.save(photo);
        return "redirect:/publishersList";
    }
}