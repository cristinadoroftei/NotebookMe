package com.example.noteme;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    private ConstraintLayout itemLayout;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        itemLayout = itemView.findViewById(R.id.parent_layout);
    }

    public ConstraintLayout getItemLayout() {
        return itemLayout;
    }
}
