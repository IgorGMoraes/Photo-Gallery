package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends CrudRepository<Ad, String> {
}
