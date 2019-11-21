package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PhotoRepository;
import com.photogallery.photogallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AlbumController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @PostMapping("/publishersList/{id}/addAlbum")
    public String addAlbum(@PathVariable("id") String id, Album album){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        album.setUser(user);
        Tag tag = new Tag(album.getTitle());
        album.getTags().add(tag);
        albumRepository.save(album);
        return "redirect:/publishersList/{id}";
    }

    @GetMapping("/publishersList/{idUser}/{idAlbum}")
    public ModelAndView showPublisherAlbumPage(@PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum, Model model){
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUser));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));

        ModelAndView mv = new ModelAndView("album");
        mv.addObject("user", user);
        mv.addObject("album", album);

        Iterable<Photo> photos = photoRepository.findAllByAlbum(album);
        mv.addObject("photos", photos);
        return mv;
    }


    @GetMapping("/a/{idAlbum}")
    public ModelAndView showAlbumPage(@PathVariable("idAlbum") String idAlbum, Model model){
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));

        ModelAndView mv = new ModelAndView("album");
        mv.addObject("album", album);

        Iterable<Photo> photos = photoRepository.findAllByAlbum(album);
        mv.addObject("photos", photos);

        return mv;
    }

}
