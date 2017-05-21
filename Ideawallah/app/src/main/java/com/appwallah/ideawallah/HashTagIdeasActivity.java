package com.appwallah.ideawallah;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.appwallah.ideawallah.fragment.HashTagIdeasFragment;
import com.appwallah.ideawallah.fragment.HashTagListFragment;

import java.util.List;

public class HashTagIdeasActivity extends AppCompatActivity {

    private static final String TAG = "HashTagIdeasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_ideas);

        String hashtag = "";

        Intent intent = getIntent();
        String action = intent.getAction();

        if (action != null && action == "android.intent.action.VIEW") {
            Uri data = intent.getData();

            Log.d(TAG, "data is: " + data.getQueryParameter("hashtag"));
            Log.d(TAG, "action is: " + action);

            hashtag = data.getQueryParameter(Extras.HASHTAG_EXTRA);
            intent.putExtra(Extras.HASHTAG_EXTRA, hashtag);

        } else {
            hashtag = intent.getStringExtra(Extras.HASHTAG_EXTRA);
        }

        setTitle("#" + hashtag);

        if (findViewById(R.id.ideas_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }

            HashTagIdeasFragment fr = new HashTagIdeasFragment();
            fr.setArguments(intent.getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ideas_fragment, fr).commit();
        }
    }
}
