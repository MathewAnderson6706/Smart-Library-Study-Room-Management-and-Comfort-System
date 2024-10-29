package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private Button registerButton;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);

        loginText.setOnClickListener(v -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        });
    }
}
