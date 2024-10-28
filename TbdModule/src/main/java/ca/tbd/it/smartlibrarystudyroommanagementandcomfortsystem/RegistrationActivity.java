package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private Button googleSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);  // Make sure this references your new layout

        // Find the views
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        googleSignUpButton = findViewById(R.id.googleSignUpButton);

        // Set click listener for the Google Sign-Up button
        googleSignUpButton.setOnClickListener(v -> {
            // Handle Google Sign-Up here
            // For example, you might want to start a Google Sign-In intent
            startGoogleSignIn();
        });
    }

    private void startGoogleSignIn() {
        // Implement Google Sign-In logic here
    }
}
