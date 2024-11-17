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

public class SecondFragment extends Fragment {


    private DatabaseReference databaseReference;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        ImageButton room1b = view.findViewById(R.id.room1b);
        ImageButton room2b = view.findViewById(R.id.room2b);
        ImageButton room3b = view.findViewById(R.id.room3b);
        ImageButton room4b = view.findViewById(R.id.room4b);

        // Set up rooms
        setupRoom(room1b, "room1b");
        setupRoom(room2b, "room2b");
        setupRoom(room3b, "room3b");
        setupRoom(room4b, "room4b");

        return view;
    }

    private void setupRoom(ImageButton roomButton, String roomId) {
        // Check room status in the database
        databaseReference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.child("status").getValue(String.class);
                    boolean isOccupied = "occupied".equals(status);

                    // Enable room button if occupied
                    roomButton.setEnabled(isOccupied);

                    if (isOccupied) {
                        roomButton.setImageResource(R.drawable.roombooked);
                        roomButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
                        roomButton.setOnClickListener(v -> promptForAccessCode(roomId));
                    }
                } else {
                    Toast.makeText(getActivity(), "Room data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void promptForAccessCode(String roomId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.enter_access_code);

        // Input field for code
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        // Buttons
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            String enteredCode = input.getText().toString();
            checkAccessCode(roomId, enteredCode);
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void checkAccessCode(String roomId, String enteredCode) {
        // Retrieve the access code from Firebase
        databaseReference.child(roomId).child("accessCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String correctCode = snapshot.getValue(String.class);

                    if (correctCode != null && correctCode.equals(enteredCode)) {
                        navigateToRoomSettings();
                    } else {
                        Toast.makeText(getActivity(), R.string.invalid_code, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Access code not set", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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