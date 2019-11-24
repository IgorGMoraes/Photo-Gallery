package com.photogallery.photogallery.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Ad {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String adversiter;

    private float percentageToPublisher;

    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Advertiser advertiser;

    @OneToOne
    private Photo photo;

    public Ad() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getadversiter() {
        return adversiter;
    }

    public void setadversiter(String adversiter) {
        this.adversiter = adversiter;
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

    public String getAdversiter() {
        return adversiter;
    }

    public void setAdversiter(String adversiter) {
        this.adversiter = adversiter;
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
}
