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

public class SettingsFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private SwitchCompat locationSwitch;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

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

        return view;

    }

    private void checkLocationPermission() {
        String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        if (ContextCompat.checkSelfPermission(requireContext(), locationPermission) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Location permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{locationPermission}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



    }

