package com.appwallah.ideas;

import android.content.Context;
import android.content.SharedPreferences;

import com.appspot.ideas_staging.ideasapi.Ideasapi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;

/**
 * Created by sbommakanty on 4/21/16.
 */
public class IdeasApiService {

    private static final String DEV_API_URL = "http://10.0.2.2:8080/_ah/api";

    public static String getAccountName(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        return settings.getString(Constants.PREF_ACCOUNT_NAME, null);
    }

    public static void setAccountName(Context ctx, String accountName) {
        SharedPreferences settings = ctx.getSharedPreferences(Constants.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.PREF_ACCOUNT_NAME, accountName);
        editor.commit();
    }

    public static GoogleAccountCredential getCredential(Context ctx) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingAudience(ctx, ClientCredentials.AUDIENCE);
        String accountName = getAccountName(ctx);
        if (getAccountName(ctx) != null) {
            credential.setSelectedAccountName(accountName);
        }

        return credential;

    }

    public static Ideasapi getApiService(Context ctx) {
        Ideasapi.Builder builder = new Ideasapi.Builder(
                AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                getCredential(ctx));

        //DEV MODE
        builder.setRootUrl(DEV_API_URL);
        return builder.build();
    }
}
