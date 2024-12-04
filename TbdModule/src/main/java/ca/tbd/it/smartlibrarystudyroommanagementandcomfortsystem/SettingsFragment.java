/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SwitchCompat;


import utils.NetworkUtils;

public class SettingsFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private SwitchCompat locationSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Location permission switch
        locationSwitch = view.findViewById(R.id.tbdlocationswitch);
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkLocationPermission();
            } else {
                Toast.makeText(getActivity(), R.string.location_tracking_disabled, Toast.LENGTH_SHORT).show();
            }
        });


        ImageView exit = view.findViewById(R.id.settings_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tbdFlFragment, homeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageView next4 = view.findViewById(R.id.arrow_next4);
        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackFragment feedbackFragment = new FeedbackFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.tbdFlFragment, feedbackFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Check if the device is offline and show a message
        if (NetworkUtils.isOffline(requireContext())) {
            Toast.makeText(requireContext(), "You are offline. Some features may not work.", Toast.LENGTH_LONG).show();
        }

        return view;

    }

    private void checkLocationPermission() {
        String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        if (ContextCompat.checkSelfPermission(requireContext(), locationPermission) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted
            Toast.makeText(getActivity(), "Location permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            // Request permission from the fragment
            requestPermissions(new String[]{locationPermission}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



    }

