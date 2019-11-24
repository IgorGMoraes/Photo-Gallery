package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Advertiser;
import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AdRepository;
import com.photogallery.photogallery.repository.AdvertiserRepository;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdvertiserController {

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private AdRepository adRepository;

    @GetMapping("/advertiserSignup")
    public String showAdvertiserSignUpForm(){
        return "addAdvertiser";
    }

    @PostMapping("/advertiserSignup")
    public String addUser(Advertiser advertiser){
        advertiserRepository.save(advertiser);
        return "redirect:/advertiserList";
    }

    @RequestMapping("/advertisersList")
    public ModelAndView advertisersList(){
        ModelAndView mv = new ModelAndView("advertisersList");
        Iterable<Advertiser> advertisers = advertiserRepository.findAll();
        mv.addObject("advertisers", advertisers);
        return mv;
    }

    @RequestMapping("/deleteAdvertiser")
    public String deleteAdvertiser(String id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        advertiserRepository.delete(advertiser);
        return "redirect:/advertisersList";
    }

    @GetMapping("/advertiserList/{id}")
    public ModelAndView advertiserPage(@PathVariable("id") String id) {
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ModelAndView mv = new ModelAndView("advertiser");
        mv.addObject("advertiser", advertiser);

        return mv;
    }

}