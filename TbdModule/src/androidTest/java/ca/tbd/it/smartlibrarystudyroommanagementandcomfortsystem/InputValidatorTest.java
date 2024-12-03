package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
public class InputValidatorTest {

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText phoneInput;

    @Before
    public void setUp() {
        nameInput = new EditText(ApplicationProvider.getApplicationContext());
        emailInput = new EditText(ApplicationProvider.getApplicationContext());
        passwordInput = new EditText(ApplicationProvider.getApplicationContext());
        confirmPasswordInput = new EditText(ApplicationProvider.getApplicationContext());
        phoneInput = new EditText(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void testValidateName_nonEmptyName_shouldReturnTrue() {
        nameInput.setText("John Doe");
        assertTrue("Valid name should return true", InputValidator.validateName(nameInput));
    }

    @Test
    public void testValidateName_emptyName_shouldReturnFalse() {
        nameInput.setText("");
        assertFalse("Empty name should return false", InputValidator.validateName(nameInput));
    }

    @Test
    public void testValidateEmail_validEmail_shouldReturnTrue() {
        emailInput.setText("example@test.com");
        assertTrue("Valid email should return true", InputValidator.validateEmail(emailInput));
    }

    @Test
    public void testValidateEmail_invalidEmail_shouldReturnFalse() {
        emailInput.setText("example@test");
        assertFalse("Invalid email should return false", InputValidator.validateEmail(emailInput));
    }

    @Test
    public void testValidatePassword_strongPassword_shouldReturnTrue() {
        passwordInput.setText("Password1@");
        confirmPasswordInput.setText("Password1@");
        assertTrue("Strong password should return true", InputValidator.validatePassword(passwordInput, confirmPasswordInput));
    }

    @Test
    public void testValidatePassword_weakPassword_shouldReturnFalse() {
        passwordInput.setText("pass");
        confirmPasswordInput.setText("pass");
        assertFalse("Weak password should return false", InputValidator.validatePassword(passwordInput, confirmPasswordInput));
    }

    @Test
    public void testValidatePassword_nonMatchingPasswords_shouldReturnFalse() {
        passwordInput.setText("Password1@");
        confirmPasswordInput.setText("Password2@");
        assertFalse("Non-matching passwords should return false", InputValidator.validatePassword(passwordInput, confirmPasswordInput));
    }

    @Test
    public void testValidatePhone_validPhone_shouldReturnTrue() {
        phoneInput.setText("1234567890");
        assertTrue("Valid phone number should return true", InputValidator.validatePhone(phoneInput));
    }

    @Test
    public void testValidatePhone_invalidPhone_shouldReturnFalse() {
        phoneInput.setText("12345");
        assertFalse("Invalid phone number should return false", InputValidator.validatePhone(phoneInput));
    }

    @Test
    public void testEditText_notNullAfterInitialization() {
        assertNotNull("EditText for name input should not be null", nameInput);
        assertNotNull("EditText for email input should not be null", emailInput);
        assertNotNull("EditText for password input should not be null", passwordInput);
        assertNotNull("EditText for confirm password input should not be null", confirmPasswordInput);
        assertNotNull("EditText for phone input should not be null", phoneInput);
    }

}
