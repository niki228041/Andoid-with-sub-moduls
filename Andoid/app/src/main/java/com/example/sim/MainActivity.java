package com.example.sim;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.application.HomeApplication;
import com.example.sim.category.CategoriesAdapter;
import com.example.sim.category.CategoryEditActivity;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.dto.category.ListRequestDTO;
import com.example.sim.dto.user.DecodedToken;
import com.example.sim.security.JwtSecurityService;
import com.example.sim.service.ApplicationNetwork;
import com.example.sim.utils.CommonUtils;
import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    CategoriesAdapter adapter;
    RecyclerView rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = findViewById(R.id.imageView);
//        String url = "https://pv016.allin.ml/images/1.jpg";
        String url = "https://pv016.allin.ml/images/1.jpg";


//        testText.setText();

        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().override(600))
                .into(iv);



        rc = findViewById(R.id.rcvCategories);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        rc.setAdapter(new CategoriesAdapter(new ArrayList<>(), MainActivity.this::onClickEditCategory, MainActivity.this::onClickDeleteCategory));
        requestServer();
    }


    void requestServer() {

        JwtSecurityService jwt = HomeApplication.getInstance();
        String token = jwt.getToken();
        ListRequestDTO listRequest = new ListRequestDTO();
        listRequest.setEmail(emailDecodeJWT(token));

        ApplicationNetwork
                .getInstance()
                .getCategoriesJsonApi()
                .list(listRequest)
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        List<CategoryItemDTO> data = response.body();
                        adapter = new CategoriesAdapter(data, MainActivity.this::onClickEditCategory, MainActivity.this::onClickDeleteCategory);
                        rc.setAdapter(adapter);

                        CommonUtils.hideLoading();
                        //int a=5;
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });
    }

    public String emailDecodeJWT(String token){
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

        return decodedToken.name;
    }

    void onClickDeleteCategory(CategoryItemDTO categoryItemDTO){
        ApplicationNetwork
                .getInstance()
                .getCategoriesJsonApi()
                .delete(String.valueOf(categoryItemDTO.getId()))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // handle successful response
                            int position = adapter.findCategoryPositionById(categoryItemDTO.getId());
                            if (position != -1) {
                                adapter.deleteCategory(position);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            // handle unsuccessful response
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // handle failure
                    }
                });

        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }



    void onClickEditCategory(CategoryItemDTO categoryItemDTO){
        Intent intent;
        intent=new Intent(MainActivity.this, CategoryEditActivity.class);
        intent.putExtra("CATEGORY_ITEM", categoryItemDTO);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
    }
}