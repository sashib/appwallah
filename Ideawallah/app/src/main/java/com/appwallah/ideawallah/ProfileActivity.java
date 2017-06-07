package com.appwallah.ideawallah;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appwallah.ideawallah.adapters.HashTagAdapter;
import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.HashTag;
import com.appwallah.ideawallah.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = ProfileActivity.class.getName();

    public static final String GRAVATAR_URL = "https://www.gravatar.com/avatar/";

    public ImageView mProfileImage;
    public EditText mNameEdit;
    public TextView mEmailText;
    public Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfileImage = (ImageView) findViewById(R.id.imageView);
        mNameEdit = (EditText) findViewById(R.id.editText);
        mEmailText = (TextView) findViewById(R.id.emailText);
        mSubmitButton = (Button) findViewById(R.id.button2);

        loadUser();

    }

    public void loadUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();

                    IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
                    Call<User> call = apiService.getUser(token);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            int statusCode = response.code();
                            Log.d(TAG, "status is - " + statusCode);
                            if (statusCode == 200) {

                                User user = response.body();
                                mNameEdit.setText(user.getName());
                                mEmailText.setText(user.getEmail());

                                loadProfileImage(user.getEmail());


                            } else {

                                Log.e(TAG, "500 when getting user: " + response.body());
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, "getting ideas list failed: ");

                        }
                    });

                }
            }
        });
    }

    public void loadProfileImage(String email) {
        String hash = MD5Util.md5Hex(email);
        String url = GRAVATAR_URL + hash;
        Picasso.with(this)
                .load(url)
                .resize(80, 80)
                .centerCrop()
                .into(mProfileImage);
    }
}
