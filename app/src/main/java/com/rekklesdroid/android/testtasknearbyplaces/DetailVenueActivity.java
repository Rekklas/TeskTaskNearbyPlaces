package com.rekklesdroid.android.testtasknearbyplaces;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity that contains more details about venue
 */
public class DetailVenueActivity extends AppCompatActivity {

    @BindView(R.id.imv_venue_photo)
    ImageView imv_venue_photo;
    @BindView(R.id.txt_venue_name)
    TextView txt_venue_name;
    @BindView(R.id.txt_venue_phone)
    TextView txt_venue_phone;
    @BindView(R.id.txt_venue_rating)
    TextView txt_venue_rating;
    @BindView(R.id.txt_venue_checkins)
    TextView txt_venue_checkins;
    @BindView(R.id.txt_venue_is_open)
    TextView txt_venue_is_open;
    @BindView(R.id.txt_venue_tips)
    TextView txt_venue_tips;

    private String venuePhotoUrl;
    private String venueName;
    private String venuePhone;
    private double venueRating;
    private int venueCheckins;
    private String venueOpenStatus;
    private List<String> venueTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_venue);
        ButterKnife.bind(this);

        Bundle venue = getIntent().getExtras();
        venueName = venue.getString(ListAdapter.EXTRA_VENUE_NAME);
        venuePhone = venue.getString(ListAdapter.EXTRA_VENUE_PHONE);
        venueRating = venue.getDouble(ListAdapter.EXTRA_VENUE_RATING);
        venueCheckins = venue.getInt(ListAdapter.EXTRA_VENUE_CHECKINS_COUNT);
        venueOpenStatus = venue.getString(ListAdapter.EXTRA_VENUE_OPEN_STATUS);
        venuePhotoUrl = venue.getString(ListAdapter.EXTRA_VENUE_PHOTO_URL);
        venueTips = venue.getStringArrayList(ListAdapter.EXTRA_VENUE_TIPS);

        txt_venue_name.setText(venueName);
        txt_venue_phone.setText(venuePhone);
        txt_venue_rating.setText("Rating - " + String.valueOf(venueRating));
        txt_venue_checkins.setText("Checkins - " + String.valueOf(venueCheckins));
        txt_venue_is_open.setText("Status - " + venueOpenStatus);

        if (venuePhotoUrl != null) {
            Picasso.with(getApplicationContext())
                    .load(venuePhotoUrl)
                    .error(R.drawable.ic_do_not_have_image_250dp)
                    .into(imv_venue_photo);
        } else {
            imv_venue_photo.setImageDrawable(getResources().getDrawable(R.drawable.ic_do_not_have_image_250dp));
        }

        txt_venue_tips.setText(getTips());
    }

    /**
     * This method retrieves last 3 tips about venues and create StringBuilder object
     *
     * @return StringBuilder object which contains 3 last tips about venue
     */
    @NonNull
    private StringBuilder getTips() {
        StringBuilder tips = new StringBuilder();

        int i = 1;
        if(venueTips.size() != 0) {
            for (String venueTip : venueTips) {
                tips.append(i)
                        .append(" comment: ")
                        .append(venueTip)
                        .append("\n\n");
                i++;
            }
        } else{
            tips.append("No Tips");
        }
        return tips;
    }

    /**
     * This method invokes new Call Activity
     * and send the telephone number of the venue via intent
     *
     * @param view TextView that was clicked
     */
    @OnClick(R.id.txt_venue_phone)
    void makePhoneCall(TextView view) {
        if (!view.getText().equals(getResources().getString(R.string.phone_unavailable))) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + view.getText();
            callIntent.setData(Uri.parse(temp));

            startActivity(callIntent);
        }
    }
}
