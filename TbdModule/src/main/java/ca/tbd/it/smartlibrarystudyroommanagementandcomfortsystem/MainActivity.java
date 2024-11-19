package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.content.pm.PackageManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    SettingsFragment settingsFragment = new SettingsFragment();
    FeedbackFragment feedbackFragment = new FeedbackFragment();
    UserProfileFragment userProfileFragment = new UserProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    ThirdFragment thirdFragment = new ThirdFragment();
    FourthFragment fourthFragment = new FourthFragment();
    SecondFragment secondFragment = new SecondFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, new HomeFragment())
                    .commit();
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    displayAlertDialog();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.settings) {
            Toast.makeText(this, R.string.you_have_clicked_on_settings, Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, settingsFragment)
                    .commit();
        } else if (id == R.id.info) {
            Toast.makeText(this, R.string.you_have_clicked_on_info, Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, userProfileFragment)
                    .commit();
        } else if (id == R.id.feedback) {
            Toast.makeText(this, R.string.you_have_clicked_on_feedback, Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, feedbackFragment)
                    .commit();
        } else if (id == R.id.exit) {
            Toast.makeText(this, R.string.you_have_clicked_on_exit, Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, homeFragment)
                    .commit();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, homeFragment)
                    .commit();

        } else if (itemId == R.id.nav_third) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, thirdFragment)
                    .commit();

        } else if (itemId == R.id.nav_fourth) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, fourthFragment)
                    .commit();

        } else if (itemId == R.id.nav_second) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, secondFragment)
                    .commit();

        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, settingsFragment)
                    .commit();
        } else if (itemId == R.id.nav_feedback) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, feedbackFragment)
                    .commit();
        } else if (itemId == R.id.nav_profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, userProfileFragment)
                    .commit();
        } else if (itemId == R.id.nav_logout) {
            logoutUser();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.userprefs), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        boolean isRemembered = prefs.getBoolean(getString(R.string.remember_me3), false);
        if (!isRemembered) {
            editor.remove(getString(R.string.remember_me2));
            editor.remove(getString(R.string.username1));
            editor.remove(getString(R.string.password1));
        }

        editor.apply();

        Toast.makeText(MainActivity.this, R.string.logged_out_successfully, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void displayAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setIcon(R.drawable.me);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void syncDrawerToggle() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.location_permission_granted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
