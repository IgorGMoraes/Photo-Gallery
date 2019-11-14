package com.photogallery.photogallery.service;


import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.DBFile;
import com.photogallery.photogallery.exeption.FileStorageException;
import com.photogallery.photogallery.exeption.MyFileNotFoundException;
import com.photogallery.photogallery.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file, Album album) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //Convert the file bytes to base64 string
            String encodedString = Base64.getEncoder().encodeToString(file.getBytes());

            DBFile dbFile = new DBFile(fileName, file.getContentType(), encodedString, album);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
