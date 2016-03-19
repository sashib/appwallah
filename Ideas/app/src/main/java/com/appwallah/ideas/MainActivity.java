package com.appwallah.ideas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static final int REQUEST_NEW_IDEA = 1;

    private Firebase mFirebaseRef;
    private FirebaseRecyclerAdapter<Idea, IdeaViewHolder> mAdapter;

    private RecyclerView mRecycler;

    private AuthManager mAuthManager;
    private AuthManagerListener mAuthListener = new AuthManagerListener() {
        @Override
        public void onAuthSuccess() {

        }

        @Override
        public void onAuthFailure(int code) {
            showLogin();
        }

        @Override
        public void onAuthStateChanged(AuthData authData) {
            //refresh list
            Log.d(TAG, "auth state changed: " + authData.getUid());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseRef = new Firebase(FireBaseConstants.FIREBASE_URL);

        mAuthManager = new AuthManager(this, mAuthListener);
        mAuthManager.authenticateUser();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                showNewIdea();
            }
        });

        initializeRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showNewIdea() {
        Intent intent = new Intent(this, NewIdeaActivity.class);
        startActivityForResult(intent, REQUEST_NEW_IDEA);
    }

    public void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void initializeRecycler() {
        mRecycler = (RecyclerView) findViewById(R.id.ideas_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        Firebase ideaRef = mFirebaseRef.child("ideas");


        mAdapter = new FirebaseRecyclerAdapter<Idea, IdeaViewHolder>(
                Idea.class, R.layout.idea_item, IdeaViewHolder.class, ideaRef) {
            @Override
            public void populateViewHolder(IdeaViewHolder ideaViewHolder, Idea idea, int position) {
                ideaViewHolder.ideaText.setText(idea.getDesc());
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
