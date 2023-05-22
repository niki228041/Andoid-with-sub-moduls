package com.example.sim.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.BaseActivity;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.application.HomeApplication;
import com.example.sim.contants.Urls;
import com.example.sim.dto.user.DecodedToken;
import com.example.sim.dto.user.LoginDTO;
import com.example.sim.dto.user.LoginResponse;
import com.example.sim.dto.user.Token;
import com.example.sim.security.JwtSecurityService;

import com.example.sim.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends BaseActivity {

    TextInputEditText txtEmail;
    TextInputEditText txtPassword;

    TextInputLayout tfEmail;
    TextInputLayout tfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txtEmail = findViewById(R.id.txtUserEmail);
        txtPassword = findViewById(R.id.txtUserPassword);

        tfEmail = findViewById(R.id.tfUserEmail);
        tfPassword = findViewById(R.id.tfUserPassword);

        if(HomeApplication.getInstance().isAuth())
        {
            Intent intent=new Intent(UserLoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        setupError();
    }

    private void setupError(){
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<=2){
                    tfEmail.setError("incorect value");
                }
                else {
                    tfEmail.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<1){
                    tfPassword.setError("incorect value");
                }
                else {
                    tfPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onClickLogin(View view){
        LoginDTO user = new LoginDTO();
        user.setEmail(txtEmail.getText().toString());
        user.setPassword(txtPassword.getText().toString());

        ApplicationNetwork
                .getInstance()
                .getAccountJsonApi()
                .login(user)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        String token = response.body().getToken();
                        JwtSecurityService jwt = HomeApplication.getInstance();

                        jwt.saveJwtToken(token);
                        testDecodeJWT(token);


                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        String s = t.toString();
                    }
                });
    }


    public void testDecodeJWT(String token){
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+ body);

        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        DecodedToken decodedToken = gson.fromJson(body, DecodedToken.class); // deserializes json into target2

//        RoleService roleService = HomeApplication.getInstance();
//        roleService.saveRole(decodedToken.roles);
    }

}