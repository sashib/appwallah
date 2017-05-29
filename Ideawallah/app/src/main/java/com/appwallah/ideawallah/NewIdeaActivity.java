package com.appwallah.ideawallah;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.Idea;
import com.appwallah.ideawallah.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
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

    private EditText mEditText;
    private Button mSubmitButton;

    public boolean mTextDirty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        setTitle(R.string.new_idea_title);

        mTextDirty = false;

        Intent intent = getIntent();
        String hashtag = intent.getStringExtra(Extras.HASHTAG_EXTRA);

        mEditText = (EditText) findViewById(R.id.idea_text);

        if (hashtag != null) {
            mEditText.setText(" #" + hashtag);
        }

        mEditText.setImeActionLabel("Go", KeyEvent.KEYCODE_ENTER);
        mEditText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (((EditText)view).getText().length() > 0 && !mTextDirty)
                    mTextDirty = true;
                return false;
            }

        });


        mSubmitButton = (Button) findViewById(R.id.submit_btn);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitIdea();
            }
        });
    }

    private void submitIdea() {
        final String body = mEditText.getText().toString();

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mEditText.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Saving a great idea...", Toast.LENGTH_SHORT).show();


        FirebaseAuth.getInstance().getCurrentUser().getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();

                    Idea idea = new Idea();
                    idea.idea = body;
                    idea.global = false;
                    IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
                    Call<Idea> call = apiService.createIdea(token, idea);
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
            }
        });

    }

    private void setEditingEnabled(boolean enabled) {
        mEditText.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.cancel_idea_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if(mTextDirty) {
            showDialog();
        }
    }

}