package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {

    private EditText fullNameInput, phoneNumberInput, emailInput, commentBox;
    private RatingBar ratingBar;
    private Button submitButton;
    private FirebaseDatabase database;
    private DatabaseReference feedbackRef;

    public FeedbackFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        fullNameInput = view.findViewById(R.id.fullNameInput);
        phoneNumberInput = view.findViewById(R.id.phoneNumberInput);
        emailInput = view.findViewById(R.id.EmailInput);
        commentBox = view.findViewById(R.id.commentBox);
        ratingBar = view.findViewById(R.id.rating);
        submitButton = view.findViewById(R.id.submitButton);

        database = FirebaseDatabase.getInstance();
        feedbackRef = database.getReference("feedback");

        submitButton.setOnClickListener(v -> {
            if (canSubmitFeedback()) {
                submitFeedback();
            } else {
                Toast.makeText(getContext(), "You can only submit feedback once every 24 hours.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private boolean canSubmitFeedback() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        long lastSubmissionTime = preferences.getLong("last_feedback_time", 0);
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastSubmissionTime) >= 86400000;
    }

    private void saveSubmissionTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("last_feedback_time", System.currentTimeMillis());
        editor.apply();
    }

    private void submitFeedback() {
        String fullName = fullNameInput.getText().toString().trim();
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String comment = commentBox.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (fullName.isEmpty() || email.isEmpty() || comment.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FeedbackHelper feedback = new FeedbackHelper(fullName, phoneNumber, email, comment, rating);

        feedbackRef.push().setValue(feedback).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveSubmissionTime();
                Toast.makeText(getContext(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(getContext(), "Failed to submit feedback. Try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        fullNameInput.setText("");
        phoneNumberInput.setText("");
        emailInput.setText("");
        commentBox.setText("");
        ratingBar.setRating(0);
    }
}
