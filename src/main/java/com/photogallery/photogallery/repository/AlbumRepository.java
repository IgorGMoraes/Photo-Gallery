package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.controller.Album;
import com.photogallery.photogallery.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    Iterable<Album> findAllByUser(User user);
}
