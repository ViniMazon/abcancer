package com.example.tcc_abcancer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.ui.model.OngModel;

import org.jetbrains.annotations.NotNull;

public class OngPrincipalAdapter extends RecyclerView.Adapter<OngModel> {

    Context c;
    String[]ongs2;

    public OngPrincipalAdapter(Context c, String[] ongs2) {
        this.c = c;
        this.ongs2 = ongs2;
    }

    @NonNull
    @NotNull
    @Override
    public OngModel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View c= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item2,parent,false);
        return new OngModel(c);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OngModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ongs2.length;
    }
}