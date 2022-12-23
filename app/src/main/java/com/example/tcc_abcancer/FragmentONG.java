package com.example.tcc_abcancer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tcc_abcancer.ui.model.OngsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class FragmentONG extends Fragment {

    private View view;
    private CircleImageView imgLogo;
    private Button btnCad;
    private EditText NomeONG, EmailOng, CnpjOng, TipoOng, TelefoneOng, CelularOng, SenhaOng, ConfSenhaOng;
    private Spinner UfOng, CidadeOng;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef, post;
    private FirebaseStorage mStorage;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    private static final int Gallery_Code=1;
    Uri imageUrl=null;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_ong, container, false);

        imgLogo = view.findViewById(R.id.imgHome);
        btnCad = view.findViewById(R.id.btnCadONG);
        NomeONG = view.findViewById(R.id.edtNomeONG);
        EmailOng = view.findViewById(R.id.edtEmailONG);
        CnpjOng = view.findViewById(R.id.edtCNPJONG);
        TipoOng = view.findViewById(R.id.edtTipoONG);
        TelefoneOng = view.findViewById(R.id.edtTelONG);
        CelularOng = view.findViewById(R.id.edtCelONG);
        SenhaOng = view.findViewById(R.id.edtSenhaONG);
        ConfSenhaOng = view.findViewById(R.id.edtConfSenhaONG);
        UfOng = view.findViewById(R.id.spUF);
        CidadeOng = view.findViewById(R.id.spCidade);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // String userid = user.getUid();

        mDatabase=FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("ONGS");
        //post = FirebaseDatabase.getInstance().getReference().child("Post").child(firebaseAuth.getUid());
        mStorage=FirebaseStorage.getInstance();
        progressDialog= new ProgressDialog(this.getActivity());



        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);


            }
        });


                firebaseAuth = FirebaseAuth.getInstance();

                btnCad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //startActivity(new Intent(getActivity(),SucessoActivity.class));



                        OngsModel ongsModel = new OngsModel();

                        String nome=NomeONG.getText().toString().trim();
                        String email=EmailOng.getText().toString().trim();
                        String cnpj=CnpjOng.getText().toString().trim();
                        String tipo=TipoOng.getText().toString().trim();
                        String tel=TelefoneOng.getText().toString().trim();
                        String cel=CelularOng.getText().toString().trim();
                        String senha=SenhaOng.getText().toString().trim();
                        String confsenha=ConfSenhaOng.getText().toString().trim();
                        String uf=UfOng.getSelectedItem().toString().trim();
                        String cd=CidadeOng.getSelectedItem().toString().trim();

                 if (!(nome.isEmpty() && email.isEmpty() && cnpj.isEmpty() && tipo.isEmpty() && tel.isEmpty() &&
                     cel.isEmpty() && senha.isEmpty() && confsenha.isEmpty() && uf.isEmpty() && cd.isEmpty() && imageUrl!=null
                 ))
                {

                progressDialog.setTitle("Carregando");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email,senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        StorageReference filepath = mStorage.getReference().child("image").child(imageUrl.getLastPathSegment());
                        filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                        String t =task.getResult().toString();

                                        DatabaseReference newPost=mRef.child(firebaseAuth.getUid());

                                        newPost.child("NomeONG").setValue(nome);
                                        newPost.child("Email").setValue(email);
                                        newPost.child("Cnpj").setValue(cnpj);
                                        newPost.child("Tipo").setValue(tipo);
                                        newPost.child("Telefone").setValue(tel);
                                        newPost.child("Celular").setValue(cel);
                                        newPost.child("Senha").setValue(senha);
                                        newPost.child("UF").setValue(uf);
                                        newPost.child("Cidade").setValue(cd);
                                        newPost.child("pessoa").setValue("Ong");
                                        newPost.child("Logo").setValue(task.getResult().toString());
                                        //post.child("Logo").setValue(task.getResult().toString());
                                        //newPost.child("userid").setValue(userid);

                                        startActivity(new Intent(getActivity(),SucessoActivity.class));

                                    }
                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });



                                        }
                                    });
                                }
                            });

                        }



                    }
                });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Code && resultCode==RESULT_OK) {

            imageUrl=data.getData();

            imgLogo.setImageURI(imageUrl);


        }



    }
}







