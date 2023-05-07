package com.example.sim.network;

import com.example.sim.dto.category.CategoryCreateDTO;
import com.example.sim.dto.category.CategoryItemDTO;
import com.example.sim.dto.category.CategoryUpdateDTO;
import com.example.sim.dto.user.LoginDTO;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriesApi {
//    @GET("/api/categories/list")
//    public Call<List<CategoryItemDTO>> list();

    @GET("/api/categories/list")
    public Call<List<CategoryItemDTO>> list();

    @DELETE("api/Categories/{id}")
    public Call<Void> delete(@Path("id") String id);

    @POST("/api/categories/create")
    public Call<Void> create(@Body CategoryCreateDTO model);

    @PUT("/api/categories/update")
    public Call<Void> update( @Body CategoryUpdateDTO model);

}
