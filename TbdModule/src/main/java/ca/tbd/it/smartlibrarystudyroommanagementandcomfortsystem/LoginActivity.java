package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    TextView signUpText;
    CheckBox rememberMeCheckBox;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpButton);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckbox);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isRemembered = prefs.getBoolean(KEY_REMEMBER_ME, false);

        if (isRemembered) {
            String savedUsername = prefs.getString(KEY_USERNAME, "");
            String savedPassword = prefs.getString(KEY_PASSWORD, "");
            usernameInput.setText(savedUsername);
            passwordInput.setText(savedPassword);
            rememberMeCheckBox.setChecked(true);
        }

        loginButton.setOnClickListener(v -> {
            if (!validateUsername() || !validatePassword()) {
                return;
            }
            checkUser();
        });

        signUpText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }

    public Boolean validateUsername() {
        String val = usernameInput.getText().toString();
        if (val.isEmpty()) {
            usernameInput.setError(getString(R.string.username_cannot_be_empty));
            return false;
        } else {
            usernameInput.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = passwordInput.getText().toString();
        if (val.isEmpty()) {
            passwordInput.setError(getString(R.string.password_cannot_be_empty));
            return false;
        } else {
            passwordInput.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = usernameInput.getText().toString().trim();
        String sanitizedUsername = userUsername.replace(".", ",");
        String userPassword = passwordInput.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(getString(R.string.users1));
        DatabaseReference userRef = reference.child(sanitizedUsername);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usernameInput.setError(null);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, userPassword)) {
                        usernameInput.setError(null);

                        // Save user info if "Remember Me" is checked
                        if (rememberMeCheckBox.isChecked()) {
                            saveUserInfo(userUsername, userPassword);
                        } else {
                            clearUserInfo();
                        }

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        passwordInput.setError("Invalid Credentials");
                        passwordInput.requestFocus();
                    }
                } else {
                    usernameInput.setError(getString(R.string.user_does_not_exist));
                    usernameInput.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void saveUserInfo(String username, String password) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER_ME, true);
        editor.apply();
    }

    private void clearUserInfo() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_REMEMBER_ME);
        editor.apply();
    }
}
