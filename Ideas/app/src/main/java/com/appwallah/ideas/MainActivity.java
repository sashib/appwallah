package com.appwallah.ideas;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.appspot.ideas_staging.ideasapi.Ideasapi;
import com.appspot.ideas_staging.ideasapi.model.IdeaProtoDescriptionDateHashtags;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static android.Manifest.permission.READ_CONTACTS;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private IdeasRecyclerAdapter mAdapter;

    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewIdea();
            }
        });

        if (mayGetAccounts()) {
            initialize();
        }

    }

    private void initialize() {
        String accountName = IdeasApiService.getAccountName(this);
        if (accountName != null) {
            loadIdeas();
        } else {
            chooseAccount();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName =
                            data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        IdeasApiService.setAccountName(getBaseContext(), accountName);
                        loadIdeas();
                    }
                }
                break;
            case Constants.REQUEST_NEW_IDEA:
                loadIdeas();
                break;
        }
    }

    private void chooseAccount() {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingAudience(this, ClientCredentials.AUDIENCE);
        startActivityForResult(credential.newChooseAccountIntent(), Constants.REQUEST_ACCOUNT_PICKER);
    }


    private void loadIdeas() {
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "2016-04-20"));
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
        startActivityForResult(intent, Constants.REQUEST_NEW_IDEA);
    }

    public void initializeRecycler(List<IdeaProtoDescriptionDateHashtags> ideas) {
        mRecycler = (RecyclerView) findViewById(R.id.ideas_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new IdeasRecyclerAdapter(ideas);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAdapter.cleanup();
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, List<IdeaProtoDescriptionDateHashtags>> {
        private Ideasapi apiService = null;
        private Context context;

        @Override
        protected List<IdeaProtoDescriptionDateHashtags> doInBackground(Pair<Context, String>... params) {
            context = params[0].first;
            String date = params[0].second;

            apiService = IdeasApiService.getApiService(context);
            try {
                return apiService.idea().list().setDate(date).execute().getItems();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<IdeaProtoDescriptionDateHashtags> ideas) {
            if (ideas != null)
                initializeRecycler(ideas);
        }
    }

    private boolean mayGetAccounts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mRecycler, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, Constants.REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, Constants.REQUEST_READ_CONTACTS);
        }
        return false;
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initialize();
            }
        }
    }
}
