package com.appwallah.filmlocations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements FilmLocationFragment.OnListFragmentInteractionListener {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title);

    }

    @Override
    public void onListFragmentInteraction(FilmLocation item) {
        Log.d(TAG, "item clicked: " + item.locations);
        Intent intent = new Intent(this, FilmLocationDetailsActivity.class);
        Gson gson = new Gson();
        intent.putExtra(Extras.FILM_LOCATION_EXTRA, gson.toJson(item));
        startActivity(intent);
    }
}
