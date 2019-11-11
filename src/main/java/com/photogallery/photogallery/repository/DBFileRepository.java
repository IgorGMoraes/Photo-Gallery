package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Album;
import com.photogallery.photogallery.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends CrudRepository<DBFile, String> {
    Iterable<DBFile> findAllByAlbum(Album album);
}
