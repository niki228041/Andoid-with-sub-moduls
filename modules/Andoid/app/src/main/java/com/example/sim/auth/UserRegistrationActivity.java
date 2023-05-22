package com.example.sim.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sim.ChangeImageActivity;
import com.example.sim.MainActivity;
import com.example.sim.R;
import com.example.sim.category.CategoryCreateActivity;
import com.example.sim.dto.category.CategoryCreateDTO;
import com.example.sim.dto.user.LoginResponse;
import com.example.sim.dto.user.RegistrationDTO;
import com.example.sim.dto.user.ValidationRegisterDTO;
import com.example.sim.service.ApplicationNetwork;
import com.example.sim.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        txtUserFirstName = findViewById(R.id.txtUserFirstName);
        txtUserLastName = findViewById(R.id.txtUserLastName);
        txtPassword = findViewById(R.id.txtUserPassword);
        txtConfirmPassword = findViewById(R.id.txtUserConfirmPassword);
        txtEmail = findViewById(R.id.txtUserEmail);

        tfUserFirstName = findViewById(R.id.tfUserFirstName);
        tfUserLastName = findViewById(R.id.tfUserLastName);
        tfPassword = findViewById(R.id.tfUserPassword);
        tfConfirmPassword = findViewById(R.id.tfUserConfirmPassword);
        tfEmail = findViewById(R.id.tfUserEmail);


        setupError();
    }

    private static int SELECT_IMAGE_RESULT=300;
    Uri uri = null;
    ImageView IVPreviewImage;

    TextInputEditText txtUserFirstName;
    TextInputEditText txtUserLastName;
    TextInputEditText txtPassword;
    TextInputEditText txtConfirmPassword;
    TextInputEditText txtEmail;

    TextInputLayout tfUserFirstName;
    TextInputLayout tfUserLastName;
    TextInputLayout tfPassword;
    TextInputLayout tfConfirmPassword;
    TextInputLayout tfEmail;


    private boolean validation() {
        boolean isValid=true;

        String firstname = txtUserFirstName.getText().toString();
        String lastname = txtUserLastName.getText().toString();
        String email = txtEmail.getText().toString();
        String confirm_password = txtConfirmPassword.getText().toString();
        String password = txtPassword.getText().toString();



        if(firstname.isEmpty()) {
            tfUserFirstName.setError(getString(R.string.user_firstname_required));
            isValid=false;
        }

        if(lastname.isEmpty()) {
            tfUserLastName.setError(getString(R.string.user_lastname_required));
            isValid=false;
        }

        if(email.isEmpty()) {
            tfEmail.setError(getString(R.string.user_email_required));
            isValid=false;
        }

        if(password.isEmpty()) {
            tfPassword.setError(getString(R.string.user_password_required));
            isValid=false;
        }

        if(confirm_password.isEmpty()) {
            tfConfirmPassword.setError(getString(R.string.user_confirm_password_required));
            isValid=false;
        }

        if(!password.equals(confirm_password)) {
            tfConfirmPassword.setError(getString(R.string.user_confirm_password_with_password_required));
            isValid=false;
        }

        if(uri == null){
            isValid=false;
            Toast.makeText(this, "imgNotFound", Toast.LENGTH_SHORT).show();
        }


        return isValid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if (requestCode == SELECT_IMAGE_RESULT) {
            uri = (android.net.Uri)data.getParcelableExtra("croppedUri");
            IVPreviewImage.setImageURI(uri);
        }

    }

    private void setupError(){
        txtUserFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<1){
                    tfUserFirstName.setError(getString(R.string.user_firstname_required));
                }
                else {
                    tfUserFirstName.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtUserLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<1){
                    tfUserLastName.setError(getString(R.string.user_lastname_required));
                }
                else {
                    tfUserLastName.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<1){
                    tfEmail.setError(getString(R.string.user_email_required));
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
                    tfPassword.setError(getString(R.string.user_password_required));
                }
                else {
                    tfPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<1){
                    tfConfirmPassword.setError(getString(R.string.user_confirm_password_required));
                }
                else {
                    tfConfirmPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //Add category and send to server
    public void onClickCreateCategory(View view){

        if(!validation())
            return;

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName(txtUserFirstName.getText().toString());
        registrationDTO.setLastName(txtUserLastName.getText().toString());
        registrationDTO.setEmail(txtEmail.getText().toString());
        registrationDTO.setPassword(txtPassword.getText().toString());
        registrationDTO.setConfirmPassword(txtConfirmPassword.getText().toString());
        registrationDTO.setImageBase64(uriGetBase64(uri));

        RegistrationDTO d = registrationDTO;


        ApplicationNetwork.getInstance()
                .getAccountJsonApi()
                .registration(registrationDTO)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Boolean resp_ = response.isSuccessful();
                        Boolean resp__ = resp_;

                        if(response.isSuccessful()) {
                            Intent intent = new Intent(UserRegistrationActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            try {
                                String resp = response.errorBody().string();
                                showErrorsServer(resp);
                            }catch(Exception ex) {
                                System.out.println("Error try");;
                            }

                        }
                        CommonUtils.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });

    }

    private void showErrorsServer(String json){
        ValidationRegisterDTO result = new Gson().fromJson(json,ValidationRegisterDTO.class);
        String str="";

        if(result.getErrors().getEmail()!=null){
            for(String item: result.getErrors().getEmail()){
                str=item;
            }
        }

        if(result.getErrors().getFirstName()!=null){
            for(String item: result.getErrors().getFirstName()){
                str=item;
            }
        }

        if(result.getErrors().getLastName()!=null){
            for(String item: result.getErrors().getLastName()){
                str=item;
            }
        }

        if(result.getErrors().getPassword()!=null){
            for(String item: result.getErrors().getPassword()){
                str=item;
            }
        }

        if(result.getErrors().getConfirmPassword()!=null){
            for(String item: result.getErrors().getConfirmPassword()){
                str=item;
            }
        }

        if(result.getErrors().getImageBase64()!=null){
            for(String item: result.getErrors().getImageBase64()){
                str=item;
            }
        }

        String str_ = str;

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private String uriGetBase64(Uri uri) {
        try {
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch(IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] byteArr = bytes.toByteArray();
            return Base64.encodeToString(byteArr, Base64.DEFAULT);

        } catch(Exception ex) {
            return null;
        }
    }



    //Select img and crop that
    public void onClickSelectImagePic(View view){
        Intent intent = new Intent(this, ChangeImageActivity.class);
        startActivityForResult(intent,SELECT_IMAGE_RESULT);
    }
}