package com.appwallah.ideas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static final int REQUEST_NEW_IDEA = 1;

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


}
