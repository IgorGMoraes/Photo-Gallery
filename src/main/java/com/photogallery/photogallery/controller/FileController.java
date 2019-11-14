package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.DBFile;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.DBFileRepository;
import com.photogallery.photogallery.repository.UserRepository;
import com.photogallery.photogallery.service.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.Base64;

@Controller
public class FileController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    DBFileRepository dbFileRepository;

    @Autowired
    DBFileStorageService dbFileStorageService;

    @PostMapping("/publishersList/{idUser}/{idAlbum}/addPhoto")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUser));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));
        DBFile dbFile;


        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("user", user);
        mv.addObject("album", album);
        Arrays.asList(files)
                .stream()
//                .forEach(file -> uploadFile(file));
                .forEach(file -> dbFileStorageService.storeFile(file, album));

        return "redirect:/publishersList/{idUser}/{idAlbum}";
    }


    @PostMapping("/{fileId}")
    public String getFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        byte[] photo = Base64.getDecoder().decode(dbFile.getData());

//        String tag = "<img src=\"data:image/png;base64," + photo + "\"/>";
        String tag = "<h1>teste</h1>";
        System.out.println(tag);
        System.out.println("TEST");
        return tag;
    }
}