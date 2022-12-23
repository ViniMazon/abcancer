package com.example.tcc_abcancer.ui.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;

import org.jetbrains.annotations.NotNull;

public class NovasOngs extends RecyclerView.ViewHolder {

    TextView name;

    public NovasOngs(@NonNull @NotNull View itemView) {
        super(itemView);

        name= itemView.findViewById(R.id.name);
    }
}