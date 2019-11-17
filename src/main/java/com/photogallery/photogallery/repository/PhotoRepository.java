package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, String> {
    Iterable<Photo> findAllByAlbum(Album album);
}
