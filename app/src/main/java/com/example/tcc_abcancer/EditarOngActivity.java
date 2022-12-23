package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.UTFDataFormatException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarOngActivity extends AppCompatActivity {

    private TextView txtNomeOng;
    private EditText edtCnpj, edtEmail, edtTipo, edtTel, edtCel, edtSenha, edtUf, edtCid;
    private CircleImageView imgLogo;
    private Button btnFotoOng, btnConf;
    DatabaseReference reference;
    FirebaseAuth auth;
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private String myUri = "";
    private ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ong);

        //reference = FirebaseDatabase.getInstance().getReference("ONGS");

        txtNomeOng = findViewById(R.id.txtNomeDaOng);
        edtCnpj = findViewById(R.id.edtCPNGOng);
        edtCel = findViewById(R.id.edtCelularOngPO);
        edtEmail = findViewById(R.id.edtEmailOngPO);
        edtSenha = findViewById(R.id.edtSenhaOngPO);
        edtTel = findViewById(R.id.edtTelefoneOngPO);
        edtTipo = findViewById(R.id.edtTipoOngPO);
        edtCid = findViewById(R.id.edtCidPO);
        edtUf = findViewById(R.id.edtUfPO);
        imgLogo = findViewById(R.id.imgPostOngPP);
        btnFotoOng = findViewById(R.id.btnFotoOng);
        btnConf = findViewById(R.id.btnConfEdtOng);
        btnVoltar = findViewById(R.id.btnVoltar3);



        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("ONGS").child(auth.getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("image");

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnFotoOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });

        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadProfileImaga();
                Toast.makeText(EditarOngActivity.this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nome = snapshot.child("NomeONG").getValue().toString();
                String cnpj = snapshot.child("Cnpj").getValue(String.class);
                String email = snapshot.child("Email").getValue(String.class);
                String tipo = snapshot.child("Tipo").getValue(String.class);
                String uf = snapshot.child("UF").getValue(String.class);
                String cidade = snapshot.child("Cidade").getValue(String.class);
                String telefone = snapshot.child("Telefone").getValue(String.class);
                String celular = snapshot.child("Celular").getValue(String.class);
                String senha = snapshot.child("Senha").getValue(String.class);
                String logo = snapshot.child("Logo").getValue().toString();


                txtNomeOng.setText(nome);
                edtCnpj.setText(cnpj);
                edtSenha.setText(senha);
                edtCel.setText(celular);
                edtEmail.setText(email);
                edtTel.setText(telefone);
                edtTipo.setText(tipo);
                edtCid.setText(cidade);
                edtUf.setText(uf);
                Picasso.get().load(logo).into(imgLogo);

//                getUserInfo();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        })
        ;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Code && resultCode == RESULT_OK) {

            imageUrl = data.getData();

            imgLogo.setImageURI(imageUrl);

        }else{
            Toast.makeText(this, "Erro, tente de novo", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadProfileImaga() {

        if(imageUrl != null){
            final StorageReference fileRef = storageReference.child(auth.getCurrentUser().getUid());

            uploadTask = fileRef.putFile(imageUrl);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull @NotNull Task task) throws Exception {
                    if(!task.isSuccessful()){

                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri> () {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {
                    if(task.isSuccessful()) {

                        Uri downloadUrl = (Uri) task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();

                        reference.child("Logo").setValue(myUri);

                        reference.updateChildren(userMap);

                    }


                }
            });


        }


    }

    private void getUserInfo(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}

