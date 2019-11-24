package com.photogallery.photogallery.service;


import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import com.photogallery.photogallery.exception.FileStorageException;
import com.photogallery.photogallery.exception.MyFileNotFoundException;
import com.photogallery.photogallery.model.Tag;
import com.photogallery.photogallery.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class PhotoStorageService {

    @Autowired
    private PhotoRepository photoRepository;

    public Photo storeFile(MultipartFile file, Album album, Tag tag) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //Convert the file bytes to base64 string
            String encodedString = Base64.getEncoder().encodeToString(file.getBytes());

            Photo photo = new Photo(fileName, file.getContentType(), encodedString, album);
            photo.getTags().add(tag);

            return photoRepository.save(photo);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Photo getFile(String fileId) {
        return photoRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
