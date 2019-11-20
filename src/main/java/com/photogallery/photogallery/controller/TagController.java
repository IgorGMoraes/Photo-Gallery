package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PhotoRepository;
import com.photogallery.photogallery.repository.TagRepository;
import com.photogallery.photogallery.repository.UserRepository;
import com.photogallery.photogallery.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class TagController {
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

    @PostMapping("/addTag/{id}/{idUser}/{idAlbum}")
    public String addTag(@PathVariable("id") String id, @PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum, @RequestParam("tagName") String tagName){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));

        Set<Tag> tags = photo.getTags();
        Tag tag = new Tag(tagName);
        photo.getTags().add(tag);

        photoRepository.save(photo);

        return "redirect:/publishersList/{idUser}/{idAlbum}";
    }

}