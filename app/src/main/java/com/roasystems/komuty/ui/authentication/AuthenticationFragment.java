package com.roasystems.komuty.ui.authentication;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.roasystems.komuty.model.AuthRequest;
import com.roasystems.komuty.model.AuthResponse;
import com.roasystems.komuty.network.ApiService;
import com.roasystems.komuty.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roasystems.komuty.R;
import com.roasystems.komuty.utility.SessionManager;

public class AuthenticationFragment extends Fragment {
    private EditText userNameEditText, passwordEditText;
    private Button loginButton;
    private AuthenticationViewModel mViewModel;

    public static AuthenticationFragment newInstance() {
        return new AuthenticationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authentication, container, false);

        // Initialize UI components
        userNameEditText = rootView.findViewById(R.id.userNameEditText);
        passwordEditText = rootView.findViewById(R.id.passwordEditText);
        loginButton = rootView.findViewById(R.id.loginButton);

        // Handle login button click
        loginButton.setOnClickListener(v -> attemptLogin());

        return rootView;
    }
    private void attemptLogin() {
        // Get user inputs
        String userName = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare AuthRequest object
        AuthRequest authRequest = new AuthRequest(userName, password);

        // Call the authentication API
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AuthResponse> call = apiService.authenticate(3, authRequest);  // Dynamic applicationId

        // Extract and display the URL
        String url = call.request().url().toString();
        String jsonBody = new Gson().toJson(authRequest);
        Toast.makeText(getActivity(), "URL: " + url + "\nBody: " + jsonBody, Toast.LENGTH_LONG).show();



        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    AuthResponse authResponse = response.body();
                    String token = authResponse.getToken();
                    Toast.makeText(getActivity(), "Login Successful! Token: " + token, Toast.LENGTH_SHORT).show();
                    // Save session and navigate to home
                    SessionManager sessionManager = new SessionManager(getActivity());
                    sessionManager.createLoginSession(token);

                    // Navigate to home fragment
                    NavController navController = Navigation.findNavController(getActivity(), R.id.fragment_Container);
                    navController.navigate(R.id.action_authenticationFragment_to_homeFragment);

                    // You can save the token for later use or proceed with your app's flow
                } else {
                    // Handle error response
                    Toast.makeText(getActivity(), "Login Failed! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(getActivity(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        // TODO: Use the ViewModel
    }

}