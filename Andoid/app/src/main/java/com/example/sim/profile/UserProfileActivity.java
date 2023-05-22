package com.example.sim.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.R;
import com.example.sim.application.HomeApplication;
import com.example.sim.contants.Urls;
import com.example.sim.dto.user.DecodedToken;
import com.example.sim.dto.user.GetByEmailDTO;
import com.example.sim.dto.user.UserDTO;
import com.example.sim.security.JwtSecurityService;
import com.example.sim.service.ApplicationNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    ImageView avatarImage;
    TextView txtEmail;
    TextView justTxtEmail;
    TextView txtRole;
    Button butChangeInfo;

    UserDTO user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        txtEmail = findViewById(R.id.email);
        txtRole = findViewById(R.id.role);
        avatarImage = findViewById(R.id.user_avatar);
        justTxtEmail = findViewById(R.id.justTxtEmail);
        butChangeInfo = findViewById(R.id.change_info);



        JwtSecurityService jwt = HomeApplication.getInstance();
        String token = jwt.getToken();
        testDecodeJWT(token);

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
        txtEmail.setText(decodedToken.name);
        txtRole.setText(decodedToken.roles);

        Glide.with(HomeApplication.getAppContext())
                .load(Urls.BASE+"/images/"+decodedToken.image)
                .apply(new RequestOptions().override(600))
                .into(avatarImage);

    }

    public void onClickChange(View view){
        Intent intent = new Intent(UserProfileActivity.this,ChangeProfileActivity.class);
        intent.putExtra("email", txtEmail.getText());
        startActivity(intent);
        finish();
    }


}