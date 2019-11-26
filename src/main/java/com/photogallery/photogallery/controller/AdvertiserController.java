package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.*;
import com.photogallery.photogallery.repository.*;
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
    private PhotoRepository photoRepository;

    @Autowired
    private AdRepository adRepository;

    @GetMapping("/advertiserSignup")
    public String showAdvertiserSignUpForm(){
        return "addAdvertiser";
    }

    @PostMapping("/advertiserSignup")
    public String addPublisher(Advertiser advertiser){
        advertiserRepository.save(advertiser);
        return "redirect:/advertisersList";
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
        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));
        advertiserRepository.delete(advertiser);
        return "redirect:/advertisersList";
    }

    @GetMapping("/advertisersList/{id}")
    public ModelAndView advertiserPage(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView("advertiser");

        Advertiser advertiser = advertiserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid publisher Id:" + id));
        mv.addObject("advertiser", advertiser);

        Iterable<Photo> photos = photoRepository.findAllByAd_IdIsNullOrderByViewsDesc();
        mv.addObject("photos", photos);

        return mv;
    }

    @GetMapping("/advertise/{advertiserId}/{photoId}")
    public String showAdvertiseForm(@PathVariable("advertiserId") String advertiserId, @PathVariable("photoId") String photoId){
        return "addAdvertisement";
    }

    @PostMapping("/advertise/{advertiserId}/{photoId}")
    public String addAdvertise(@PathVariable("advertiserId") String advertiserId, @PathVariable("photoId") String photoId, Ad ad){
        Advertiser advertiser = advertiserRepository.findById(advertiserId).orElseThrow(() -> new IllegalArgumentException("Invalid advertiser Id:" + advertiserId));
        Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new IllegalArgumentException("Invalid photo Id:" + photoId));

        ad.setAdversiterName(advertiser.getName());
        ad.setAdvertiser(advertiser);
        ad.setPhoto(photo);

        advertiser.getAds().add(ad);
        photo.setAd(ad);

        adRepository.save(ad);
        advertiserRepository.save(advertiser);
        photoRepository.save(photo);
        return "redirect:/advertisersList/{advertiserId}";
    }
}