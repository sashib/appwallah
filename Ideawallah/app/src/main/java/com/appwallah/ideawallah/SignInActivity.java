package com.appwallah.ideawallah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appwallah.ideawallah.api.IdeawallahApiService;
import com.appwallah.ideawallah.api.IdeawallahApiServiceInterface;
import com.appwallah.ideawallah.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    private String currentUserToken;

    private LinearLayout mFormLayout;
    private LinearLayout mButtonsLayout;
    private LinearLayout mForgotLayout;
    private LinearLayout mForgotLinkLayout;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mForgotEmailField;
    private Button mSignInButton;
    private Button mSignUpButton;
    private Button mForgotButton;
    private TextView mCancelForgot;
    private TextView mForgotText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        mFormLayout = (LinearLayout) findViewById(R.id.layout_email_password);
        mButtonsLayout = (LinearLayout) findViewById(R.id.layout_buttons);
        mForgotLayout = (LinearLayout) findViewById(R.id.forgotLayout);
        mForgotLinkLayout = (LinearLayout) findViewById(R.id.forgotLinkLayout);

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mForgotEmailField = (EditText) findViewById(R.id.field_forgot_email);
        mSignInButton = (Button) findViewById(R.id.button_sign_in);
        mSignUpButton = (Button) findViewById(R.id.button_sign_up);

        mForgotText = (TextView) findViewById(R.id.forgotText);
        mForgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });

        mForgotButton = (Button) findViewById(R.id.button_forgot);
        mForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doForgotPassword();
            }
        });

        mCancelForgot = (TextView) findViewById(R.id.cancelForgotText);
        mCancelForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelForgot();
            }
        });

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        } else {
            mFormLayout.setVisibility(View.VISIBLE);
            mButtonsLayout.setVisibility(View.VISIBLE);
            mForgotLinkLayout.setVisibility(View.VISIBLE);
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(final FirebaseUser user) {

        Log.d(TAG, "about to get token");

        user.getToken(false).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                if (task.isSuccessful()) {
                    currentUserToken = task.getResult().getToken();
                    //Utils.saveToken(getBaseContext(), currentUserToken);

                    Log.d(TAG, "TOKEN IS: " + currentUserToken);
                    String username = usernameFromEmail(user.getEmail());
                    registerUser(user.getUid(), username, user.getEmail());

                    // Go to MainActivity
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    private void registerUser(String userId, String name, String email) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.userId = userId;


        IdeawallahApiServiceInterface apiService = IdeawallahApiService.getApiService();
        Call<User> call = apiService.createUser(currentUserToken, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                Log.d(TAG, "status is - " + statusCode);
                if (statusCode == 200) {
                    User user = response.body();
                    Log.d(TAG, "created user is: " + user.email);
                } else {

                    Log.e(TAG, "500 when creating suer: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.d(TAG, "created user failed: ");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn();
        } else if (i == R.id.button_sign_up) {
            signUp();
        }
    }

    public void forgotPassword() {
        mButtonsLayout.setVisibility(View.GONE);
        mFormLayout.setVisibility(View.GONE);
        mForgotLinkLayout.setVisibility(View.GONE);

        mForgotLayout.setVisibility(View.VISIBLE);

    }

    public void doForgotPassword() {
        String emailAddress = mForgotEmailField.getText().toString();

        if (TextUtils.isEmpty(emailAddress)) {
            mForgotEmailField.setError("Required");
            return;
        } else {
            mEmailField.setError(null);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(SignInActivity.this, getString(R.string.forgot_sent),
                                    Toast.LENGTH_SHORT).show();
                            cancelForgot();
                        } else {

                        }
                    }
                });
    }

    public void cancelForgot() {
        mButtonsLayout.setVisibility(View.VISIBLE);
        mFormLayout.setVisibility(View.VISIBLE);
        mForgotLinkLayout.setVisibility(View.VISIBLE);

        mForgotLayout.setVisibility(View.GONE);

    }
}