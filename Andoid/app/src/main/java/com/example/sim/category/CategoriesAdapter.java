package com.example.sim.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.R;
import com.example.sim.application.HomeApplication;
import com.example.sim.contants.Urls;
import com.example.sim.dto.category.CategoryItemDTO;


import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {

    private List<CategoryItemDTO> categories;
    private final OnItemClickListener editCategory;
    private final OnItemClickListener deleteCategory;

    public CategoriesAdapter(List<CategoryItemDTO> categories, OnItemClickListener editCategory, OnItemClickListener deleteCategory) {
        this.categories = categories;
        this.editCategory = editCategory;
        this.deleteCategory = deleteCategory;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_view, parent, false);
        return new CategoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if(categories!=null && position<categories.size())
        {
            CategoryItemDTO item = categories.get(position);
            holder.getCategoryName().setText(item.getName());
            String url = Urls.BASE+item.getImage();
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    .apply(new RequestOptions().override(600))
                    .into(holder.getCategoryImage());

            holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editCategory.onItemClick(item);
                }
            });

            holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCategory.onItemClick(item);
                }
            });
        }
    }



    public void deleteCategory(int position) {
        categories.remove(position);
    }

    public int findCategoryPositionById(int categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            CategoryItemDTO category = categories.get(i);
            if (category.getId() == categoryId) {
                return i;
            }
        }
        return -1; // category not found
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
