package com.appwallah.ideawallah.fragment;

import com.appwallah.ideawallah.Extras;
import com.appwallah.ideawallah.Utils;
import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;

import java.util.List;

import retrofit2.Call;

/**
 * Created by sbommakanty on 5/20/17.
 */

public class HashTagIdeasFragment extends IdeaListFragment {

    public HashTagIdeasFragment() {}


    @Override
    public Call<List<Idea>> getIdeas() {
        IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
        String hashtag = getArguments().getString(Extras.HASHTAG_EXTRA);
        return apiService.getIdeasByHashTag(Utils.getToken(getContext()), hashtag, Integer.toString(mIdeasLimit), Integer.toString(mIdeasPage));

    }

}