package com.example.jokestarapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogInOutFragment extends Fragment {

    GoogleSignInClient mGoogleSignInClient;
    private TextView displayName;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        MainActivity main = (MainActivity) getActivity();
        mGoogleSignInClient = GoogleSignIn.getClient(main, gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(main);
        if (account != null) {
            // Auto-Login to firebase with previous account
            Log.d("AUTH", "Auto-Login to firebase with previous account");
            firebaseAuthWithGoogle(account.getIdToken());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_log_in_out, container, false);
        Button btnGoogleLogin = v.findViewById(R.id.buttonGoogleLogin);
        Button btnGoogleLogout = v.findViewById(R.id.buttonGoogleLogout);
        displayName = v.findViewById(R.id.displayName);

        btnGoogleLogin.setOnClickListener(v1 -> googleSignIn());
        btnGoogleLogout.setOnClickListener(v12 -> googleSignOut());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUILoginFragment(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //init task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("AUTH", "firebaseAuthWithGoogle:" + (account != null ? account.getId() : null));
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("AUTH", "Google sign in failed", e);
            }

        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        MainActivity mainActivity = (MainActivity) getActivity();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mainActivity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AUTH", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(getActivity(), "Google Login successful",
                                Toast.LENGTH_LONG).show();
                        updateUILoginFragment(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("AUTH", "signInWithCredential:failure", task.getException());
                        Toast.makeText(getActivity(), "Google Login failed",
                                Toast.LENGTH_LONG).show();
                        updateUILoginFragment(null);
                    }

                });
    }

    private void updateUILoginFragment(FirebaseUser user) {
        if (user != null) {
            Log.d("AUTH", "Update GUI, displayName: " + user.getDisplayName());
            displayName.setText("Hallo " + user.getDisplayName());
            MainActivity.displayUserName = user.getDisplayName();
            MainActivity.displayUserEmail = user.getEmail();
            updateSideNav();
        } else {
            Log.d("AUTH", "Update GUI, Kein user gefunden");
            displayName.setText("Nicht eingeloggt");
        }

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    private void googleSignOut() {
        // signout fireabase
        FirebaseAuth.getInstance().signOut();

        //mGoogleSignInClient - to prevent auto re-login
        MainActivity main = (MainActivity) getActivity();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(main, task -> updateUILoginFragment(null));

        Toast.makeText(getActivity(), "Google Logout successful",
                Toast.LENGTH_LONG).show();
        updateUILoginFragment(null);

        //Reset global var and side nav
        MainActivity.displayUserName = "Gast";
        MainActivity.displayUserEmail = "Gast";

        updateSideNav();

    }

    private void updateSideNav() {
        // Reset Name and email in side nav immediately
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(((MainActivity) getActivity()));
        View headerView = navigationView.getHeaderView(0);
        TextView displayNameSideNav = headerView.findViewById(R.id.displayName);
        TextView displayEmailSideNav = headerView.findViewById(R.id.displayEmail);
        displayNameSideNav.setText(MainActivity.displayUserName);
        displayEmailSideNav.setText(MainActivity.displayUserEmail);
    }
}