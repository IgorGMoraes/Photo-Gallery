package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PhotoRepository;
import com.photogallery.photogallery.repository.TagRepository;
import com.photogallery.photogallery.repository.PublisherRepository;
import com.photogallery.photogallery.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {
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

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");

        List<Photo> photoTrends = new ArrayList<>();
        List<Album> albums = albumRepository.findFirst4ByOrderByViewsDesc();
        albums.forEach(album -> photoTrends.add(photoRepository.findTopFromLastWeek(album.getId())));
        photoTrends.removeAll(Collections.singleton(null));

        mv.addObject("photoTrends", photoTrends);

        Iterable<Photo> photos = photoRepository.findAllByOrderByDateDesc();
        mv.addObject("photos", photos);

        return mv;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("tag") String tag){
        ModelAndView mv = new ModelAndView("results");

        Iterable<Photo> photos = photoRepository.findByTags_Name(tag);
        Iterable<Album> albums = albumRepository.findByTags_Name(tag);

        mv.addObject("albums", albums);
        mv.addObject("photos", photos);
        mv.addObject("tag", tag);
        return mv;
    }
}