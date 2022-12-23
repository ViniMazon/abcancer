package com.example.tcc_abcancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SucessoActivity extends AppCompatActivity {

    private Button btnsucesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        btnsucesso = findViewById(R.id.btnSucesso);

        btnsucesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SucessoActivity.this, CriarPostActivity.class);
                startActivity(intent);

            }
        });

    }


}