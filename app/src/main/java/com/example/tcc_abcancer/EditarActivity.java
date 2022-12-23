package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class EditarActivity extends AppCompatActivity {
    private TextView txtNome;
    private EditText edtEmail, edtSenha, edtCPF;
    private Button btnA;
    FirebaseAuth auth;
    String email, senha;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        txtNome = findViewById(R.id.txtNomeE);
        edtEmail = findViewById(R.id.edtEmailE);
        edtSenha = findViewById(R.id.edtSenhaE);
        edtCPF = findViewById(R.id.edtCPFE);
       // btnA = findViewById(R.id.btnConfE);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nome = snapshot.child("nome").getValue(String.class);
                String cpf  = snapshot.child("cpf").getValue(String.class);
                String email  = snapshot.child("email").getValue(String.class);
                String senha  = snapshot.child("senha").getValue(String.class);

                txtNome.setText(nome);
                edtEmail.setText(email);
                edtCPF.setText(cpf);
                edtSenha.setText(senha);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        })
        ;}}
