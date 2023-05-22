package com.example.sim.network;

import com.example.sim.dto.user.GetByEmailDTO;
import com.example.sim.dto.user.LoginDTO;
import com.example.sim.dto.user.LoginResponse;
import com.example.sim.dto.user.RegistrationDTO;
import com.example.sim.dto.user.Token;
import com.example.sim.dto.user.UpdateUserDTO;
import com.example.sim.dto.user.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("/api/Account/login")
    public Call<LoginResponse> login(@Body LoginDTO model);

    @POST("/api/account/register")
    public Call<Void> registration(@Body RegistrationDTO model);

    @POST("/api/Account/getUserInfoByEmail")
    public Call<UserDTO> getUserInfoByEmail(@Body GetByEmailDTO model);

    @POST("/api/Account/changeUserInfo")
    public Call<Void> changeUserInfo(@Body UpdateUserDTO model);
}


