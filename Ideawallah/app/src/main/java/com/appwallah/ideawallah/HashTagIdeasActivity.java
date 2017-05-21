package com.appwallah.ideawallah;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appwallah.ideawallah.fragment.HashTagIdeasFragment;
import com.appwallah.ideawallah.fragment.HashTagListFragment;

public class HashTagIdeasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_ideas);

        setTitle("#" + getIntent().getStringExtra(Extras.HASHTAG_EXTRA));

        if (findViewById(R.id.ideas_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }

            HashTagIdeasFragment fr = new HashTagIdeasFragment();
            fr.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ideas_fragment, fr).commit();
        }
    }
}
