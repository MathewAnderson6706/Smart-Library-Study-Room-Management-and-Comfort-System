/*
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

@RunWith(org.robolectric.RobolectricTestRunner.class)
@Config(sdk = {29}) // Use SDK 29 for Robolectric compatibility
public class LoginActivityTest {

    private LoginActivity loginActivity;
    private SharedPreferences mockSharedPreferences;
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        // Initialize LoginActivity using Robolectric
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();

        // Mock UI components
        loginActivity.usernameInput = mock(EditText.class);
        loginActivity.passwordInput = mock(EditText.class);
        loginActivity.rememberMeCheckBox = mock(CheckBox.class);

        // Mock SharedPreferences and Editor
        mockSharedPreferences = mock(SharedPreferences.class);
        mockEditor = mock(SharedPreferences.Editor.class);

        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        when(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor);
        when(mockEditor.putBoolean(anyString(), anyBoolean())).thenReturn(mockEditor);
        when(mockEditor.remove(anyString())).thenReturn(mockEditor);

        doNothing().when(mockEditor).apply();
    }

    @Test
    public void validateUsername_shouldReturnTrueForValidInput() {
        when(loginActivity.usernameInput.getText().toString()).thenReturn("validUser");

        boolean result = loginActivity.validateUsername();

        assertTrue("Username validation should return true for valid input", result);
    }

    @Test
    public void validateUsername_shouldReturnFalseForEmptyInput() {
        when(loginActivity.usernameInput.getText().toString()).thenReturn("");

        boolean result = loginActivity.validateUsername();

        assertFalse("Username validation should return false for empty input", result);
    }

    @Test
    public void validatePassword_shouldReturnTrueForValidInput() {
        when(loginActivity.passwordInput.getText().toString()).thenReturn("validPass");

        boolean result = loginActivity.validatePassword();

        assertTrue("Password validation should return true for valid input", result);
    }

    @Test
    public void validatePassword_shouldReturnFalseForEmptyInput() {
        when(loginActivity.passwordInput.getText().toString()).thenReturn("");

        boolean result = loginActivity.validatePassword();

        assertFalse("Password validation should return false for empty input", result);
    }

    @Test
    public void saveUserInfo_shouldStoreUsernameAndPassword() throws Exception {
        // Access private saveUserInfo using reflection
        Method saveUserInfo = LoginActivity.class.getDeclaredMethod("saveUserInfo", String.class, String.class);
        saveUserInfo.setAccessible(true);

        // Call private method
        saveUserInfo.invoke(loginActivity, "testUser", "testPass");

        // Verify SharedPreferences interactions
        verify(mockEditor).putString("username", "testUser");
        verify(mockEditor).putString("password", "testPass");
        verify(mockEditor).putBoolean("remember_me", true);
        verify(mockEditor).apply();
    }

    @Test
    public void clearUserInfo_shouldClearSavedUsernameAndPassword() throws Exception {
        // Access private clearUserInfo using reflection
        Method clearUserInfo = LoginActivity.class.getDeclaredMethod("clearUserInfo");
        clearUserInfo.setAccessible(true);

        // Call private method
        clearUserInfo.invoke(loginActivity);

        // Verify SharedPreferences interactions
        verify(mockEditor).remove("username");
        verify(mockEditor).remove("password");
        verify(mockEditor).remove("remember_me");
        verify(mockEditor).apply();
    }

    @Test
    public void checkUser_shouldSaveCredentialsWhenRememberMeChecked() {
        when(loginActivity.rememberMeCheckBox.isChecked()).thenReturn(true);

        // Simulate user input
        when(loginActivity.usernameInput.getText().toString()).thenReturn("testUser");
        when(loginActivity.passwordInput.getText().toString()).thenReturn("testPass");

        loginActivity.checkUser();

        // Verify SharedPreferences interactions
        verify(mockEditor).putString("username", "testUser");
        verify(mockEditor).putString("password", "testPass");
        verify(mockEditor).putBoolean("remember_me", true);
        verify(mockEditor).apply();
    }

    @Test
    public void checkUser_shouldNotSaveCredentialsWhenRememberMeUnchecked() {
        when(loginActivity.rememberMeCheckBox.isChecked()).thenReturn(false);

        // Simulate user input
        when(loginActivity.usernameInput.getText().toString()).thenReturn("testUser");
        when(loginActivity.passwordInput.getText().toString()).thenReturn("testPass");

        loginActivity.checkUser();

        // Verify that SharedPreferences is not updated
        verify(mockEditor, never()).putString("username", "testUser");
        verify(mockEditor, never()).putString("password", "testPass");
    }

    @Test
    public void validateUsername_shouldHandleNullInputGracefully() {
        when(loginActivity.usernameInput.getText().toString()).thenReturn(null);

        boolean result = loginActivity.validateUsername();

        assertFalse("Username validation should return false for null input", result);
    }


    @Test
    public void validatePassword_shouldHandleNullInputGracefully() {
        when(loginActivity.passwordInput.getText().toString()).thenReturn(null);

        boolean result = loginActivity.validatePassword();

        assertFalse("Password validation should return false for null input", result);
    }
}

*/