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


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import utils.NetworkUtils;

public class ThirdFragment extends Fragment {

    private DatabaseReference databaseReference;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        ImageButton room1c = view.findViewById(R.id.room1c);
        ImageButton room2c = view.findViewById(R.id.room2c);
        ImageButton room3c = view.findViewById(R.id.room3c);
        ImageButton room4c = view.findViewById(R.id.room4c);

        // Set up rooms
        setupRoom(room1c, "room1c");
        setupRoom(room2c, "room2c");
        setupRoom(room3c, "room3c");
        setupRoom(room4c, "room4c");

        // Initialize the FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab_generate_code);

        // Set a click listener on the FAB
        fab.setOnClickListener(v -> {
            // Display a Snack bar message with information about codes
            Snackbar.make(view, "Green=Vacant, Red=Occupied", Snackbar.LENGTH_LONG)
                    .setAnchorView(fab) // Ensure Snack bar is anchored to the FAB
                    .show();
        });

        // Check if the device is offline and show a message
        if (NetworkUtils.isOffline(requireContext())) {
            Toast.makeText(requireContext(), "You are offline. Some features may not work.", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void setupRoom(ImageButton roomButton, String roomId) {
        RoomUtils.setupRoom(requireContext(), roomButton, roomId, databaseReference, selectedRoomId ->
                AccessCodeUtils.promptForAccessCode(requireContext(), selectedRoomId, databaseReference, () -> navigateToRoomSettings(selectedRoomId))
        );
    }

    private void navigateToRoomSettings(String roomId) {
        RoomSettingsFragment roomSettingsFragment = new RoomSettingsFragment();

        // Pass the room ID to the fragment
        Bundle args = new Bundle();
        args.putString("roomId", roomId);
        roomSettingsFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tbdFlFragment, roomSettingsFragment)
                .addToBackStack(null)
                .commit();
    }
}