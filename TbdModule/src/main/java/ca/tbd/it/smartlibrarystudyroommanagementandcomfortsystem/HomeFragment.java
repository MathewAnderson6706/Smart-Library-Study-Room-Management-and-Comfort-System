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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton room1 = view.findViewById(R.id.room1);
        room1.setEnabled(false);
        ImageButton room2 = view.findViewById(R.id.room2);
        room2.setEnabled(true);

        // Set click listeners for the rooms
        room1.setOnClickListener(v -> promptForAccessCode());
        room2.setOnClickListener(v -> promptForAccessCode());

        return view;
    }

    private void promptForAccessCode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.enter_access_code);

        // Input
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        // Buttons
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            String code = input.getText().toString();
            // Below is the Dummy Code
            if (code.equals(getString(R.string._1234))) {
                // Navigate to RoomSettingsFragment
                navigateToRoomSettings();
            } else {
                Toast.makeText(getActivity(), "Invalid code", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
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