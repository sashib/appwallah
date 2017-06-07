package com.appwallah.ideawallah.fragment;

import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;


import java.util.List;

import retrofit2.Call;

/**
 * Created by sbommakanty on 2/13/17.
 */

public class AllIdeasFragment extends IdeaListFragment {

    public AllIdeasFragment() {}


    @Override
    public Call<List<Idea>> getIdeas() {

        IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
        return apiService.getIdeas(mToken, Integer.toString(mIdeasLimit+1), Integer.toString(mIdeasPage));

    }

}