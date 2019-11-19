package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, String> {
    Iterable<Photo> findAllByAlbum(Album album);
    List<Photo> findAllByOrderByDateDesc();

    @Nullable
    @Query(value="SELECT * FROM photogallery.photos WHERE(album_id = :#{#albumId}) AND (date >= curdate() - INTERVAL DAYOFWEEK(curdate())+6 DAY) ORDER BY views DESC LIMIT 1;", nativeQuery = true)
    Photo findTopFromLastWeek(@Param("albumId") String albumId);

}
