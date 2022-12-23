package com.example.tcc_abcancer.ui.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;

import org.jetbrains.annotations.NotNull;

public class OngModel extends RecyclerView.ViewHolder {

    TextView teste;

    public OngModel(@NonNull @NotNull View itemView) {
        super(itemView);

        teste=itemView.findViewById(R.id.txtTextoImportante);
    }



}