package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.DBFile;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.DBFileRepository;
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
    DBFileRepository dbFileRepository;

    @PostMapping("/publishersList/{id}/addAlbum")
    public String addAlbum(@PathVariable("id") String id, Album album){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        album.setUser(user);
        albumRepository.save(album);
        return "redirect:/publishersList/{id}";
    }

    @GetMapping("/publishersList/{idUser}/{idAlbum}")
    public ModelAndView showPhotoPage(@PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum, Model model){
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUser));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));

        ModelAndView mv = new ModelAndView("album");
        mv.addObject("user", user);
        mv.addObject("album", album);

        Iterable<DBFile> photos = dbFileRepository.findAllByAlbum(album);
        mv.addObject("photos", photos);
        return mv;
    }

    @GetMapping("/publishersList/{idUser}/{idAlbum}/addPhoto")
    public String showNewPhotoForm(@PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum){
        return "add-photo";
    }
}
