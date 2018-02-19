package com.rekklesdroid.android.testtasknearbyplaces;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareItem;
import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareJSON;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Activity that contains retrieved from Foursquare API data and
 * displays it in the recyclerView
 */
public class ListActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    @BindView(R.id.btn_logout)
    Button logoutButton;

    @BindView(R.id.recview_places_list)
    RecyclerView venueList;
    private LinearLayoutManager venueListManager;
    private RecyclerView.Adapter venueListAdapter;

    private String foursquareClientID;
    private String foursquareClientSecret;
    private String userLL;
    private double userLLAcc;

    List<FoursquareItem> foursquareItemsList = new ArrayList<FoursquareItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        // if not login yet
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        }

        venueList.setHasFixedSize(true);
        venueListManager = new LinearLayoutManager(this);
        venueList.setLayoutManager(venueListManager);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        foursquareClientID = getResources().getString(R.string.foursquare_client_id);
        foursquareClientSecret = getResources().getString(R.string.foursquare_client_secret);

    }

    /**
     * Method that invokes LoginActivity if user is not logged in
     */
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Method that invokes {@link #goLoginScreen()} method
     * after clicking on logout button
     */
    @OnClick(R.id.btn_logout)
    public void logout() {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if (lastLocation != null) {

                userLL = lastLocation.getLatitude() + "," + lastLocation.getLongitude();
                userLLAcc = lastLocation.getAccuracy();
            }

            ExploreAsyncTask exploreAsyncTask = new ExploreAsyncTask(this);
            exploreAsyncTask.execute();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Can't connect to Google's servers!", Toast.LENGTH_LONG).show();
        finish();
    }


    /**
     * AsyncTask for exploring nearby places. Also implemented WeakReference pattern
     * for solving leak of memory AsyncTask problem
     */
    private static class ExploreAsyncTask extends AsyncTask<Void, Void, List<FoursquareItem>> {

        private final WeakReference<ListActivity> listActivityWeakReference;

        private ExploreAsyncTask(ListActivity listActivity) {
            listActivityWeakReference = new WeakReference<>(listActivity);
        }

        @Override
        protected List<FoursquareItem> doInBackground(Void... voids) {

            FoursquareService fourSquareService = FoursquareService.retrofit.create(FoursquareService.class);
            final Call<FoursquareJSON> call = fourSquareService.searchNearbyVenues(
                    listActivityWeakReference.get().foursquareClientID,
                    listActivityWeakReference.get().foursquareClientSecret,
                    listActivityWeakReference.get().userLL,
                    listActivityWeakReference.get().userLLAcc
            );

            try {
                FoursquareJSON foursquareJSON = call.execute().body();
                listActivityWeakReference.get().foursquareItemsList = foursquareJSON.getResponse().getGroups().get(0).getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return listActivityWeakReference.get().foursquareItemsList;
        }

        @Override
        protected void onPostExecute(List<FoursquareItem> foursquareItems) {
            super.onPostExecute(foursquareItems);
            listActivityWeakReference.get().venueListAdapter = new ListAdapter(listActivityWeakReference.get().getApplicationContext(), foursquareItems);
            listActivityWeakReference.get().venueList.setAdapter(listActivityWeakReference.get().venueListAdapter);
        }
    }

}
