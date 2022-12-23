package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserCampanhaActivity extends AppCompatActivity {

    private TextView txtNome, txtNomeOng, txtLink, txtDesc;
    private CircleImageView logo;
    private ImageView foto;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_campanha);

        txtNome = findViewById(R.id.txtNomeCampUser);
        txtNomeOng = findViewById(R.id.TXTONGONME);
        txtLink = findViewById(R.id.TXTlinkdacamp);
        txtDesc = findViewById(R.id.txtdescdacampuser);
        logo = findViewById(R.id.IMAGELOGOUSER);
        foto = findViewById(R.id.imagemdaCamp);
        String key = getIntent().getStringExtra("key");
        reference = FirebaseDatabase.getInstance().getReference().child("Campanhas").child(key);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nameOng =snapshot.child("nomeOng").getValue().toString();
                txtNomeOng.setText(nameOng);

                String nome =snapshot.child("nome").getValue().toString();
                txtNome.setText(nome);

                String link =snapshot.child("link").getValue().toString();
                txtLink.setText(link);

                String desc =snapshot.child("desc").getValue().toString();
                txtDesc.setText(desc);

                String logos =snapshot.child("Logo").getValue().toString();
                Picasso.get().load(logos).into(logo);

                String fotoc = snapshot.child("foto").getValue().toString();
                Picasso.get().load(fotoc).into(foto);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}