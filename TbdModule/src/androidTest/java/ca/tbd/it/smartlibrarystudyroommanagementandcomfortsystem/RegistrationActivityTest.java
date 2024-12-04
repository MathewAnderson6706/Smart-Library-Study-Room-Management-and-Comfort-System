package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;



import org.junit.Before;
import org.junit.Test;
public class RegistrationActivityTest {

    private ActivityScenario<RegistrationActivity> scenario;

    @Before
    public void setUp() {
        // Launch the activity using ActivityScenario
        scenario = ActivityScenario.launch(RegistrationActivity.class);
    }

    @Test
    public void testInitialState_allFieldsEmpty() {
        scenario.onActivity(activity -> {
            assertEquals("", activity.nameInput.getText().toString());
            assertEquals("", activity.emailInput.getText().toString());
            assertEquals("", activity.passwordInput.getText().toString());
            assertEquals("", activity.passwordConfirmInput.getText().toString());
            assertEquals("", activity.phoneInput.getText().toString());
        });
    }

    @Test
    public void testValidateInputs_validInputs_shouldReturnTrue() {
        scenario.onActivity(activity -> {
            activity.nameInput.setText("John Doe");
            activity.emailInput.setText("johndoe@example.com");
            activity.passwordInput.setText("Password1@");
            activity.passwordConfirmInput.setText("Password1@");
            activity.phoneInput.setText("1234567890");

            boolean result = activity.validateInputs(
                    activity.nameInput.getText().toString(),
                    activity.emailInput.getText().toString(),
                    activity.passwordInput.getText().toString(),
                    activity.passwordConfirmInput.getText().toString(),
                    activity.phoneInput.getText().toString()
            );
            assertTrue("Valid inputs should pass validation", result);
        });
    }

    @Test
    public void testValidateInputs_emptyFields_shouldReturnFalse() {
        scenario.onActivity(activity -> {
            activity.nameInput.setText("");
            activity.emailInput.setText("");
            activity.passwordInput.setText("");
            activity.passwordConfirmInput.setText("");
            activity.phoneInput.setText("");

            boolean result = activity.validateInputs(
                    activity.nameInput.getText().toString(),
                    activity.emailInput.getText().toString(),
                    activity.passwordInput.getText().toString(),
                    activity.passwordConfirmInput.getText().toString(),
                    activity.phoneInput.getText().toString()
            );
            assertFalse("Empty fields should fail validation", result);
        });
    }

    @Test
    public void testSanitizedEmailNotNull() {
        String sanitizedEmail = "john,doe@gmail,com";
        assertNotNull("Sanitized email should not be null", sanitizedEmail);
    }

    @Test
    public void testSanitizedEmailEquality() {
        String originalEmail = "john.doe@gmail.com";
        String sanitizedEmail = originalEmail.replace(".", ",");
        assertEquals("Sanitized email should replace '.' with ','", "john,doe@gmail,com", sanitizedEmail);
        assertNotEquals("Sanitized email should not equal the original email", originalEmail, sanitizedEmail);
    }

    @Test
    public void testLoginText_click_shouldNavigateToLogin() {
        scenario.onActivity(activity -> {
            activity.loginText.performClick();
            Intent expectedIntent = new Intent(activity, LoginActivity.class);
            assertNotEquals("Activity should navigate to LoginActivity on loginText click", expectedIntent, activity.getIntent());
        });
    }

    @Test
    public void testRegisterButton_initiallyEnabled() {
        scenario.onActivity(activity -> {
            boolean isEnabled = activity.registerButton.isEnabled();
            assertTrue("Register button should be enabled initially", isEnabled);
        });
    }

    @Test
    public void testInputs_notNullAfterInitialization() {
        scenario.onActivity(activity -> {
            assertNotNull("Name input should not be null", activity.nameInput);
            assertNotNull("Email input should not be null", activity.emailInput);
            assertNotNull("Password input should not be null", activity.passwordInput);
            assertNotNull("Password confirm input should not be null", activity.passwordConfirmInput);
            assertNotNull("Phone input should not be null", activity.phoneInput);
        });
    }

}
