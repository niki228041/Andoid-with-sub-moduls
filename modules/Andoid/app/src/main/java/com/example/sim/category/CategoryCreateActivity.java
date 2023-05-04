package com.example.sim.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.example.sim.dto.category.CategoryCreateDTO;
import com.example.sim.service.CategoryNetwork;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends AppCompatActivity {
    private static int SELECT_IMAGE_RESULT=300;
    Uri uri = null;
    ImageView IVPreviewImage;

    TextInputEditText txtCategoryName;
    TextInputEditText txtCategoryPriority;
    TextInputEditText txtCategoryDescription;

    TextInputLayout tfCategoryName;
    TextInputLayout tfCategoryPriority;
    TextInputLayout tfCategoryDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtCategoryPriority = findViewById(R.id.txtCategoryPriority);
        txtCategoryDescription = findViewById(R.id.txtCategoryDescription);

        tfCategoryName = findViewById(R.id.tfCategoryName);
        tfCategoryPriority = findViewById(R.id.tfCategoryPriority);
        tfCategoryDescription = findViewById(R.id.tfCategoryDescription);

        setupError();
    }



    private boolean validation() {
        boolean isValid=true;
        String name = txtCategoryName.getText().toString();
        String description = txtCategoryDescription.getText().toString();
        String priority = txtCategoryPriority.getText().toString();



        if(name.isEmpty()) {
            tfCategoryName.setError(getString(R.string.category_name_required));
            isValid=false;
        }


        int number = 0;

        try {
            number = Integer.parseInt(txtCategoryPriority.getText().toString());
        }catch (Exception ex){
            if(number<=0)
            {
                tfCategoryPriority.setError(getString(R.string.category_priority_required));
                isValid=false;
            }
        }

        if(description.isEmpty()) {
            tfCategoryDescription.setError(getString(R.string.category_description_required));
            isValid=false;
        }

        if(uri == null){
            isValid=false;
            Toast.makeText(this, "imgNotFound", Toast.LENGTH_SHORT).show();
        }

//        else if(description.isEmpty()){
//            tfCategoryDescription.setError("Вкажіть назву");
//            isValid=false;
//        }
//        else if(priority.isEmpty()){
//            tfCategoryPriority.setError("Вкажіть назву");
//            isValid=false;
//        }
//        else {
//            tfCategoryName.setError("");
//            tfCategoryDescription.setError("");
//            tfCategoryPriority.setError("");
//        }

        return isValid;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if (requestCode == SELECT_IMAGE_RESULT) {
            uri = (android.net.Uri)data.getParcelableExtra("croppedUri");
            IVPreviewImage.setImageURI(uri);
        }

    }

    private void setupError(){
        txtCategoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<=2){
                    tfCategoryName.setError(getString(R.string.category_name_required));
                }
                else {
                    tfCategoryName.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtCategoryPriority.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<1){
                    tfCategoryPriority.setError(getString(R.string.category_priority_required));
                }
                else {
                    tfCategoryPriority.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtCategoryDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                if(text.length()<=2){
                    tfCategoryDescription.setError(getString(R.string.category_description_required));
                }
                else {
                    tfCategoryDescription.setError("");
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

//        Intent intent = new Intent();
//
//
//        String croppedUri = intent.getStringExtra("croppedUri");
//
//// Check if the value is null or empty
//        if (croppedUri == null || croppedUri.isEmpty()) {
//            return;
//        }


        CategoryCreateDTO model = new CategoryCreateDTO();
        model.setName(txtCategoryName.getText().toString());
        model.setPriority(Integer.parseInt(txtCategoryPriority.getText().toString()));
        model.setDescription(txtCategoryDescription.getText().toString());
        model.setImageBase64(uriGetBase64(uri));
        CategoryNetwork.getInstance()
                .getJsonApi()
                .create(model)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
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
