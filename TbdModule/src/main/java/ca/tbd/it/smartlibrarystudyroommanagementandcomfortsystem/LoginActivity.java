package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button signUpButton; // Declare the sign-up button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Reference the activity's layout

        // Find the views
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton); // Initialize the sign-up button

        // Set click listener for login button
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else {
                // Simple validation (replace with your logic)
                if (username.equals("admin") && password.equals("password")) {
                    // Navigate to the main activity (home screen)
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();  // Optional: Close the login activity
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(v -> {
            // Navigate to the registration activity
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }
}
