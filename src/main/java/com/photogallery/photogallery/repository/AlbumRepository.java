package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, String> {
    List<Album> findAllByPublisher(Publisher publisher);
    List<Album> findFirst4ByOrderByViewsDesc();

    List<Album> findByTags_Name(String tag);

    List<Album> findAllByOrderByViewsDesc();

    @Query(value = "select title from photogallery.album album where album.id = :#{#albumId}", nativeQuery = true)
    String findTitleById(@Param("albumId") String albumId);
}
