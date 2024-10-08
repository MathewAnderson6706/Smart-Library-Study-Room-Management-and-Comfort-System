package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_settings, container, false);

        // Set up the toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Enable the back button
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Handle the back button click
        toolbar.setNavigationOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

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
}