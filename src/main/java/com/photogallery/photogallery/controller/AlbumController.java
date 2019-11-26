package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.model.Publisher;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PhotoRepository;
import com.photogallery.photogallery.repository.TagRepository;
import com.photogallery.photogallery.repository.PublisherRepository;
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
    private PublisherRepository publisherRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    TagRepository tagRepository;

    @GetMapping("/publishersList/{id}/addAlbum")
    public String showNewAlbumForm(@PathVariable("id") String id){
        return "addAlbum";
    }

    @PostMapping("/publishersList/{id}/addAlbum")
    public String addAlbum(@PathVariable("id") String id, Album album){
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));
        album.setPublisher(publisher);

        Tag tag = tagRepository.findByName(album.getTitle());
        if (tag == null){
            tag = new Tag(album.getTitle());
        }

        album.getTags().add(tag);
        albumRepository.save(album);
        return "redirect:/publishersList/{id}";
    }

    @GetMapping("/publishersList/{idPublisher}/{idAlbum}")
    public ModelAndView showPublisherAlbumPage(@PathVariable("idPublisher") String idPublisher, @PathVariable("idAlbum") String idAlbum, Model model){
        Publisher publisher = publisherRepository.findById(idPublisher).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + idPublisher));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));

        ModelAndView mv = new ModelAndView("publisherAlbum");
        mv.addObject("publisher", publisher);
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
