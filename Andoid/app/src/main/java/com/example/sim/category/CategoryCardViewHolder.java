package com.example.sim.category;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sim.R;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private ImageView categoryImage;
    private TextView categoryName;
    public Button buttonEdit;
    public Button buttonDelete;

    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName=itemView.findViewById(R.id.categoryName);
        categoryImage=itemView.findViewById(R.id.categoryImage);
        buttonEdit=itemView.findViewById(R.id.buttonEdit);
        buttonDelete=itemView.findViewById(R.id.buttonDelete);
    }

    public ImageView getCategoryImage() {
        return categoryImage;
    }

    public TextView getCategoryName() {
        return categoryName;
    }
}
