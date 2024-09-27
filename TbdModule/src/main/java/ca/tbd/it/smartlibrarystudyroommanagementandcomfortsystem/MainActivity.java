/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Safah Virk N01596470
Section RCA
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView
                = findViewById(R.id.tbdBottomNavigationView);

        bottomNavigationView
                .setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.tbdHome);

        // Use OnBackPressedDispatcher to handle the back press event
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Call your method to display the AlertDialog
                displayAlertDialog();
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

        if (itemId == R.id.tbdHome) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, homeFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.tbdThird) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, thirdFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.tbdFourth) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, fourthFragment)
                    .commit();
            return true;
        }
        else if (itemId == R.id.tbdSecond) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tbdFlFragment, secondFragment)
                    .commit();
            return true;
        }

        return false;
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


}