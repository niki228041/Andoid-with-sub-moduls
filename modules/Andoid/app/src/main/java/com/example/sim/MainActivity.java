package com.example.sim;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.category.CategoriesAdapter;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.service.CategoryNetwork;
import com.example.sim.utils.CommonUtils;

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
        CategoryNetwork
                .getInstance()
                .getJsonApi()
                .list()
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

    void onClickDeleteCategory(CategoryItemDTO categoryItemDTO){
        CategoryNetwork
                .getInstance()
                .getJsonApi()
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
        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
    }
}