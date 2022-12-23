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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tcc_abcancer.ui.model.CampanhaModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class CriarCampanhaActivity extends AppCompatActivity {

    private EditText edtDesc, edtLink, edtNome;
    private ImageView imgAdd;
    private Button btnCreate;
    private DatabaseReference reference;
    private DatabaseReference references;
    private FirebaseStorage storage;
    private FirebaseAuth firebaseAuth;
    private long maxid;
    private CampanhaModel campanhaModel;

    private static final int Gallery_Code=1;
    Uri imageUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_campanha);

        edtDesc = findViewById(R.id.edtDescCamp);
        edtLink = findViewById(R.id.edtLinkCamp);
        imgAdd = findViewById(R.id.imgAddCamp);
        btnCreate = findViewById(R.id.btnCreateCamp);
        edtNome = findViewById(R.id.edtNomeCamp);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Campanha");
        references = FirebaseDatabase.getInstance().getReference().child("Campanhas");
        storage=FirebaseStorage.getInstance();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());
                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            campanhaModel = new CampanhaModel();

                String nome = edtNome.getText().toString().trim();
                String link = edtLink.getText().toString().trim();
                String desc = edtDesc.getText().toString().trim();

                StorageReference filepath = storage.getReference().child("campanha").child(imageUrl.getLastPathSegment());
                filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {

                                //reference.child("link").setValue(link);
                                //reference.child("desc").setValue(desc);
                                //reference.child("foto").setValue(task.getResult().toString());

                                campanhaModel.setLink(link);
                                campanhaModel.setDesc(desc);
                                campanhaModel.setNome(nome);
                                //campanhaModel.setId(firebaseAuth.getUid());
                                campanhaModel.setFoto(task.getResult().toString());
                                reference.child(firebaseAuth.getUid()).child(String.valueOf(maxid+1)).setValue(campanhaModel);
                                references.child(firebaseAuth.getUid()).setValue(campanhaModel);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(CriarCampanhaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Code && resultCode==RESULT_OK) {

            imageUrl=data.getData();

            imgAdd.setImageURI(imageUrl);


        }

    }
}