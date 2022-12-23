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
import com.example.tcc_abcancer.ui.model.FotosModel;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.MyViewHolder> {

    private Context mContext;
    private List<PostagemModel> postagemModels;
    DatabaseReference reference;

    public FotosAdapter(Context context, List<PostagemModel> modelList) {
        mContext = context;
        postagemModels = modelList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_foto,parent,false);



        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        reference = FirebaseDatabase.getInstance().getReference().child("Post");

        PostagemModel model = postagemModels.get(position);
        reference.child(model.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    String imagens = snapshot.child("imagens").getValue().toString();
                    Picasso.get().load(imagens).into(holder.imageFoto);



                }

            }



            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return postagemModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //TextView txtTeste;
        ImageView imageFoto;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

                //txtTeste = itemView.findViewById(R.id.txtTeste);
                imageFoto = itemView.findViewById(R.id.imageFoto);


        }
    }


}

