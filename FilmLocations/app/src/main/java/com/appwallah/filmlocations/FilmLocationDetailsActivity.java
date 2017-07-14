package com.appwallah.filmlocations;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmLocationDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = FilmLocationDetailsActivity.class.getName();

    public static final String SF_CITY_STR = " San Francisco CA";

    public FilmLocation mFilmLocation;
    public GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_location_details);

        Bundle bundle = getIntent().getExtras();
        String filmLocationStr = bundle.getString(Extras.FILM_LOCATION_EXTRA);
        mFilmLocation = (new Gson()).fromJson(filmLocationStr, FilmLocation.class);

        setTitle(R.string.title);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText(mFilmLocation.getTitle());
        TextView dateText = (TextView) findViewById(R.id.date);
        dateText.setText(mFilmLocation.getReleaseYear());

        TextView castText = (TextView) findViewById(R.id.cast);
        String cast = mFilmLocation.getActor1() + ", " + mFilmLocation.getActor2() + ", " + mFilmLocation.getActor3();
        castText.setText(mFilmLocation.getCast());

        TextView writerText = (TextView) findViewById(R.id.writer);
        writerText.setText(mFilmLocation.getWriter());

        TextView directorText = (TextView) findViewById(R.id.director);
        directorText.setText(mFilmLocation.getDirector());

        TextView funFactText = (TextView) findViewById(R.id.funfact);
        LinearLayout funFactLayout = (LinearLayout) findViewById(R.id.funfact_layout);
        if (mFilmLocation.getFunFacts() == null) {
            funFactLayout.setVisibility(View.GONE);
        } else {
            funFactLayout.setVisibility(View.VISIBLE);
            funFactText.setText(mFilmLocation.getFunFacts());
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (mFilmLocation.getLocations() != null) {
            String location  = mFilmLocation.getLocations() + SF_CITY_STR;
            getGeoCode(location);

        }

    }

    public void getGeoCode(String location) {
        ApiServiceInterface apiService = ApiService.getApiService();
        Call<ResponseBody> geoCode = apiService.getGeoCode(ApiService.GEO_API_URL,
                location, ApiService.GEO_API_KEY);

        Call<ResponseBody> call = geoCode;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                Log.d(TAG, "status is - " + statusCode);
                if (statusCode == 200) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONObject result = new JSONObject(json.getJSONArray("results").get(0).toString());
                        JSONObject geometry = new JSONObject(result.get("geometry").toString());
                        JSONObject location = new JSONObject(geometry.get("location").toString());

                        showMarker(location.getDouble("lat"), location.getDouble("lng"));


                    } catch (Exception e) {
                        Log.e(TAG, "Exception parsing Json " + e.getMessage());
                    }

                } else {
                    Log.e(TAG, "500 when getting ideas: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "getting ideas list failed: ");
            }
        });
    }

    public void showMarker(Double lat, Double lng) {

        LatLng loc = new LatLng(lat, lng);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));

        mMap.addMarker(new MarkerOptions()
                .title(mFilmLocation.getLocations())
                .position(loc))
                .showInfoWindow();

    }
}
