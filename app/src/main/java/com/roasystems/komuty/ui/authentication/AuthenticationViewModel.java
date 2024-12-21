package com.roasystems.komuty.ui.authentication;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.roasystems.komuty.model.AuthRequest;
import com.roasystems.komuty.model.AuthResponse;
import com.roasystems.komuty.network.ApiService;
import com.roasystems.komuty.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AuthenticationViewModel extends ViewModel {
    private MutableLiveData<AuthResponse> authResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Getter for authResponseLiveData
    public LiveData<AuthResponse> getAuthResponse() {
        return authResponseLiveData;
    }

    // Getter for errorMessage LiveData
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Function to make the authentication request
    public void authenticateUser(String userName, String password, int applicationId) {
        // Create the request object
        AuthRequest authRequest = new AuthRequest(userName, password);

        // Get an instance of the ApiService
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Make the API call
        Call<AuthResponse> call = apiService.authenticate(applicationId, new AuthRequest(userName, password));

        // Asynchronous request to the API
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    // Success: Pass the response to the LiveData
                    authResponseLiveData.setValue(response.body());
                } else {
                    // Error: Set the error message
                    errorMessage.setValue("Authentication failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // Network failure or other errors
                errorMessage.setValue("An error occurred: " + t.getMessage());
                Log.e("AuthViewModel", "Error during authentication", t);
            }
        });
    }
}