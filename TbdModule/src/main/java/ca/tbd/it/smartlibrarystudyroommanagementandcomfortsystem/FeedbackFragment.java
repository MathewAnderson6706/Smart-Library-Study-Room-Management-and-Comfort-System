package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class FeedbackFragment extends Fragment {

    private EditText fullNameInput, phoneNumberInput, emailInput, commentBox;
    private RatingBar ratingBar;
    private Button submitButton;
    private TextView countdownTimerText;
    private FirebaseDatabase database;
    private DatabaseReference feedbackRef;
    private Handler handler = new Handler();
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private DatabaseReference databaseReference;

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
        countdownTimerText = view.findViewById(R.id.countdownTimerText);

        database = FirebaseDatabase.getInstance();
        feedbackRef = database.getReference("feedback");

        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        if (!canSubmitFeedback()) {
            disableSubmitButton();
            startCountdown();
        }

        submitButton.setOnClickListener(v -> {
            if (canSubmitFeedback()) {
                if (validateInput()) { // Validate the input before submitting
                    submitFeedback();
                }
            } else {
                Toast.makeText(getContext(), "You can only submit feedback once every 24 hours.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void sendNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "FEEDBACK_CHANNEL")
                .setSmallIcon(R.drawable.ic_launcher_playstore2)
                .setContentTitle("Feedback Available")
                .setContentText("You can submit feedback now!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

    private boolean canSubmitFeedback() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String username = getLoggedInUsername();
        long lastSubmissionTime = preferences.getLong(username + "_last_feedback_time", 0); // Use username as the key
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastSubmissionTime) >= 86400000; // 24 hours in milliseconds
    }

    private void saveSubmissionTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        String username = getLoggedInUsername();
        editor.putLong(username + "_last_feedback_time", System.currentTimeMillis()); // Store the time per user
        editor.apply();
    }

    private String getLoggedInUsername() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        return prefs.getString(KEY_USERNAME, "");
    }

    private void disableSubmitButton() {
        submitButton.setEnabled(false);
        submitButton.setAlpha(0.5f);
    }

    private void enableSubmitButton() {
        submitButton.setEnabled(true);
        submitButton.setAlpha(1.0f);
    }

    private void startCountdown() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Ensure the fragment is attached to an activity before accessing context
                if (getContext() != null) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String username = getLoggedInUsername(); // Fetch logged-in username
                    long lastSubmissionTime = preferences.getLong(username + "_last_feedback_time", 0); // Use username as the key
                    long remainingTime = 86400000 - (System.currentTimeMillis() - lastSubmissionTime);

                    if (remainingTime > 0) {
                        long hours = TimeUnit.MILLISECONDS.toHours(remainingTime);
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60;
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60;
                        countdownTimerText.setText(String.format("You can submit feedback in %02d:%02d:%02d", hours, minutes, seconds));
                        handler.postDelayed(this, 1000);
                    } else {
                        countdownTimerText.setText("");
                        enableSubmitButton();
                        sendNotification(getContext());
                    }
                }
            }
        });
    }

    private boolean validateInput() {
        if (!InputValidator.validateName(fullNameInput)) return false;
        if (!InputValidator.validateEmail(emailInput)) return false;
        if (!InputValidator.validatePhone(phoneNumberInput)) return false;
        if (TextUtils.isEmpty(commentBox.getText())) {
            commentBox.setError("Comment is required");
            return false;
        }
        return true;
    }

    private void showAlertDialog(String title, String message) {
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
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

        ProgressBar progressBar = getView().findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {
            feedbackRef.push().setValue(feedback).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    saveSubmissionTime();
                    disableSubmitButton();
                    startCountdown();
                    showAlertDialog("Success", "Feedback submitted successfully!");
                    clearFields();
                } else {
                    showAlertDialog("Error", "Failed to submit feedback. Try again!");
                }
            });
        }, 5000);
    }


    private void clearFields() {
        fullNameInput.setText("");
        phoneNumberInput.setText("");
        emailInput.setText("");
        commentBox.setText("");
        ratingBar.setRating(0);
    }
}
