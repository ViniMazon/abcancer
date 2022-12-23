package com.example.tcc_abcancer;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imgPost;
    View v;

    public ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        imgPost = itemView.findViewById(R.id.imageFoto);
        v=itemView;
    }
}
