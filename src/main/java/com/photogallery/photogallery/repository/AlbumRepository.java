package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    Iterable<Album> findAllByPublisher(Publisher publisher);
    List<Album> findFirst4ByOrderByViewsDesc();

    List<Album> findByTags_Name(String tag);

}
