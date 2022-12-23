package com.example.tcc_abcancer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.ui.model.EditarModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EditarAdapter extends RecyclerView.Adapter<EditarAdapter.MyViewHolder> {

    private Context context;
    private List<EditarModel> fotosEdt;

    public EditarAdapter(Context context, List<EditarModel> fotosEdt) {
        this.context = context;
        this.fotosEdt = fotosEdt;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTeste2;
        ImageView imageFoto;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

           // txtTeste2 = itemView.findViewById(R.id.txtTeste2);
            imageFoto = itemView.findViewById(R.id.imageDec);


        }
    }


    @NonNull
    @NotNull
    @Override
    public EditarAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_post,parent,false);



        return new EditarAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        EditarModel editarModel = fotosEdt.get(position);

        holder.txtTeste2.setText (editarModel.getTeste());
        holder.imageFoto.setImageResource(editarModel.getImage());

    }

    @Override
    public int getItemCount() {
        return fotosEdt.size();
    }

}

