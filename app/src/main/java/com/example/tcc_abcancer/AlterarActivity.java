package com.example.tcc_abcancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AlterarActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnAlterar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        btnBack = findViewById(R.id.btnBack3);
        btnAlterar = findViewById(R.id.btnAlterarSenha);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AlterarActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}