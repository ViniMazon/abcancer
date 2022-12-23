package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.HashMap;

public class EditarCampanha extends AppCompatActivity {

    private ImageView img;
    private EditText edtDesc, edtLink, edtNome;
    private Button btnEditar;
    private ImageButton btnSair, btndelete;
    DatabaseReference ref, reference;
    FirebaseAuth firebaseAuth;
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageReference;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_campanha);

        alert = new AlertDialog.Builder(EditarCampanha.this);

        btndelete = findViewById(R.id.imgbtnDelete);
        edtNome = findViewById(R.id.edtCampNomeEdt);
        btnSair = findViewById(R.id.btnVoltarCampEdt);
        String key = getIntent().getStringExtra("key");
        firebaseAuth = FirebaseAuth.getInstance();
        img = findViewById(R.id.imgEdtCamp);
        edtDesc = findViewById(R.id.edtCampEdt);
        edtLink = findViewById(R.id.edtLinkCampEdt);
        btnEditar = findViewById(R.id.btnEditarCamp);
        ref = FirebaseDatabase.getInstance().getReference().child("Campanha").child(firebaseAuth.getUid()).child(key);
        reference = FirebaseDatabase.getInstance().getReference().child("Campanhas").child(firebaseAuth.getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("campanha");

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setTitle("Deletar a Campanha")
                        .setMessage("Deseja deletar essa Campanha?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).setNegativeButton("Cancelar", null).create().show();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);
            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String link = snapshot.child("link").getValue().toString();
                String image = snapshot.child("foto").getValue().toString();
                String desc = snapshot.child("desc").getValue().toString();
                String nome = snapshot.child("nome").getValue().toString();

                Picasso.get().load(image).into(img);

                edtLink.setText(link);
                edtDesc.setText(desc);
                edtNome.setText(nome);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImage();
                String link = edtLink.getText().toString().trim();
                String desc = edtDesc.getText().toString().trim();
                String nome = edtNome.getText().toString().trim();

                updateData(link, desc, nome);

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Code && resultCode == RESULT_OK) {

            imageUrl = data.getData();

            img.setImageURI(imageUrl);

        }else{
            Toast.makeText(this, "Erro, tente de novo", Toast.LENGTH_SHORT).show();
        }

    }


    private void uploadImage() {
        if(imageUrl != null){
            final StorageReference fileRef = storageReference.child(firebaseAuth.getCurrentUser().getUid());

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

                        ref.child("foto").setValue(myUri);
                        reference.child("foto").setValue(myUri);
                        ref.updateChildren(userMap);
                        reference.updateChildren(userMap);


                    }


                }
            });


        }
    }

    private void updateData(String link, String desc, String nome) {

        HashMap user = new HashMap();
        user.put("link", link);
        user.put("desc", desc);
        user.put("nome", nome);

        reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });

        ref.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {

                Toast.makeText(EditarCampanha.this, "Dados alterados com sucesso", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(EditarCampanha.this, "Falhou", Toast.LENGTH_SHORT).show();
            }
        });
    }
}