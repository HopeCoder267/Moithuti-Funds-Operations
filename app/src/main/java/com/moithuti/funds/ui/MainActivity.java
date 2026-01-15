package com.moithuti.funds.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moithuti.funds.R;

/**
 * Main Activity with bottom navigation
 * Hosts all fragments and handles navigation
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = 
            (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController());
        }

        // Set action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Moithuti Funds Operations");
        }
    }
}
