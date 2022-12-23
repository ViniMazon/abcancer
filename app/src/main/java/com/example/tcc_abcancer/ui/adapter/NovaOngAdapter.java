package com.example.tcc_abcancer.ui.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.ui.model.Model;
import com.example.tcc_abcancer.ui.model.NovasOngs;
import com.example.tcc_abcancer.ui.model.OngsModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NovaOngAdapter extends RecyclerView.Adapter<NovaOngAdapter.ViewHolder> {

    Context context;
    List<OngsModel> modelList;

    public NovaOngAdapter(Context context, List<OngsModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        OngsModel ongsModel=modelList.get(position);
        holder.nome.setText(ongsModel.getNomeONG());

        String imageUri = null;
        imageUri=ongsModel.getLogo();
        Picasso.get().load(imageUri).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

    CircleImageView imageView;
    TextView nome;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageHorizontal);
            nome=itemView.findViewById(R.id.name);

        }
    }


}