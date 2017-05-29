package com.appwallah.ideawallah.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.appwallah.ideawallah.MainActivity;
import com.appwallah.ideawallah.SignInActivity;
import com.appwallah.ideawallah.Utils;
import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

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