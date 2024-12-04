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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FourthFragment extends Fragment {


    private DatabaseReference databaseReference;

    public FourthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        ImageButton room1d = view.findViewById(R.id.room1d);
        ImageButton room2d = view.findViewById(R.id.room2d);
        ImageButton room3d = view.findViewById(R.id.room3d);
        ImageButton room4d = view.findViewById(R.id.room4d);

        // Set up rooms
        setupRoom(room1d, "room1d");
        setupRoom(room2d, "room2d");
        setupRoom(room3d, "room3d");
        setupRoom(room4d, "room4d");

        // Initialize the FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab_generate_code);

        // Set a click listener on the FAB
        fab.setOnClickListener(v -> {
            // Display a Snack bar message with information about codes
            Snackbar.make(view, "This screen requires codes to access rooms.", Snackbar.LENGTH_LONG)
                    .setAnchorView(fab) // Ensure Snack bar is anchored to the FAB
                    .show();
        });

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
