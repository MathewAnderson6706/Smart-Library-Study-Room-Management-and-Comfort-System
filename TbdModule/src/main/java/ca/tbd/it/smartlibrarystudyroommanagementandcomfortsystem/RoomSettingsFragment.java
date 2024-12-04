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
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class RoomSettingsFragment extends Fragment {

    private DatabaseReference databaseReference;
    private String roomId;
    private Handler handler = new Handler();
    private long timerTimeLeft;
    private CountDownTimer countDownTimer;
    private TextView remainingTimeTextView;

    public RoomSettingsFragment() {
        // Required empty public constructor
    }


    HomeFragment homeFragment = new HomeFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_settings, container, false);

        TextView roomNameTextView = view.findViewById(R.id.lightingText);
        TextView actualTemperatureTextView = view.findViewById(R.id.actualTemperatureTextView);
        TextView targetTemperatureTextView = view.findViewById(R.id.targetTemperatureTextView);
        SeekBar temperatureSeekBar = view.findViewById(R.id.temperatureSeekBar);
        Button setTemperatureButton = view.findViewById(R.id.setTemperatureButton);
        SwitchCompat lightSwitch = view.findViewById(R.id.lightSwitch);
        SeekBar lightDimnessSeekBar = view.findViewById(R.id.lightDimnessSeekBar);
        remainingTimeTextView = view.findViewById(R.id.remaining_time_textview);

        // Retrieve the room ID from the arguments
        if (getArguments() != null) {
            roomId = getArguments().getString("roomId");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("roooms");

        // Fetch room data
        fetchRoomData(roomNameTextView, actualTemperatureTextView, targetTemperatureTextView, temperatureSeekBar);

        // Fetch Light data
        fetchLightData(lightSwitch, lightDimnessSeekBar);

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

        // Set target temperature
        setTemperatureButton.setOnClickListener(v -> {
            if (roomId != null) {
                int targetTemp = temperatureSeekBar.getProgress();
                String targetTempStr = String.valueOf(targetTemp);
                databaseReference.child(roomId).child("temperature").child("target").setValue(targetTempStr);
                targetTemperatureTextView.setText("Target Temperature: " + targetTemp + "°C");
                adjustActualTemperature(actualTemperatureTextView, targetTemp);
            }
        });

        // Light Switch Toggle
        lightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (roomId != null) {
                databaseReference.child(roomId).child("light").child("state").setValue(isChecked ? "ON" : "OFF");
            }
        });

        // Light Dimness SeekBar
        lightDimnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (roomId != null) {
                    databaseReference.child(roomId).child("light").child("dimness").setValue(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        return view;
    }

    private void fetchRoomData(TextView roomNameTextView, TextView actualTempText, TextView targetTempText, SeekBar seekBar) {
        if (roomId != null) {
            databaseReference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String roomName = snapshot.child("name").getValue(String.class);
                        String actualTemp = snapshot.child("temperature").child("actual").getValue(String.class);
                        String targetTemp = snapshot.child("temperature").child("target").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);
                        Long timer = snapshot.child("timer").getValue(Long.class);

                        roomNameTextView.setText(roomName != null ? roomName : "Room Name Not Found");
                        actualTempText.setText(actualTemp != null ? "Actual Temperature: " + actualTemp + "°C" : "Actual Temperature Not Found");
                        targetTempText.setText(targetTemp != null ? "Target Temperature: " + targetTemp + "°C" : "Target Temperature Not Found");

                        if (status != null && status.equals("occupied") && timer != null) {
                            // If the room is occupied, start the countdown
                            timerTimeLeft = timer * 1000; // Convert seconds to milliseconds
                            startCountdownTimer();
                        }

                        if (targetTemp != null) {
                            seekBar.setProgress(Integer.parseInt(targetTemp));
                        }
                    } else {
                        roomNameTextView.setText("Room Not Found");
                        actualTempText.setText("N/A");
                        targetTempText.setText("N/A");
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

    private void startCountdownTimer() {
        // Initialize the countdown timer
        countDownTimer = new CountDownTimer(timerTimeLeft, 1000) {  // 1 second interval
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the remaining time
                timerTimeLeft = millisUntilFinished;
                updateTimerUI();
                updateFirebaseTimer();
            }

            @Override
            public void onFinish() {
                // When the timer finishes, mark the room as vacant
                kickUserOut();
            }
        };

        countDownTimer.start();
    }

    private void updateTimerUI() {
        // Convert the remaining time to hours, minutes, and seconds
        long hours = timerTimeLeft / 1000 / 3600;
        long minutes = (timerTimeLeft / 1000 % 3600) / 60;
        long seconds = timerTimeLeft / 1000 % 60;

        remainingTimeTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void updateFirebaseTimer() {
        // Update the room's remaining timer in Firebase
        databaseReference.child(roomId).child("timer").setValue(timerTimeLeft / 1000);  // Store time in seconds
    }

    private void kickUserOut() {
        // Set the room status to "vacant"
        databaseReference.child(roomId).child("status").setValue("vacant");

        databaseReference.child(roomId).child("timer").setValue(7200);  // 2 hours in seconds

        if (getView() != null) {
            Snackbar.make(getView(), "Your time is up, please leave the room", Snackbar.LENGTH_LONG).show();
        }

        // Redirect the user to the HomeFragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tbdFlFragment, new HomeFragment())
                .commit();
    }

    private void adjustActualTemperature(TextView actualTempText, int targetTemp) {
        if (roomId != null) {
            databaseReference.child(roomId).child("temperature").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    String actualTempStr = snapshot.child("actual").getValue(String.class);

                    if (actualTempStr != null) {
                        int actualTemp = Integer.parseInt(actualTempStr);

                        if (actualTemp < targetTemp) {
                            actualTemp++;
                        } else if (actualTemp > targetTemp) {
                            actualTemp--;
                        }

                        String updatedActualTempStr = String.valueOf(actualTemp);

                        // Update the database with the new actual temperature
                        databaseReference.child(roomId).child("temperature").child("actual").setValue(updatedActualTempStr);

                        // Update the UI
                        actualTempText.setText("Actual Temperature: " + actualTemp + "°C");

                        // Continue adjusting until the target is reached
                        if (actualTemp != targetTemp) {
                            handler.postDelayed(() -> adjustActualTemperature(actualTempText, targetTemp), 1000);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        }
    }

    private void fetchLightData(SwitchCompat lightSwitch, SeekBar lightDimnessSeekBar) {
        if (roomId != null) {
            databaseReference.child(roomId).child("light").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Get light state
                        String state = snapshot.child("state").getValue(String.class);
                        lightSwitch.setChecked("ON".equals(state));

                        // Get light dimness
                        Integer dimness = snapshot.child("dimness").getValue(Integer.class);
                        if (dimness != null) {
                            lightDimnessSeekBar.setProgress(dimness);
                        }
                    } else {
                        lightSwitch.setChecked(false);
                        lightDimnessSeekBar.setProgress(0);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

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