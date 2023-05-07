package com.example.sim.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.dto.user.LoginDTO;
import com.example.sim.dto.user.Token;
import com.example.sim.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends AppCompatActivity {

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
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        String s = t.toString();
                    }
                });
    }
}