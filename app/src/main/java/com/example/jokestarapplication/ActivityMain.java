package com.example.jokestarapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //global available user data
    public static String displayUserName;
    public static String displayUserEmail;
    GoogleSignInClient mGoogleSignInClient;
    private TextView displayName;
    private TextView displayEmail;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private List<JokeCategory> categories;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        categories = DemoData();
        bundle = new Bundle();
        bundle.putSerializable("Categories", (Serializable) categories);

        //load default fragment

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMain fragmentMain = new FragmentMain();
        fragmentMain.setArguments(bundle);
        fragmentTransaction.replace(R.id.container_fragment, fragmentMain);
        fragmentTransaction.commit();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Side navigation elements (needs headerView since sidenav can be hidden (null)
        View headerView = navigationView.getHeaderView(0);
        displayName = headerView.findViewById(R.id.displayName);
        displayEmail = headerView.findViewById(R.id.displayEmail);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            //account.getDisplayName()
            Log.d("AUTH", "User is logged-in automatically");
            Toast.makeText(this, "Google Login automatically (already signed in previously)",
                    Toast.LENGTH_LONG).show();

            updateMainActivityUI(account);
        } else {
            Log.d("AUTH", "User is NOT logged-in automatically");
        }


    }

    public void updateMainActivityUI(GoogleSignInAccount account) {
        // Global vars
        displayUserName = account.getDisplayName();
        displayUserEmail = account.getEmail();
        //Side nav vars
        Log.d("AUTH", "side nav vars: " + displayUserName);
        Log.d("AUTH", "side nav vars: " + displayUserEmail);
        // if (displayUserName != null) {
        displayName.setText(displayUserName);
        // }
        // if (displayUserEmail != null) {
        displayEmail.setText(displayUserEmail);
        //  }


    }


    //move to new Fragment

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);


        bundle = new Bundle();
        bundle.putSerializable("Categories", (Serializable) categories);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.home:
                FragmentMain fragmentMain = new FragmentMain();
                fragmentMain.setArguments(bundle);
                fragmentTransaction.replace(R.id.container_fragment, fragmentMain);
                break;

            case R.id.newJoke:
                FragmentNewJoke fragmentNewJoke = new FragmentNewJoke();
                fragmentNewJoke.setArguments(bundle);
                fragmentTransaction.replace(R.id.container_fragment, fragmentNewJoke);
                break;

            case R.id.logInOut:
                fragmentTransaction.replace(R.id.container_fragment, new FragmentLogInOut());
                break;
            case R.id.aboutUs:
                fragmentTransaction.replace(R.id.container_fragment, new FragmentAboutUs());
                break;

            case R.id.register:
                fragmentTransaction.replace(R.id.container_fragment, new FragmentRegister());
                break;

        }
        fragmentTransaction.commit();

        return true;
    }

    private List<JokeCategory> DemoData() {
        List<JokeCategory> data = new ArrayList<>();
        data.add(new JokeCategory("Short Jokes"));
        data.add(new JokeCategory("Long Jokes"));
        data.add(new JokeCategory("One Liner"));
        data.add(new JokeCategory("Dumb Jokes"));
        data.add(new JokeCategory("Chuck Norris"));

        return data;
    }

    public void updateCategories(List<JokeCategory> list) {
        categories = list;
    }

}