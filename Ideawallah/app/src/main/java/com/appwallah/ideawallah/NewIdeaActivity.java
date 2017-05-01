package com.appwallah.ideawallah;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

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

public class NewIdeaActivity extends BaseActivity {

    private static final String TAG = NewIdeaActivity.class.getName();
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
        Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewIdeaActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewIdea(userId, user.username, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    private void writeNewIdea(String userId, String username, String body) {
        String ideasPath = "/user-ideas/" + userId;
        String key = mDatabase.child(ideasPath).push().getKey();
        Idea idea = new Idea(userId, username, false, body);
        Map<String, Object> ideaValues = idea.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(ideasPath + "/" + key, ideaValues);

        ArrayList<String> tags = Utils.getHashTags(body);
        for (String tag : tags) {
            tag = tag.trim().substring(1);
            Log.d(TAG, "tags are: " + "/user-tags/" + userId + "/" + tag + "/" + key);
            childUpdates.put("/user-tags/" + userId + "/" + tag + "/" + key, true);
        }

        mDatabase.updateChildren(childUpdates);
    }


}