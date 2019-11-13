package com.photogallery.photogallery.controller;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.DBFile;
import com.photogallery.photogallery.model.User;
import com.photogallery.photogallery.payload.UploadFileResponse;
import com.photogallery.photogallery.repository.AlbumRepository;
import com.photogallery.photogallery.repository.DBFileRepository;
import com.photogallery.photogallery.repository.UserRepository;
import com.photogallery.photogallery.service.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired DBFileRepository dbFileRepository;

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
    public ModelAndView userPage(@PathVariable("id") String id, Model model){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ModelAndView mv = new ModelAndView("publisher");
        mv.addObject("user", user);

        Iterable<Album> albums = albumRepository.findAllByUser(user);
        mv.addObject("albums", albums);
        return mv;
    }


    //#######################  ALBUM CONTROLLER  #######################
    @GetMapping("/publishersList/{id}/addAlbum")
    public String showNewAlbumForm(@PathVariable("id") String id){
        return "add-album";
    }

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






    ///////////////////////////// PHOTO CONTROLLER  //////////////////////////////////////////
    @Autowired
    DBFileStorageService dbFileStorageService;

    public UploadFileResponse uploadFile(MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(dbFile.getId())
//                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), file.   getContentType(), file.getSize());
    }

    @PostMapping("/publishersList/{idUser}/{idAlbum}/addPhoto")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable("idUser") String idUser, @PathVariable("idAlbum") String idAlbum) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idUser));
        Album album = albumRepository.findById(idAlbum).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + idAlbum));


        ModelAndView mv = new ModelAndView("photo");
        mv.addObject("user", user);
        mv.addObject("album", album);

        Arrays.asList(files)
                .stream()
                .forEach(file -> uploadFile(file));

        return "redirect:/publishersList/{idUser}/{idAlbum}";
    }


//    @GetMapping("/downloadFile/{fileId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
//        // Load file from database
//        DBFile dbFile = dbFileStorageService.getFile(fileId);
//
//        byte[] data = Base64.getDecoder().decode(dbFile.getData());
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
//                .body(new ByteArrayResource(data));
//    }

}