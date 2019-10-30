package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.Model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    Album findById(long id);
}
