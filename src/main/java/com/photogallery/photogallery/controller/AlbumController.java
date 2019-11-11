package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class AlbumController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/publishersList/{id}/addalbum")
    public String showNewAlbumForm(@PathVariable("id") String id){
        return "add-album";
    }

    @PostMapping("/publishersList/{id}/addalbum")
    public String addAlbum(@PathVariable("id") String id, Album album){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        album.setUser(user);
        albumRepository.save(album);
        return "redirect:/publishersList/{id}";
    }


}
