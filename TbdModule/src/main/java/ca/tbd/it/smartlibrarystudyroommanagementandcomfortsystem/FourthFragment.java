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

import androidx.appcompat.app.AlertDialog;
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
