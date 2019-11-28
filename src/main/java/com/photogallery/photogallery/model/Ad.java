package com.photogallery.photogallery.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Ad {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String adversiterName;

    private float percentageToPublisher;

    private float price;

    private int views = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private Advertiser advertiser;

    @OneToOne(fetch = FetchType.LAZY)
    private Photo photo;

    public Ad() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdversiterName() {
        return adversiterName;
    }

    public void setAdversiterName(String adversiterName) {
        this.adversiterName = adversiterName;
    }

    public float getPercentageToPublisher() {
        return percentageToPublisher;
    }

    public void setPercentageToPublisher(float percentageToPublisher) {
        this.percentageToPublisher = percentageToPublisher;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
