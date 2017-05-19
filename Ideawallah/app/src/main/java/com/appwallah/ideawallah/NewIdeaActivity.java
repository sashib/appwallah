package com.appwallah.ideawallah;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;
import com.appwallah.ideawallah.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewIdeaActivity extends BaseActivity {

    private static final String TAG = NewIdeaActivity.class.getName();
    private static final String REQUIRED = "Required";

    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_idea);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitIdea();
            }
        });
    }

    private void submitIdea() {
        final String body = mBodyField.getText().toString();

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Saving a great idea...", Toast.LENGTH_SHORT).show();

        Idea idea = new Idea();
        idea.idea = body;
        idea.global = false;
        IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
        Call<Idea> call = apiService.createIdea(Utils.getToken(getBaseContext()), idea);
        call.enqueue(new Callback<Idea>() {
            @Override
            public void onResponse(Call<Idea> call, Response<Idea> response) {
                int statusCode = response.code();
                Log.d(TAG, "status is - " + statusCode);
                if (statusCode == 200) {
                    Idea idea = response.body();
                    Log.d(TAG, "created idea is: " + idea.idea);
                    // Finish this Activity, back to the stream
                    setEditingEnabled(true);
                    setResult(Activity.RESULT_OK);
                    finish();

                } else {

                    Log.e(TAG, "500 when creating idea: " + response.body());
                    setEditingEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Idea> call, Throwable t) {
                // Log error here since request failed
                Log.d(TAG, "created user failed: ");
                setEditingEnabled(true);
            }
        });
    }

    private void setEditingEnabled(boolean enabled) {
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }


}