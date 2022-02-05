package com.symbol.shoppinglistv2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.symbol.shoppinglistv2.Other.FirebaseUtil;
import com.symbol.shoppinglistv2.R;

import static com.symbol.shoppinglistv2.Activities.ActivityMain.TAG;

public class ActivitySignIn extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleSignIn;
    private Button btnASILogout;
    private Button btnASIEmailSignIn;
    private Button btnDeleteAccount;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;

    private EditText testpw;
    private EditText testlogin;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);
        googleSignIn = findViewById(R.id.googleSignIn);
        btnASILogout = findViewById(R.id.btnASILogout);
        btnASIEmailSignIn = findViewById(R.id.btnASIEmailSignIn);
        testpw = findViewById(R.id.editTextTextPassword);
        testlogin = findViewById(R.id.editTextTextPersonName);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);


        googleSettings();
        buttonListeners();

    }

    private void buttonListeners(){
        //Google Sign in
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "");
                signIn();
            }
        });

        //Email signIn
        btnASIEmailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pw = testpw.getText().toString();
                String login = testlogin.getText().toString();

                if(pw.length() >0 && login.length()>0){
                    Log.d(TAG, "onClick: Email Sign in trbls ");
                    signInEmailAndPassword(login,pw);
                }
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FirebaseUtil.removeAccount();
                    removeAccount(mAuth.getCurrentUser());
                } catch (NullPointerException e) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Logout
        btnASILogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        Log.d(TAG, "onClick: trbls USER LOGGED OUT" );
                        Toast.makeText(context, "You have been signed out", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something went wrong" + e.toString(), Toast.LENGTH_LONG).show();
                    }
            }
        });




    }

    private void openListActivity(){
        try {
            Log.d(TAG, "onClick: trbls " + mAuth.getCurrentUser().getEmail());
            Intent intent = new Intent(context, ActivityMain.class);
            startActivity(intent);
        } catch (NullPointerException e) {
            Log.d(TAG, "onClick: trbls  NO USER" + mAuth.toString());
            e.printStackTrace();
        }
    }

    private void googleSettings(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        try {
//            Log.d(TAG, "onStart: trbls " + currentUser.getUid());
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
        //updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle: trbls" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed trbls", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success trbls");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(ActivitySignIn.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            openListActivity();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure trbls", task.getException());
                            //updateUI(null);
                        }
                    }
                });
    }

    private void createEmailAndPassword(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(ActivitySignIn.this, "Account creation successful.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ActivitySignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInEmailAndPassword(String email, String password){
        Log.d(TAG, "signInEmailAndPassword: trbls " + email + password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(ActivityMain.appContext, "Login successful.", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            openListActivity();
                        } else {
                            createEmailAndPassword(email, password);
                        }
                    }
                });
    }

    private void removeAccount(FirebaseUser user){
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Account deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}



