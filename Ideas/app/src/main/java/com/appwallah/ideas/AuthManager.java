package com.appwallah.ideas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * Created by sbommakanty on 3/17/16.
 */
public class AuthManager {

    private static final String TAG = AuthManager.class.getName();

    private Context mContext;
    private Firebase mFirebaseRef;
    private AuthManagerListener mListener;

    public AuthManager(Context ctx, AuthManagerListener listener) {

        mContext = ctx;
        mListener = listener;
        mFirebaseRef = new Firebase(FireBaseConstants.FIREBASE_URL);

    }

    public String retrieveToken() {
        SharedPreferences prefs = mContext.getSharedPreferences(
                Constants.PREFS_NAME, Constants.MODE_PRIVATE);
        String token = prefs.getString(Constants.TOKEN, null);
        return token;
    }

    public void saveToken(String token) {
        SharedPreferences settings = mContext.getSharedPreferences(
                Constants.PREFS_NAME, Constants.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.TOKEN, token);
        editor.commit();
    }

    public void authenticateUser() {
        /*
        String token = retrieveToken();

        if (TextUtils.isEmpty(token)) {
            mListener.onAuthFailure();
        } else {
            authenticateUserWithToken(token);
        }
        */
        AuthData authData = mFirebaseRef.getAuth();
        if (authData != null) {
            // user authenticated
            mListener.onAuthSuccess();
        } else {
            // no user authenticated
            mListener.onAuthFailure(FirebaseError.DISCONNECTED);
        }
    }

    public void authenticateUserWithPassword(String email, String pass) {
        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                mListener.onAuthSuccess();
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                Log.e(TAG, "Error authenticating: " + firebaseError.getMessage());

                mListener.onAuthFailure(firebaseError.getCode());
            }
        };
        mFirebaseRef.authWithPassword(email, pass, authResultHandler);
    }

    public void authenticateUserWithToken(String token) {
        mFirebaseRef.authWithCustomToken(token, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d(TAG, "user authenticated: " + authData.getUid());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.e(TAG, "error while authenticating");
                mListener.onAuthFailure(firebaseError.getCode());

            }
        });
    }

    public void registerUser(String email, String pass) {
        Firebase.ValueResultHandler<Map<String, Object>> firebaseResultsHandler =
                new Firebase.ValueResultHandler<Map<String,Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.d(TAG, "Successfully created user account with uid: " + result.get("uid"));
                mListener.onAuthSuccess();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
                Log.e(TAG, "Error creating user: " + firebaseError.getMessage());
                mListener.onAuthFailure(firebaseError.getCode());
            }
        };

        mFirebaseRef.createUser(email, pass,
                firebaseResultsHandler);

    }
}
