package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.view.View;

import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Test;

public class LoginActivityTest {

    private ActivityScenario<LoginActivity> scenario;

    @Before
    public void setUp() {
        // Launch the activity using ActivityScenario
        scenario = ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void testValidateUsername_empty_shouldReturnFalse() {
        scenario.onActivity(activity -> {
            activity.usernameInput.setText(""); // Simulate empty username
            boolean result = activity.validateUsername();
            assertFalse("Empty username should not be valid", result);
        });
    }

    @Test
    public void testValidateUsername_nonEmpty_shouldReturnTrue() {
        scenario.onActivity(activity -> {
            activity.usernameInput.setText("testUser"); // Simulate valid username
            boolean result = activity.validateUsername();
            assertTrue("Non-empty username should be valid", result);
        });
    }

    @Test
    public void testValidatePassword_empty_shouldReturnFalse() {
        scenario.onActivity(activity -> {
            activity.passwordInput.setText(""); // Simulate empty password
            boolean result = activity.validatePassword();
            assertFalse("Empty password should not be valid", result);
        });
    }

    @Test
    public void testValidatePassword_nonEmpty_shouldReturnTrue() {
        scenario.onActivity(activity -> {
            activity.passwordInput.setText("password123"); // Simulate valid password
            boolean result = activity.validatePassword();
            assertTrue("Non-empty password should be valid", result);
        });
    }

    @Test
    public void testRememberMeCheckbox_defaultStateUnchecked() {
        scenario.onActivity(activity -> {
            boolean isChecked = activity.rememberMeCheckBox.isChecked();
            assertFalse("Remember Me checkbox should be unchecked by default", isChecked);
        });
    }

    @Test
    public void testProgressBar_initiallyHidden() {
        scenario.onActivity(activity -> {
            int visibility = activity.progressBar.getVisibility();
            assertEquals("Progress bar should be initially hidden", View.GONE, visibility);
        });
    }

    // Additional Tests

    @Test
    public void testUsernameInput_initiallyEmpty() {
        scenario.onActivity(activity -> {
            String username = activity.usernameInput.getText().toString();
            assertEquals("Username input should be empty initially", "", username);
        });
    }

    @Test
    public void testPasswordInput_initiallyEmpty() {
        scenario.onActivity(activity -> {
            String password = activity.passwordInput.getText().toString();
            assertEquals("Password input should be empty initially", "", password);
        });
    }

    @Test
    public void testLoginButton_initiallyEnabled() {
        scenario.onActivity(activity -> {
            boolean isEnabled = activity.loginButton.isEnabled();
            assertTrue("Login button should be enabled initially", isEnabled);
        });
    }

    @Test
    public void testSignUpButton_initiallyVisible() {
        scenario.onActivity(activity -> {
            int visibility = activity.signUpText.getVisibility();
            assertEquals("Sign-Up button should be visible initially", View.VISIBLE, visibility);
        });
    }
}
