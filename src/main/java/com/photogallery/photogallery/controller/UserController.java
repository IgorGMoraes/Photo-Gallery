package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    //Show new user form
    @GetMapping("/signup")
    public String showSignUpForm(){
        return "add-user";
    }

    //Save new user
    @PostMapping("/signup")
    public String addUser(User user){
        userRepository.save(user);
        return "redirect:/publishersList";
    }

    //List users
    @RequestMapping("/publishersList")
    public ModelAndView publishersList(){
        ModelAndView mv = new ModelAndView("publishersList");
        Iterable<User> users = userRepository.findAll();
        mv.addObject("users", users);
        return mv;
    }

    //Delete users
    @RequestMapping("/delete")
    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/publishersList";
    }

    //Show user page
    @GetMapping("/publishersList/{id}")
    public ModelAndView userPage(@PathVariable("id") String id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ModelAndView mv = new ModelAndView("publisher");
        mv.addObject("user", user);

        Iterable<Album> albums = albumRepository.findAllByUser(user);
        mv.addObject("albums", albums);
        return mv;
    }

    @GetMapping("/publishersList/{id}/addAlbum")
    public String showNewAlbumForm(@PathVariable("id") String id){
        return "add-album";
    }

}