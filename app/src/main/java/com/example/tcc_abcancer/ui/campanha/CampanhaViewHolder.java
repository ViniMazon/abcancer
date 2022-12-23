package com.example.tcc_abcancer.ui.campanha;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;

import org.jetbrains.annotations.NotNull;

public class CampanhaViewHolder extends RecyclerView.ViewHolder {

    TextView txtNomeOng;
    ImageView img;
    View view;

    public CampanhaViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        txtNomeOng = itemView.findViewById(R.id.txtTextoImportante);
        img = itemView.findViewById(R.id.imagemHomeLayout);

        view=itemView;

    }
}
