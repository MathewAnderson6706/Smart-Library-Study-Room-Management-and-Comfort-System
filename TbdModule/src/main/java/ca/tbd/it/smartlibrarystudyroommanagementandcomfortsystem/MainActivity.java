/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;


import android.content.DialogInterface;
import android.os.Bundle;
import android.content.pm.PackageManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    SettingsFragment settingsFragment = new SettingsFragment();
    FeedbackFragment feedbackFragment = new FeedbackFragment();
    UserProfileFragment userProfileFragment = new UserProfileFragment();


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(id == R.id.settings){
            Toast.makeText(this, R.string.you_have_clicked_on_settings,Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, settingsFragment)
                    .commit();
        } else if (id == R.id.info) {
            Toast.makeText(this, R.string.you_have_clicked_on_info,Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, userProfileFragment)
                    .commit();
        } else if (id == R.id.feedback) {
            Toast.makeText(this, R.string.you_have_clicked_on_feedback,Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, feedbackFragment)
                    .commit();
        } else if (id == R.id.exit) {
            Toast.makeText(this, R.string.you_have_clicked_on_exit,Toast.LENGTH_SHORT).show();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, homeFragment)
                    .commit();
        }
        return true;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        //Setting up the ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Check if we're restoring from a previous state
        if (savedInstanceState == null) {
            // Set the HomeFragment as the default fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, new HomeFragment())
                    .commit();
        }

        // Use OnBackPressedDispatcher to handle the back press event
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
        // Register the callback with the dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    ThirdFragment thirdFragment = new ThirdFragment();
    HomeFragment homeFragment = new HomeFragment();
    FourthFragment fourthFragment = new FourthFragment();
    SecondFragment secondFragment = new SecondFragment();

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

        }
        else if (itemId == R.id.nav_second) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, secondFragment)
                    .commit();

        } else if (itemId == R.id.nav_settings){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, settingsFragment)
                    .commit();
        } else if (itemId == R.id.nav_feedback){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, feedbackFragment)
                    .commit();
        } else if (itemId == R.id.nav_profile){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, userProfileFragment)
                    .commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    // Handle the permission result for all fragments in this activity
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check if the request code matches the location permission request
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                // Handle what happens when permission is granted
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                // Handle what happens when permission is denied
            }
        }
    }

    public void displayAlertDialog(){

        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Add the buttons.
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancels the dialog.
                dialog.dismiss();
            }
        });

        builder.setIcon(R.drawable.me);

// 2. Chain together various setter methods to set the dialog characteristics.

        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        //android.R.drawable.ic_dialog_alert


        builder.setCancelable(false);

// 3. Get the AlertDialog.
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public void syncDrawerToggle() {
        // Re-sync the toggle when coming back to a fragment where the drawer is needed
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


}