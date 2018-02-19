package com.rekklesdroid.android.testtasknearbyplaces.model;


import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareTip;
import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareVenue;

import java.util.ArrayList;
import java.util.List;

public class FoursquareItem {

    private FoursquareVenue venue;

    private List<FoursquareTip> tips = new ArrayList<FoursquareTip>();

    public FoursquareVenue getVenue() {
        return venue;
    }

    public void setVenue(FoursquareVenue venue) {
        this.venue = venue;
    }

    public List<FoursquareTip> getTips() {
        return tips;
    }

    public void setTips(List<FoursquareTip> tips) {
        this.tips = tips;
    }
}
