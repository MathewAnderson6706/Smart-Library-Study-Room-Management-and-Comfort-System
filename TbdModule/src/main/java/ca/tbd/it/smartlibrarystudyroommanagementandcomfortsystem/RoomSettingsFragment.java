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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RoomSettingsFragment extends Fragment {

    private DatabaseReference databaseReference;
    private String roomId;

    public RoomSettingsFragment() {
        // Required empty public constructor
    }

    //BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_settings, container, false);

        TextView roomNameTextView = view.findViewById(R.id.lightingText);

        // Retrieve the room ID from the arguments
        if (getArguments() != null) {
            roomId = getArguments().getString("roomId");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        // Fetch room data
        fetchRoomData(roomNameTextView);

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



        return view;
    }

    private void fetchRoomData(TextView roomNameTextView) {
        if (roomId != null) {
            databaseReference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String roomName = snapshot.child("name").getValue(String.class);
                        //String temperature = snapshot.child("temperature").getValue(String.class);

                        roomNameTextView.setText(roomName != null ? roomName : "Room Name Not Found");
                        //temperatureTextView.setText(temperature != null ? temperature + "°C" : "Temperature Not Found");
                    } else {
                        roomNameTextView.setText("Room Not Found");
                        //temperatureTextView.setText("N/A");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    roomNameTextView.setText("Error loading data");
                    //temperatureTextView.setText("Error");
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Re-enable the drawer when leaving the fragment
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}