package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
public class InputValidator {

    public static boolean validateName(EditText nameInput) {
        String name = nameInput.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameInput.setError("Name is required");
            return false;
        }
        return true;
    }

    public static boolean validateEmail(EditText emailInput) {
        String email = emailInput.getText().toString();
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email address");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(EditText passwordInput, EditText confirmPasswordInput) {
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (password.length() < 6 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*") ||
                !password.matches(".*[@#$%^&+=!].*") || !password.matches(".*[A-Z].*")) {
            passwordInput.setError("Password must be at least 6 characters, contain upper case letters, digits, and special characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    public static boolean validatePhone(EditText phoneInput) {
        String phone = phoneInput.getText().toString();
        if (phone.length() != 10 || !phone.matches("\\d{10}")) {
            phoneInput.setError("Enter a valid 10-digit phone number");
            return false;
        }
        return true;
    }

}
