package com.appwallah.ideas;

import com.firebase.client.AuthData;

/**
 * Created by sbommakanty on 3/17/16.
 */
public interface AuthManagerListener {
    void onAuthSuccess();
    void onAuthFailure(int code);
    void onAuthStateChanged(AuthData authData);
}
