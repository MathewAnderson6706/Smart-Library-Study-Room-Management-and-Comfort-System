package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(v -> {

            if(!validateUsername() | !validatePassword()){

            } else {
                checkUser();

            }
        });

        signUpText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }

    public Boolean validateUsername(){
        String val = usernameInput.getText().toString();
        if (val.isEmpty()){
            usernameInput.setError(getString(R.string.username_cannot_be_empty));
            return false;
        } else {
            usernameInput.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = passwordInput.getText().toString();
        if (val.isEmpty()){
            passwordInput.setError(getString(R.string.password_cannot_be_empty));
            return false;
        } else {
            passwordInput.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = usernameInput.getText().toString().trim();
        String sanitizedUsername = userUsername.replace(".", ",");
        String userPassword = passwordInput.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference userRef = reference.child(sanitizedUsername);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usernameInput.setError(null);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, userPassword)) {
                        usernameInput.setError(null);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        passwordInput.setError("Invalid Credentials");
                        passwordInput.requestFocus();
                    }
                } else {
                    usernameInput.setError("User does not exist");
                    usernameInput.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
