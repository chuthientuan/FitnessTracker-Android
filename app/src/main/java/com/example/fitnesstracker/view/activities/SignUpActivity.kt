package com.example.fitnesstracker.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesstracker.R
import com.example.fitnesstracker.utils.FirebaseUtil
import com.example.fitnesstracker.viewmodel.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk.sdkInitialize
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    var btnFacebookHidden: LoginButton? = null
    private var loginViewModel: LoginViewModel? = null
    private var callbackManager: CallbackManager? = null
    var btnGoogle: MaterialButton? = null
    var btnFacebook: MaterialButton? = null
    var btnSignup: MaterialButton? = null
    var edtName: MaterialAutoCompleteTextView? = null
    var edtPassword: MaterialAutoCompleteTextView? = null
    var txtAlreadyAccount: MaterialTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View?, insets: WindowInsetsCompat? ->
            val systemBars = insets!!.getInsets(WindowInsetsCompat.Type.systemBars())
            v!!.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebookHidden = findViewById(R.id.btnFacebookHidden)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnSignup = findViewById(R.id.btnSignup)
        edtName = findViewById(R.id.edtName)
        edtPassword = findViewById(R.id.edtPassword)
        txtAlreadyAccount = findViewById(R.id.txtAlreadyAccount)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        // Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        btnGoogle?.setOnClickListener { v: View? ->
            googleSignInClient.signOut().addOnCompleteListener { task: Task<Void?>? ->
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN_WITH_GOOGLE)
            }
        }
        // Facebook Sign In
        sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this.application)
        callbackManager = create()
        btnFacebookHidden!!.setReadPermissions("email", "public_profile")
        btnFacebookHidden!!.registerCallback(
            callbackManager!!,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    loginViewModel!!.handleFacebookAccessToken(result.accessToken)
                    val accessToken = result.accessToken
                    Log.d("FB_ACCESS_TOKEN", "Token: " + accessToken.token)
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException) {
                }
            })
        btnFacebook?.setOnClickListener { v: View? -> btnFacebookHidden?.performClick() }
        loginViewModel?.getUser?.observe(this, Observer { user: FirebaseUser? ->
            if (user != null) {
                FirebaseUtil.checkProfileExists { exists: Boolean ->
                    if (exists) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, OnboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })

        // Signup with Email
        btnSignup!!.setOnClickListener { v: View? ->
            val firebaseAuth = FirebaseAuth.getInstance()
            val email = edtName?.text.toString()
            val password = edtPassword?.text.toString()
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult?>? ->
                    if (task!!.isSuccessful) {
                        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        txtAlreadyAccount?.setOnClickListener { v: View? ->
            val intent = Intent(this, EmailLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Google Sign In
        if (requestCode == RC_SIGN_IN_WITH_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                loginViewModel?.signInWithGoogle(account.idToken)
                val intent = Intent(this, OnboardActivity::class.java)
                startActivity(intent)
                finish()
            } catch (_: ApiException) {
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
        // Facebook Sign In
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val RC_SIGN_IN_WITH_GOOGLE = 1001
    }
}