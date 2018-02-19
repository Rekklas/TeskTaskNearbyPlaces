package com.rekklesdroid.android.testtasknearbyplaces.model;


import java.util.ArrayList;
import java.util.List;

public class FoursquareFeaturedPhotos {


    private Integer count;
    private List<FoursquarePhotoItem> items = new ArrayList<FoursquarePhotoItem>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<FoursquarePhotoItem> getItems() {
        return items;
    }

    public void setItems(List<FoursquarePhotoItem> items) {
        this.items = items;
    }
}
