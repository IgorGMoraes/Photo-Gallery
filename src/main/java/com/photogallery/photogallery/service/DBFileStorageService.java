package com.photogallery.photogallery.service;


import com.photogallery.photogallery.model.DBFile;
import com.photogallery.photogallery.exeption.FileStorageException;
import com.photogallery.photogallery.exeption.MyFileNotFoundException;
import com.photogallery.photogallery.repository.DBFileRepository;
//import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "test";


        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            //TRYING TO CONVERT THE FILE TO BASE64

//          StringBuilder sb = new StringBuilder();
//          sb.append("data:image/png;base64,");
//          sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));
//          System.out.println(sb.toString());

            String encodedString = Base64.getEncoder().encodeToString(file.getBytes());
            System.out.println(encodedString);

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

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
