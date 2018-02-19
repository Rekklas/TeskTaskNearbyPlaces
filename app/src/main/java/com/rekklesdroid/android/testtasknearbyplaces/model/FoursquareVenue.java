package com.rekklesdroid.android.testtasknearbyplaces.model;


public class FoursquareVenue {

    private String id;
    private String name;
    private FoursquareContact contact;
    private FoursquareStats stats;
    private Double rating;
    private FoursquareHours hours;
    private FoursquareFeaturedPhotos featuredPhotos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoursquareContact getContact() {
        return contact;
    }

    public void setContact(FoursquareContact contact) {
        this.contact = contact;
    }

    public FoursquareStats getStats() {
        return stats;
    }

    public void setStats(FoursquareStats stats) {
        this.stats = stats;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public FoursquareHours getHours() {
        return hours;
    }

    public void setHours(FoursquareHours hours) {
        this.hours = hours;
    }

    public FoursquareFeaturedPhotos getFeaturedPhotos() {
        return featuredPhotos;
    }

    public void setFeaturedPhotos(FoursquareFeaturedPhotos featuredPhotos) {
        this.featuredPhotos = featuredPhotos;
    }
}
