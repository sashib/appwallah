package com.appwallah.filmlocations;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmLocationFragment extends Fragment {

    public static String TAG = FilmLocationFragment.class.getName();

    private OnListFragmentInteractionListener mListener;

    private FilmLocationRecyclerViewAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private List<FilmLocation> mList;

    public FilmLocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filmlocation_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecycler = (RecyclerView) view;
            mRecycler.setHasFixedSize(true);
            mManager = new LinearLayoutManager(context);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(getContext(),
                    mManager.getOrientation());
            mRecycler.addItemDecoration(mDividerItemDecoration);
            mRecycler.setLayoutManager(mManager);
        }

        mList = new ArrayList<>();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
            loadFilmLocations();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void loadFilmLocations() {

        ApiServiceInterface apiService = ApiService.getApiService();
        Call<List<FilmLocation>> filmLocations = apiService.getFilmLocations(ApiService.API_URL);

        Call<List<FilmLocation>> call = filmLocations;
        call.enqueue(new Callback<List<FilmLocation>>() {
            @Override
            public void onResponse(Call<List<FilmLocation>> call, Response<List<FilmLocation>> response) {
                int statusCode = response.code();
                Log.d(TAG, "status is - " + statusCode);

                if (statusCode == 200) {
                    //List<FilmLocation> filmLocations = getAllFilmLocations(response.body());
                    List<FilmLocation> filmLocations = response.body();
                    int itemsCount = filmLocations.size();
                    if (itemsCount > 0) {
                        mList.addAll(filmLocations);
                        mAdapter = new FilmLocationRecyclerViewAdapter(mList, mListener);
                        mRecycler.setAdapter(mAdapter);
                    }

                } else {
                    Log.e(TAG, "500 when getting ideas: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<FilmLocation>> call, Throwable t) {
                Log.e(TAG, "getting ideas list failed: ");
            }
        });
    }

    private List<FilmLocation> getAllFilmLocations(List<FilmLocation> filmLocations) {
        ArrayList<FilmLocation> allFilmLocations = new ArrayList<>();
        HashMap<String, FilmLocation> locationsMap = new HashMap<>();

        for (FilmLocation fl:filmLocations) {
            FilmLocation fLocation = locationsMap.get(fl.getTitle());
            ArrayList<String> locations;
            if (fLocation == null) {
                fLocation = fl;
                locations = new ArrayList<>();
            } else {
                locations = fLocation.getAllLocations();
                locations.add(fl.getLocations());
            }
            fLocation.setAllLocations(locations);
            locationsMap.put(fl.getTitle(), fLocation);
        }

        Iterator it = locationsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d(TAG, pair.getKey() + " = " + pair.getValue());
            allFilmLocations.add((FilmLocation)pair.getValue());
        }

        return allFilmLocations;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(FilmLocation item);
    }


}
