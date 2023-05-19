package com.example.sim.network;

import com.example.sim.dto.user.LoginDTO;
import com.example.sim.dto.user.RegistrationDTO;
import com.example.sim.dto.user.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("/api/Account/login")
    public Call<Void> login(@Body LoginDTO model);

    @POST("/api/Account/register")
    public Call<Void> registration(@Body RegistrationDTO model);
}


