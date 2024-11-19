/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.app.AlertDialog;
import android.os.Bundle;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    private DatabaseReference databaseReference;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        ImageButton room1a = view.findViewById(R.id.room1a);
        ImageButton room2a = view.findViewById(R.id.room2a);
        ImageButton room3a = view.findViewById(R.id.room3a);
        ImageButton room4a = view.findViewById(R.id.room4a);

        // Set up rooms
        setupRoom(room1a, "room1a");
        setupRoom(room2a, "room2a");
        setupRoom(room3a, "room3a");
        setupRoom(room4a, "room4a");

        return view;
    }

    private void setupRoom(ImageButton roomButton, String roomId) {
        RoomUtils.setupRoom(requireContext(), roomButton, roomId, databaseReference, selectedRoomId ->
                AccessCodeUtils.promptForAccessCode(requireContext(), selectedRoomId, databaseReference, this::navigateToRoomSettings)
        );
    }

    private void navigateToRoomSettings() {
        RoomSettingsFragment roomSettingsFragment = new RoomSettingsFragment();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tbdFlFragment, roomSettingsFragment)
                .addToBackStack(null)
                .commit();
    }
}
