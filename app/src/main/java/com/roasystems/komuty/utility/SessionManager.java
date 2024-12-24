package com.roasystems.komuty.utility;
import android.content.Context;
import android.content.SharedPreferences;
public class SessionManager {
    private static final String PREF_NAME = "user_session"; // SharedPreferences file name
    private static final String KEY_IS_LOGGED_IN = "is_logged_in"; // Key to store login status
    private static final String KEY_TOKEN = "token"; // Key to store token

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); // Initialize SharedPreferences
        editor = sharedPreferences.edit(); // Get editor to modify SharedPreferences
    }

    // Save login status
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn); // Put the login status in SharedPreferences
        editor.apply(); // Commit changes asynchronously
    }

    // Get login status
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false); // Return login status; false by default
    }
    // Clear session details
    public void logout() {
        editor.clear(); // Clear all data from SharedPreferences
        editor.apply(); // Commit changes asynchronously
    }

    public void createLoginSession(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }
}
