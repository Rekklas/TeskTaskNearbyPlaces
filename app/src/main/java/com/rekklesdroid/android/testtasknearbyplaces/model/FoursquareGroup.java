package com.rekklesdroid.android.testtasknearbyplaces.model;


import java.util.ArrayList;
import java.util.List;

public class FoursquareGroup {

    private List<FoursquareItem> items = new ArrayList<FoursquareItem>();

    public List<FoursquareItem> getItems() {
        return items;
    }

    public void setItems(List<FoursquareItem> items) {
        this.items = items;
    }
}
