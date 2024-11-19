package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileFragment extends Fragment {

    private EditText nameEditText, emailEditText, passwordEditText;
    private Spinner schoolSpinner;
    private Button saveButton;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nameEditText = view.findViewById(R.id.editTextName);
        emailEditText = view.findViewById(R.id.editTextEmail);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        schoolSpinner = view.findViewById(R.id.school_spinner);
        saveButton = view.findViewById(R.id.saveButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        String username = prefs.getString(KEY_USERNAME, "").replace(".", ",");

        if (!TextUtils.isEmpty(username)) {
            databaseReference.child(username).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                    nameEditText.setText(task.getResult().child("name").getValue(String.class));
                    emailEditText.setText(task.getResult().child("email").getValue(String.class));
                    passwordEditText.setText(task.getResult().child("password").getValue(String.class));
                } else {
                    Toast.makeText(requireContext(), "Unable to fetch user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        saveButton.setOnClickListener(v -> {
            String updatedName = nameEditText.getText().toString().trim();
            String updatedEmail = emailEditText.getText().toString().trim();
            String updatedPassword = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedEmail) || TextUtils.isEmpty(updatedPassword)) {
                Toast.makeText(requireContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            databaseReference.child(username).child("name").setValue(updatedName);
            databaseReference.child(username).child("email").setValue(updatedEmail);
            databaseReference.child(username).child("password").setValue(updatedPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to update profile.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
