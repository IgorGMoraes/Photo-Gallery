package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Ad;
import com.photogallery.photogallery.model.Advertiser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiserRepository extends CrudRepository<Advertiser, String> {
}
