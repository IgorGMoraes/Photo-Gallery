package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Publisher;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AlbumRepository albumRepository;

    //Show new publisher form
    @GetMapping("/publisherSignup")
    public String showSignUpForm(){
        return "addPublisher";
    }

    //Save new publisher
    @PostMapping("/publisherSignup")
    public String addPublisher(Publisher publisher){
        publisherRepository.save(publisher);
        return "redirect:/publishersList";
    }

    //List publishers
    @RequestMapping("/publishersList")
    public ModelAndView publishersList(){
        ModelAndView mv = new ModelAndView("publishersList");
        Iterable<Publisher> publishers = publisherRepository.findAll();
        mv.addObject("publishers", publishers);
        return mv;
    }

    //Delete publishers
    @RequestMapping("/delete")
    public String deletePublisher(String id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));
        publisherRepository.delete(publisher);
        return "redirect:/publishersList";
    }

    //Show publisher page
    @GetMapping("/publishersList/{id}")
    public ModelAndView publisherPage(@PathVariable("id") String id, Model model) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));
        ModelAndView mv = new ModelAndView("publisher");
        mv.addObject("publisher", publisher);

        Iterable<Album> albums = albumRepository.findAllByPublisher(publisher);
        mv.addObject("albums", albums);
        return mv;
    }

}