package com.rekklesdroid.android.testtasknearbyplaces.model;

import java.util.ArrayList;
import java.util.List;

public class FoursquareResponse {

    private List<FoursquareGroup> groups = new ArrayList<FoursquareGroup>();

    public List<FoursquareGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FoursquareGroup> groups) {
        this.groups = groups;
    }
}
