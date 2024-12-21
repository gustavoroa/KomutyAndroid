package com.roasystems.komuty.network;
import com.roasystems.komuty.model.AuthRequest;
import com.roasystems.komuty.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    // POST request to authenticate the user
    @POST("Security/authentication")
    Call<AuthResponse> authenticate(
            @Query("applicationId") int applicationId,  // Dynamic applicationId passed as a query parameter
            @Body AuthRequest authRequest  // The request body contains the user's credentials
    );
}
