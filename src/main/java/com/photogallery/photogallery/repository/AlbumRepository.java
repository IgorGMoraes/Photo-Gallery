package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    Iterable<Album> findAllByUser(User user);
    List<Album> findFirst4ByOrderByViewsDesc();

    List<Album> findByTags_Name(String tag);

}
