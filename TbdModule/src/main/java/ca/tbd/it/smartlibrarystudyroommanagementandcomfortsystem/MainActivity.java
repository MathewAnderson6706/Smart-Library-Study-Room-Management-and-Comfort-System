package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
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


}