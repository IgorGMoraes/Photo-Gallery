package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, String> {
}
