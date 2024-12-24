package com.roasystems.komuty;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.roasystems.komuty.databinding.ActivityMainBinding;
import com.roasystems.komuty.ui.authentication.AuthenticationFragment;
import com.roasystems.komuty.ui.settings.SettingsFragment;
import com.roasystems.komuty.ui.userprofile.UserProfileFragment;
import com.roasystems.komuty.utility.SessionManager;
import com.roasystems.komuty.ui.mainmenu.MainMenuFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        if (!sessionManager.isLoggedIn()) {
            // Redirect to login page
            loadFragment(new AuthenticationFragment());
        } else {
            loadFragment(new MainMenuFragment());
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_Container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            // Navigate to the user profile fragment
            loadFragment(new UserProfileFragment());
            return true;
        } else if (id == R.id.action_settings) {
            // Navigate to the settings fragment
            loadFragment(new SettingsFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}