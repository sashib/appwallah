package com.appwallah.ideas;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appspot.ideas_staging.ideasapi.Ideasapi;
import com.appspot.ideas_staging.ideasapi.model.Idea;

import java.io.IOException;
import java.util.List;

public class NewIdeaActivity extends AppCompatActivity {

    private static final String TAG = NewIdeaActivity.class.getName();

    NewIdeaController mNewIdeaController;

    Button mSaveButton;
    EditText mIdeaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        initializeController();
        initializeView();

    }


    public void initializeController() {
        this.mNewIdeaController = new NewIdeaController(this);
    }

    public void initializeView() {
        mIdeaEdit = (EditText) findViewById(R.id.idea_edit);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIdea();
            }
        });
    }

    public void saveIdea() {
        String ideaContent = mIdeaEdit.getText().toString();
        List<String> hashTags = NewIdeaController.getHashTags(ideaContent);

        Idea newIdea = new Idea();
        newIdea.setDescription(ideaContent);
        newIdea.setHashtags(hashTags);

        new EndpointsAsyncTask().execute(new Pair<Context, Idea>(this, newIdea));

    }


    class EndpointsAsyncTask extends AsyncTask<Pair<Context, Idea>, Void, Idea> {
        private Ideasapi apiService = null;
        private Context context;

        @Override
        protected Idea doInBackground(Pair<Context, Idea>... params) {
            context = params[0].first;
            Idea idea = params[0].second;

            apiService = IdeasApiService.getApiService(context);
            try {
                return apiService.idea().insert(idea).execute();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Idea idea) {
            if (idea != null)
                onBackPressed();
        }
    }
}
