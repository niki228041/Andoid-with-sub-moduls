package com.example.sim.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.BaseActivity;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.application.HomeApplication;
import com.example.sim.auth.UserLoginActivity;
import com.example.sim.category.CategoryCreateActivity;
import com.example.sim.contants.Urls;
import com.example.sim.dto.user.GetByEmailDTO;
import com.example.sim.dto.user.UpdateUserDTO;
import com.example.sim.dto.user.UserDTO;
import com.example.sim.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        txtFirstName = findViewById(R.id.txtChangeFirstName);
        txtLastName = findViewById(R.id.txtChangeLastName);
        txtEmailName = findViewById(R.id.txtChangeUserEmail);
        txtUserId = findViewById(R.id.userId);

        avatarImage = findViewById(R.id.IVPreviewImage);


        String email = getIntent().getStringExtra("email");
        getUserByEmail(email);
    }

    TextInputEditText txtFirstName;
    TextInputEditText txtLastName;
    TextInputEditText txtEmailName;
    TextView txtUserId;
    ImageView avatarImage;

    public void getUserByEmail(String email){
        GetByEmailDTO request = new GetByEmailDTO();
        request.setEmail(email);

        ApplicationNetwork
                .getInstance()
                .getAccountJsonApi()
                .getUserInfoByEmail(request)
                .enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        UserDTO user = response.body();
                        txtFirstName.setText(user.firstName);
                        txtLastName.setText(user.lastName);
                        txtEmailName.setText(user.email);
                        txtUserId.setText(Integer.toString(user.id));

                        Glide.with(HomeApplication.getAppContext())
                                .load(Urls.BASE+"/images/"+user.image)
                                .apply(new RequestOptions().override(600))
                                .into(avatarImage);
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {

                    }
                });
    }

    public void changeUserById(){
        UpdateUserDTO request = new UpdateUserDTO();
        request.setEmail(txtEmailName.getText().toString());
        request.setFirstName(txtFirstName.getText().toString());
        request.setLastName(txtLastName.getText().toString());
        request.setId(Integer.parseInt(txtUserId.getText().toString()));
//        request.setImage(avatarImage.toString());



        UpdateUserDTO request_ = request;

        ApplicationNetwork
                .getInstance()
                .getAccountJsonApi()
                .changeUserInfo(request)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        HomeApplication.getInstance().deleteToken();
                        Intent intent = new Intent(ChangeProfileActivity.this, UserLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }

    public void onClickChange(View view){
        changeUserById();
    }




}