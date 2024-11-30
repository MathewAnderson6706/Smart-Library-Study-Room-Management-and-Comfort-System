/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText passwordConfirmInput;
    EditText phoneInput;
    Button registerButton;
    TextView loginText;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirmInput = findViewById(R.id.confirmPasswordInput);
        phoneInput = findViewById(R.id.phoneNumberInput);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String sanitizedEmail = email.replace(".", ","); // Replace '.' with ','
                String password = passwordInput.getText().toString();
                String confirmPassword = passwordConfirmInput.getText().toString();
                String phone = phoneInput.getText().toString();

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                if (validateInputs(name, email, password, confirmPassword, phone)) {
                    HelperClass helperClass = new HelperClass(name, email, password, phone);
                    checkEmailExistsAndRegister(sanitizedEmail, helperClass);
                }
            }
        });

        loginText.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        });
    }

    private boolean validateInputs(String name, String email, String password, String confirmPassword, String phone) {
        // Name validation
        if (TextUtils.isEmpty(name)) {
            nameInput.setError("Name is required");
            return false;
        }

        // Email validation
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email address");
            return false;
        }

        // Password validation
        if (password.length() < 6 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*") ||
                !password.matches(".*[@#$%^&+=!].*") || !password.matches(".*[A-Z].*")) {
            passwordInput.setError("Password must be at least 6 characters, contain upper case letters, digits, and special characters");
            return false;
        }

        // Confirm password validation
        if (!password.equals(confirmPassword)) {
            passwordConfirmInput.setError("Passwords do not match");
            return false;
        }

        // Phone number validation
        if (phone.length() != 10 || !phone.matches("\\d{10}")) {
            phoneInput.setError("Enter a valid 10-digit phone number");
            return false;
        }

        return true;
    }

    private void checkEmailExistsAndRegister(String sanitizedEmail, HelperClass helperClass) {
        reference.child(sanitizedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    emailInput.setError("Email already exists");
                    Toast.makeText(RegistrationActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(sanitizedEmail, helperClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegistrationActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(String sanitizedEmail, HelperClass helperClass) {
        reference.child(sanitizedEmail).setValue(helperClass)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegistrationActivity.this, R.string.you_have_registered_an_account, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistrationActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
