package com.example.tcc_abcancer.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView txtNomeOng, txtDist;
    public CircleImageView imgOng, imgPost;
    public View view;

    public MyViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        txtNomeOng=itemView.findViewById(R.id.textView41);
        imgOng=itemView.findViewById(R.id.imgHome);
        txtDist= itemView.findViewById(R.id.TXToNGDISTLOCAL);
        //imgPost=itemView.findViewById(R.id.imageFoto);
        view=itemView;


    }
}
