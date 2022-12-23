package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private ImageButton btnVoltar;
    private Button btnEsqueci, btnCadSe, btnLogin;
    private EditText edtEmail, edtSenha;
    private FirebaseAuth firebaseAuth;
    DatabaseReference loginDB;
    DatabaseReference loginDB1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(LoginActivity.this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEsqueci = findViewById(R.id.btnEsqueci);
        btnCadSe = findViewById(R.id.btnCadSe);
        btnLogin = findViewById(R.id.btnLoginL);
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtSenha = findViewById(R.id.edtSenhaLogin);

        btnEsqueci.setPaintFlags(btnEsqueci.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnCadSe.setPaintFlags(btnCadSe.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EsqueciActivity.class);
                startActivity(intent);
            }
        });

        btnCadSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract data // validate

                if (edtEmail.getText().toString().isEmpty()) {
                    edtEmail.setError("E-mail está vazio");
                    return;

                }
                if (edtSenha.getText().toString().isEmpty()) {
                    edtSenha.setError("Senha está vazia");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String salve = edtEmail.getText().toString().trim();
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = currentUser.getUid();

                            loginDB = FirebaseDatabase.getInstance().getReference().child("Users").child(RegisteredUserID).child("pessoa");
                            loginDB1 = FirebaseDatabase.getInstance().getReference().child("ONGS").child(RegisteredUserID);

                            FirebaseUser currentUser1 = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID1 = currentUser1.getUid();


                            loginDB.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    //String va = snapshot.getValue(String.class);
                                    String snapshotValue = snapshot.getValue(String.class);
                                    //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    // Toast.makeText(signinActivity.this, value, Toast.LENGTH_SHORT).show();
                                    if (snapshotValue == null) {
                                        //String jason = (String) snapshot.getValue();
                                        //Toast.makeText(signinActivity.this, jason, Toast.LENGTH_SHORT).show();
                                        progressDialog.setTitle("Carregando");
                                        progressDialog.show();


                                        startActivity(new Intent(LoginActivity.this, OngActivity.class));
                                        finish();
                                    } else {
                                        progressDialog.setTitle("Carregando");
                                        progressDialog.show();
                                        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                                        finish();
                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

/*
                            loginDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                    String userType1 = snapshot.getValue(String.class);

                                    if(userType1.equals("1")){

                                        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                                        startActivity(intent);
                                    }else if(userType1 == null){

                                        Intent intent = new Intent(LoginActivity.this, OngActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

 */
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });


/*
                firebaseAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        final String user = edtEmail.getText().toString().trim();

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

                        Query checkUser = databaseReference.orderByChild("email").equalTo(user);

                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                                String username1 = String.valueOf(snapshot.child("email").getValue());

                                if(username1.equals(user)) {
                                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                                    startActivity(intent);
                                }else {
                                    Intent intent1 = new Intent(LoginActivity.this, OngActivity.class);
                                    startActivity(intent1);
                                }


                                String user123 = snapshot.child(user).child("email").getValue(String.class);

                                if(user123.equals(user)) {
                                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                                    startActivity(intent);
                                }else{
                                    Intent intent1 = new Intent(LoginActivity.this, OngActivity.class);
                                    startActivity(intent1);
                                }

                            }



                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

*/
            }
        });

    }
}


/*
    private void userLogin () {

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                            //Toast.makeText(signinActivity.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(signinActivity.this, "Could not login, password or email wrong , bullcraps", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {

        //String username = usernameFromEmail(user.getEmail())
        if (user != null) {
            //Toast.makeText(signinActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
            loginDB = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("type");
            loginDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // Toast.makeText(signinActivity.this, value, Toast.LENGTH_SHORT).show();
                    if(Integer.parseInt(value) == 1) {
                        //String jason = (String) snapshot.getValue();
                        //Toast.makeText(signinActivity.this, jason, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, OngActivity.class));

                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    }
 */


