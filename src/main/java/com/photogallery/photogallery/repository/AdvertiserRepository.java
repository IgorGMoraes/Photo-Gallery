package com.photogallery.photogallery.repository;

import com.photogallery.photogallery.model.Advertiser;
import com.photogallery.photogallery.model.AdvertiserExpenses;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiserRepository extends CrudRepository<Advertiser, String> {

    @Query(value="SELECT sum(expense) as expense, album_id as albumId from " +
            "(SELECT album_id,(price*ad.views) as expense FROM photogallery.photos p " +
            "LEFT JOIN photogallery.ad ad ON p.id = ad.photo_id WHERE ad.advertiser_id = :#{#advertiserId} AND ad.views > 0) a " +
            "GROUP BY album_id ORDER BY expense DESC;", nativeQuery = true)
    List<Object[]> customQuery(@Param("advertiserId") String advertiserId);

}
