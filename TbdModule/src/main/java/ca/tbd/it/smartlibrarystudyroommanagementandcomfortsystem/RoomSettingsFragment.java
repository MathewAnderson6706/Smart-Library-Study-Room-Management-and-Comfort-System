/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RoomSettingsFragment extends Fragment {

    public RoomSettingsFragment() {
        // Required empty public constructor
    }

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_settings, container, false);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar2);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Disable the drawer
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // Enable the back button
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_white);
        }

        // Handle the back button click
        toolbar.setNavigationOnClickListener(v -> {
                // Otherwise, navigate to tbdFlFragment explicitly
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tbdFlFragment, homeFragment)
                        .commit();
            ((MainActivity) getActivity()).syncDrawerToggle();
        });

        BottomNavigationView bottomNav = view.findViewById(R.id.roomSettingsBottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;

            if (id == R.id.action_temperature) {
                selectedFragment = new TemperatureFragment();
            } else if (id == R.id.action_lighting) {
                selectedFragment = new LightingFragment();
            } else if (id == R.id.action_time) {
                selectedFragment = new TimeFragment();
            } else if (id == R.id.action_idk) {
                selectedFragment = new IdkFragment();
            }

            if (selectedFragment != null) {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.roomSettingsFragmentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });


        //bottomNavigationView.setSelectedItemId(R.id.action_temperature);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Re-enable the drawer when leaving the fragment
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}