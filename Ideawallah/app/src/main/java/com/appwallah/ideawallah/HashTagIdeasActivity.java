package com.appwallah.ideawallah;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.appwallah.ideawallah.fragment.HashTagIdeasFragment;
import com.appwallah.ideawallah.fragment.HashTagListFragment;
import com.appwallah.ideawallah.fragment.IdeaListFragment;

import java.util.List;

public class HashTagIdeasActivity extends AppCompatActivity {

    private static final String TAG = "HashTagIdeasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_ideas);

        final String hashtag;

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

        findViewById(R.id.fab_new_idea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HashTagIdeasActivity.this, NewIdeaActivity.class);
                intent.putExtra(Extras.HASHTAG_EXTRA, hashtag);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "reqcode: " + requestCode + ", result: " + resultCode);
        // Check which request we're responding to
        if (requestCode == Extras.NEW_IDEA_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                HashTagIdeasFragment fr = (HashTagIdeasFragment)getSupportFragmentManager().findFragmentById(R.id.ideas_fragment);
                if (fr.getView() != null) {
                    Log.d(TAG, "fragment isn't null, so will call loadideas");
                    fr.loadIdeasFromStart();
                }
            }
        }
    }
}
