package com.photogallery.photogallery.model;

public class AdvertiserExpenses {
    private double expense;
    private String albumId;
    private String title;

    public AdvertiserExpenses(double expense, String albumId, String title) {
        this.expense = expense;
        this.albumId = albumId;
        this.title = title;
    }

    public AdvertiserExpenses() {
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
