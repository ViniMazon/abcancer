package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import com.example.tcc_abcancer.ui.adapter.EdtPostAdapter;
import com.example.tcc_abcancer.ui.adapter.FotosAdapter;
import com.example.tcc_abcancer.ui.home.MyViewHolder;
import com.example.tcc_abcancer.ui.model.CampanhaModel;
import com.example.tcc_abcancer.ui.model.FotosModel;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PostagemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //List<PostagemActivity> models;
    ImageSlider slider;
    DatabaseReference reference;
    private TextView txtNomeOng, txtCancer, txtEmail, txtContato, txtLocal, txtHora, txtImportante, edtSobre, edtNec;
    private List<SlideModel> models = new ArrayList<>();
    private FotosAdapter mAdapter;
    private PostagemModel model;
    private FirebaseRecyclerOptions<CampanhaModel> options;
    private FirebaseRecyclerAdapter<CampanhaModel, MyViewHolder> adapter;
    private DatabaseReference ref;
    private ImageView face, teste;
    DatabaseReference getReference;
    private RecyclerView recyclerViewV, recyclerViewPostagem;
    private DatabaseReference ref2;
    private TextView cont, Txtemail, loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);

        teste = findViewById(R.id.imagemHomeLayout);
        txtImportante = findViewById(R.id.txtTextoImportante);
        slider = findViewById(R.id.slider);
        txtNomeOng = findViewById(R.id.txtPostNomeOng);
        edtSobre = findViewById(R.id.txtSobreAongpostagem);
        txtCancer = findViewById(R.id.txtTipoPost);
        txtEmail = findViewById(R.id.txtEmailPost);
        txtContato = findViewById(R.id.txtContPost);
        txtLocal = findViewById(R.id.txtLocPost);
        txtHora = findViewById(R.id.txtAtenPost);
        edtNec = findViewById(R.id.txtSobreDoacaoPost);
        face = findViewById(R.id.imageFace);
        cont = findViewById(R.id.txtContPost);
        Txtemail = findViewById(R.id.txtEmailPost);
        loc = findViewById(R.id.txtLocPost);


        //recyclerView = findViewById(R.id.recyclerViewFotos);
       // recyclerView.setHasFixedSize(true);
        String chave = getIntent().getStringExtra("chave");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Campanhas").child(chave);
        reference = FirebaseDatabase.getInstance().getReference().child("Post").child(chave);
        ref = FirebaseDatabase.getInstance().getReference().child("Post").child(chave).child("imagens");
        //getReference = FirebaseDatabase.getInstance().getReference().child("ONGS");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren()) {
                    models.add(new SlideModel(data.child("imagem").getValue().toString(), ScaleTypes.FIT));
                    slider.setImageList(models, ScaleTypes.FIT);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String name =snapshot.child("nomeOng").getValue().toString();
                txtNomeOng.setText(name);

                String sobre = snapshot.child("sobre").getValue().toString();
                edtSobre.setText(" " +sobre);

                String cancer = snapshot.child("tipo").getValue().toString();
                txtCancer.setText(cancer);

                String email = snapshot.child("email").getValue().toString();
                txtEmail.setText(email);

                String contato = snapshot.child("contato").getValue().toString();
                txtContato.setText(contato);

                String local = snapshot.child("local").getValue().toString();
                txtLocal.setText(local);

                String horario = snapshot.child("horario").getValue().toString();
                txtHora.setText(horario);

                String nec = snapshot.child("necessidade").getValue().toString();
                edtNec.setText(" " +nec);

                loc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                       intent.setData(Uri.parse("geo:0,0?q=" + local));
                        startActivity(intent);
                    }
                });

                //Txtemail.setText(Html.fromHtml("<a href=\"oncoamigo@oncoamigo.org.br\">" +  email));
                //Txtemail.setMovementMethod(LinkMovementMethod.getInstance());


                face.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String facebook = snapshot.child("Facebook").getValue().toString();

                        goToUrl(facebook);

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String teste123 = snapshot.child("nome").getValue().toString();
                txtImportante.setText(teste123);

                String foto = snapshot.child("foto").getValue().toString();
                Picasso.get().load(foto).into(teste);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


/*
        String nome = getIntent().getStringExtra("nomeOng");
        txtNomeOng.setText(nome);

        String sobre = getIntent().getStringExtra("sobre");
        edtSobre.setText(" " +sobre);

 */

        //recyclerView = findViewById(R.id.recyclerViewPost);

        /*
        String chave = getIntent().getStringExtra("chave");

        models = new ArrayList<>();

        reference.child(chave).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    PostagemModel postagemModel = dataSnapshot.getValue(PostagemModel.class);
                    postagemModel.setKey(dataSnapshot.getKey());
                    models.add(postagemModel);



                }

                mAdapter = new FotosAdapter(PostagemActivity.this, models);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();



                }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        /*
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PostagemActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        models = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                //Object object=snapshot.child("imagens").child("imagem").getValue();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    PostagemModel postagemModel = dataSnapshot.getValue(PostagemModel.class);
                    postagemModel.setKey(dataSnapshot.getKey());

                    //models.add(postagemModel);

            }
                mAdapter = new FotosAdapter(PostagemActivity.this, models);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }




            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

         */

        //String


    }

    private void goToUrl(String facebook) {
        Uri uri = Uri.parse(facebook);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}