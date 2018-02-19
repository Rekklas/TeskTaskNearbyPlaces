package com.rekklesdroid.android.testtasknearbyplaces;

import com.rekklesdroid.android.testtasknearbyplaces.model.FoursquareJSON;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FoursquareService {

    @GET("venues/explore?v=20180218&radius=2000&venuePhotos=1")
    Call<FoursquareJSON> searchNearbyVenues(@Query("client_id") String clientID,
                                            @Query("client_secret") String clientSecret,
                                            @Query("ll") String ll,
                                            @Query("llAcc") double llAcc);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
