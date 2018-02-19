package com.rekklesdroid.android.testtasknearbyplaces;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareItem;
import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareTip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    /**
     * This variable keeps the value of image size which should be downloaded from url
     */
    public static final String DEFAULT_IMAGE_SIZE = "250x250";

    /**
     * Set of variables which used to put and get Extras via Intent
     */
    public static final String EXTRA_VENUE_NAME = "name";
    public static final String EXTRA_VENUE_PHONE = "phone";
    public static final String EXTRA_VENUE_OPEN_STATUS = "open_status";
    public static final String EXTRA_VENUE_PHOTO_URL = "photo_url";
    public static final String EXTRA_VENUE_CHECKINS_COUNT = "checkins_count";
    public static final String EXTRA_VENUE_TIPS = "tips";
    public static final String EXTRA_VENUE_RATING = "rating";

    /**
     * The application context for getting resources
     */
    private Context context;

    /**
     * The list of results from the Foursquare API
     */
    private List<FoursquareItem> foursquareItems;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imv_venue_photo)
        ImageView imv_photo;
        @BindView(R.id.txt_venue_name)
        TextView txt_name;
        @BindView(R.id.txt_venue_phone)
        TextView txt_phone;
        @BindView(R.id.txt_venue_is_open)
        TextView txt_is_open;

        int checkinsCount;
        double rating;
        String photoUrl;

        ArrayList<String> tips = new ArrayList<>();


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            ButterKnife.bind(this, v);
        }

        @Override
        public void onClick(View view) {
            Context context = txt_name.getContext();
            Intent detailIntent = new Intent(context, DetailVenueActivity.class);
            detailIntent.putExtra(EXTRA_VENUE_NAME, txt_name.getText());
            detailIntent.putExtra(EXTRA_VENUE_PHONE, txt_phone.getText());
            detailIntent.putExtra(EXTRA_VENUE_OPEN_STATUS, txt_is_open.getText());
            detailIntent.putExtra(EXTRA_VENUE_CHECKINS_COUNT, checkinsCount);
            detailIntent.putExtra(EXTRA_VENUE_RATING, rating);
            detailIntent.putExtra(EXTRA_VENUE_PHOTO_URL, photoUrl);
            detailIntent.putStringArrayListExtra(EXTRA_VENUE_TIPS, tips);
            context.startActivity(detailIntent);
        }
    }

    public ListAdapter(Context context, List<FoursquareItem> foursquareItems) {
        this.context = context;
        this.foursquareItems = foursquareItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venue, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        FoursquareItem item = foursquareItems.get(position);

        holder.txt_name.setText(item.getVenue().getName());
        holder.txt_phone.setText(getVenuePhone(item));
        holder.txt_is_open.setText(getVenueOpenStatus(item));
        putVenueImage(holder, item);

        holder.checkinsCount = getVenueCheckinsCount(item);
        holder.rating = getVenueRating(item);

        int i = 0;
        for (FoursquareTip tip : item.getTips()) {
            holder.tips.add(tip.getText());
            i++;
            if (i >= 3) break;
        }

        holder.txt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.txt_phone.getText().equals(context.getResources().getString(R.string.phone_unavailable))) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    String temp = "tel:" + holder.txt_phone.getText();
                    callIntent.setData(Uri.parse(temp));
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(callIntent);
                }
            }
        });

    }

    /**
     * This method retrieves venue's rating
     *
     * @param item item which comprises particular venue
     * @return venue's rating
     */
    private double getVenueRating(FoursquareItem item) {

        double rating;
        try {
            rating = item.getVenue().getRating();
        } catch (NullPointerException ex) {
            rating = 0.0;
        }
        return rating;
    }

    /**
     * This method retrieves venue's checkins count
     *
     * @param item item which comprises particular venue
     * @return venue's checkins count
     */
    private int getVenueCheckinsCount(FoursquareItem item) {

        int checkinsCount;
        try {
            checkinsCount = item.getVenue().getStats().getCheckinsCount();
        } catch (NullPointerException ex) {
            checkinsCount = 0;
        }
        return checkinsCount;
    }

    /**
     * This method retrieves venue's phone number
     *
     * @param item item which comprises particular venue
     * @return venue's phone number
     */
    private String getVenuePhone(FoursquareItem item) {

        String venuePhone;

        if (item.getVenue().getContact().getPhone() != null) {
            venuePhone = item.getVenue().getContact().getPhone();
        } else {
            venuePhone = context.getResources().getString(R.string.phone_unavailable);
        }

        return venuePhone;
    }

    /**
     * This method retrieves venue's status, is it open or closed
     * or unknown
     *
     * @param item item which comprises particular venue
     * @return venue's open/closed status
     */
    private int getVenueOpenStatus(FoursquareItem item) {

        int venueOpenStatus;

        try {
            if (item.getVenue().getHours().getOpen()) {
                venueOpenStatus = R.string.venue_is_open;
            } else {
                venueOpenStatus = R.string.venue_is_closed;
            }
        } catch (NullPointerException ex) {
            venueOpenStatus = R.string.venue_is_unknown;
        }

        return venueOpenStatus;
    }

    /**
     * This method forms Url of a photo and downloads it by Picasso
     * or set default image if loading failed
     *
     * @param holder holder which is responsible for displaying particular venue
     * @param item item which comprises particular venue
     */
    private void putVenueImage(ViewHolder holder, FoursquareItem item) {
        try {
            holder.photoUrl = item.getVenue().getFeaturedPhotos().getItems().get(0).getPrefix() +
                    DEFAULT_IMAGE_SIZE +
                    item.getVenue().getFeaturedPhotos().getItems().get(0).getSuffix();

            Picasso.with(context)
                    .load(holder.photoUrl)
                    .resize(64, 64)
                    .centerCrop()
                    .error(R.drawable.ic_do_not_have_image_64dp)
                    .into(holder.imv_photo);

        } catch (NullPointerException ex) {
            holder.imv_photo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_do_not_have_image_64dp));
        }
    }

    @Override
    public int getItemCount() {
        return foursquareItems.size();
    }

}
