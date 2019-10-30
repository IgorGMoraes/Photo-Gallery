package com.photogallery.photogallery;

import com.photogallery.photogallery.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class PhotoGalleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoGalleryApplication.class, args);
	}



}