/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        // Initialize room buttons
        ImageButton room1a = view.findViewById(R.id.room1a);
        ImageButton room2a = view.findViewById(R.id.room2a);
        ImageButton room3a = view.findViewById(R.id.room3a);
        ImageButton room4a = view.findViewById(R.id.room4a);

        // Set up rooms
        setupRoom(room1a, "room1a");
        setupRoom(room2a, "room2a");
        setupRoom(room3a, "room3a");
        setupRoom(room4a, "room4a");

        // Initialize the FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab_generate_code);

        // Set a click listener on the FAB
        fab.setOnClickListener(v -> {
            // Display a Snack bar message with information about codes
            Snackbar.make(view, "This screen requires codes to access rooms.", Snackbar.LENGTH_LONG)
                    .setAnchorView(fab) // Ensure Snack bar is anchored to the FAB
                    .show();
        });

        // Notify user about offline mode if no network
        if (!isNetworkAvailable(requireContext())) {
            Snackbar.make(view, "You are offline. Cached data is being used.", Snackbar.LENGTH_LONG).show();
        }

        return view;
    }

    private void setupRoom(ImageButton roomButton, String roomId) {
        if (isNetworkAvailable(requireContext())) {
            // Fetch data from Firebase
            databaseReference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String roomDetails = snapshot.getValue(String.class);
                        cacheRoomDetails(roomId, roomDetails); // Cache room data locally
                        setupRoomUI(roomButton, roomDetails, roomId); // Update UI
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), "Failed to load room data. Using cached data.", Toast.LENGTH_SHORT).show();
                    useCachedRoomData(roomButton, roomId); // Use cached data if Firebase fails
                }
            });
        } else {
            // Use cached data when offline
            useCachedRoomData(roomButton, roomId);
        }
    }

    private void useCachedRoomData(ImageButton roomButton, String roomId) {
        String cachedDetails = getCachedRoomDetails(roomId);
        if (cachedDetails != null) {
            setupRoomUI(roomButton, cachedDetails, roomId); // Update UI with cached data
        } else {
            Toast.makeText(requireContext(), "No cached data available for " + roomId, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRoomUI(ImageButton roomButton, String roomDetails, String roomId) {
        // Update room button UI
        roomButton.setContentDescription(roomDetails);
        roomButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Accessing " + roomDetails, Toast.LENGTH_SHORT).show();
            navigateToRoomSettings(roomId);
        });
    }

    private void cacheRoomDetails(String roomId, String details) {
        SharedPreferences prefs = requireContext().getSharedPreferences("room_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString(roomId, details).apply();
    }

    private String getCachedRoomDetails(String roomId) {
        SharedPreferences prefs = requireContext().getSharedPreferences("room_prefs", Context.MODE_PRIVATE);
        return prefs.getString(roomId, null);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                return capabilities != null &&
                        (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
            }
        }
        return false;
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


