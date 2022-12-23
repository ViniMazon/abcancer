package com.example.tcc_abcancer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.SucessoActivity;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EdtPostAdapter extends RecyclerView.Adapter<EdtPostAdapter.ImageViewHolder> {

    private Context mContext;
    private List<PostagemModel> postagemModels;

    FirebaseAuth auth;
    DatabaseReference databaseReference;

    public EdtPostAdapter (Context context, List<PostagemModel> modelList){

        mContext = context;
        postagemModels = modelList;

    }

    @NonNull
    @NotNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_post, parent, false);
        return new ImageViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageViewHolder holder, int position) {
        PostagemModel model = postagemModels.get(position);
        Picasso.get().load(model.getImagem()).into(holder.imgEx);
        auth = FirebaseAuth.getInstance();

        holder.imgLixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Post").child(auth.getUid()).child("imagens").child(model.getKey()).child("imagem");


                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                        if(ref == null){
                            Toast.makeText(mContext, "delete: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            //FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Post/");

                            //StorageReference storageReference = firebaseStorage.getReferenceFromUrl(model.getStorageKey());
                            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    int pos = postagemModels.indexOf(model);
                                    postagemModels.remove(pos);
                                    notifyItemChanged(pos);
                                    Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {

                                    Toast.makeText(mContext, "Excluido com Sucesso!", Toast.LENGTH_LONG).show();

                                }
                            });
                        }
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return postagemModels.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageButton imgLixo;
        public ImageView imgEx;


        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgLixo = itemView.findViewById(R.id.imageButton2);
            imgEx = itemView.findViewById(R.id.imageDec);

        }
    }

}
