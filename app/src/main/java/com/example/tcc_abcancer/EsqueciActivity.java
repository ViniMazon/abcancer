package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class EsqueciActivity extends AppCompatActivity {


    private ImageButton btnBack;
    private EditText edtEnviarEmail;
    private Button btnEnviar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci);

        edtEnviarEmail =findViewById(R.id.edtEnviarEmail);
        btnBack = findViewById(R.id.btnBack);
        btnEnviar = findViewById(R.id.btnEnviar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.sendPasswordResetEmail(edtEnviarEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(EsqueciActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EsqueciActivity.this, "Email incorreto", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}