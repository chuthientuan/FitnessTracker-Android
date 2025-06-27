package com.example.fitnesstracker.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesstracker.R;
import com.example.fitnesstracker.utils.FirebaseUtil;
import com.example.fitnesstracker.viewmodel.LoginViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN_WITH_GOOGLE = 1001;
    LoginButton btnFacebookHidden;
    private LoginViewModel loginViewModel;
    private CallbackManager callbackManager;
    MaterialButton btnGoogle, btnFacebook, btnSignup;
    MaterialAutoCompleteTextView edtName, edtPassword;
    MaterialTextView txtAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnGoogle = findViewById(R.id.btnGoogle);
        btnFacebookHidden = findViewById(R.id.btnFacebookHidden);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnSignup = findViewById(R.id.btnSignup);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        txtAlreadyAccount = findViewById(R.id.txtAlreadyAccount);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGoogle.setOnClickListener(v -> {
            googleSignInClient.signOut().addOnCompleteListener(task -> {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN_WITH_GOOGLE);
            });
        });
        // Facebook Sign In
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this.getApplication());
        callbackManager = CallbackManager.Factory.create();
        btnFacebookHidden.setReadPermissions("email", "public_profile");
        btnFacebookHidden.registerCallback(callbackManager, new FacebookCallback<>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginViewModel.handleFacebookAccessToken(loginResult.getAccessToken());
                AccessToken accessToken = loginResult.getAccessToken();
                Log.d("FB_ACCESS_TOKEN", "Token: " + accessToken.getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
        btnFacebook.setOnClickListener(v -> btnFacebookHidden.performClick());
        loginViewModel.getUser().observe(this, user -> {
            if (user != null) {
                FirebaseUtil.checkProfileExists(exists -> {
                    if (exists) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, OnboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        // Signup with Email
        btnSignup.setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String email = edtName.getText().toString();
            String password = edtPassword.getText().toString();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        txtAlreadyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmailLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Google Sign In
        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginViewModel.signInWithGoogle(account.getIdToken());
                Intent intent = new Intent(this, OnboardActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
        // Facebook Sign In
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}