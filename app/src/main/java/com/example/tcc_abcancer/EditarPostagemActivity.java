package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tcc_abcancer.ui.adapter.EditarAdapter;
import com.example.tcc_abcancer.ui.adapter.EdtPostAdapter;
import com.example.tcc_abcancer.ui.adapter.FotosAdapter;
import com.example.tcc_abcancer.ui.adapter.PostagemAdapter;
import com.example.tcc_abcancer.ui.model.EditarModel;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.example.tcc_abcancer.ui.model.UserModel;
import com.example.tcc_abcancer.ui.ongperfil.OngperfilFragment;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditarPostagemActivity extends AppCompatActivity {

    private EditText edtNome, edtSobre, edtTipo, edtContato, edtEmail, edtLocal, edtHorario, edtNeces;
    private Button btnConf, btnAdd;
    private ImageButton btnSair, btnDel;
    private ImageView fb, insta, tt;
    private ArrayList<PostagemModel> list;
    private PostagemAdapter adapter;
    private TextView alert;
    private RecyclerView mRecyclerView;
    private EdtPostAdapter mAdapter;
    private List<PostagemModel> models;
    private FirebaseStorage mStorage;
    private Uri ImageUri;
    private int upload = 0;
    private static final int Gallery_Code = 1;
    RecyclerView recyclerView;
    List<EditarModel> fotosEdt;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    FirebaseAuth auth;
    DatabaseReference reference;

    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_postagem);

        alert = findViewById(R.id.txtAlertPost);
        btnSair = findViewById(R.id.btnVoltarPostOng);
        btnConf = findViewById(R.id.btnEdtConfPost);
        edtNome = findViewById(R.id.edtEdtPostNome);
        edtSobre = findViewById(R.id.edtEdtPostSobre);
        edtTipo = findViewById(R.id.edtPostTipoC);
        edtContato = findViewById(R.id.edtPostCont);
        edtEmail = findViewById(R.id.edtPostEmail);
        edtLocal = findViewById(R.id.edtPostLocal);
        edtHorario = findViewById(R.id.edtPostHA);
        edtNeces = findViewById(R.id.edtEdtPostNeces);
        fb = findViewById(R.id.imageEdtFace);
        insta = findViewById(R.id.imageEdtInsta);
        tt = findViewById(R.id.imageEdtTwi);
        btnAdd = findViewById(R.id.btnAddEdtPostUhu);


        auth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Post").child(auth.getUid());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, Gallery_Code);

            }
        });


        mRecyclerView = findViewById(R.id.recyclerViewPost);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(EditarPostagemActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        models = new ArrayList<>();

        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("Post");

        reference.child("imagens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostagemModel postagemModel = dataSnapshot.getValue(PostagemModel.class);
                    postagemModel.setKey(dataSnapshot.getKey());

                    models.add(postagemModel);

                }

                mAdapter = new EdtPostAdapter(EditarPostagemActivity.this, models);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nome = snapshot.child("nomeOng").getValue(String.class);
                edtNome.setText(nome);

                String sobre = snapshot.child("sobre").getValue(String.class);
                edtSobre.setText(sobre);

                String tipo = snapshot.child("tipo").getValue(String.class);
                edtTipo.setText(tipo);

                String contato = snapshot.child("contato").getValue(String.class);
                edtContato.setText(contato);

                String email = snapshot.child("email").getValue(String.class);
                edtEmail.setText(email);

                String local = snapshot.child("local").getValue(String.class);
                edtLocal.setText(local);

                String horario = snapshot.child("horario").getValue(String.class);
                edtHorario.setText(horario);

                String necs = snapshot.child("necessidade").getValue(String.class);
                edtNeces.setText(necs);

                String face = snapshot.child("Facebook").getValue(String.class);
                fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(EditarPostagemActivity.this);
                        alert.setTitle("Deseja alterar o link do seu Facebook?");
                        final EditText edtFB = new EditText(EditarPostagemActivity.this);
                        edtFB.setInputType(InputType.TYPE_CLASS_TEXT);
                        alert.setView(edtFB);
                        edtFB.setText(face);
                        alert.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String fbLink=edtFB.getText().toString().trim();

                                updateLink1(fbLink);

                                //Toast.makeText(EditarPostagemActivity.this, "O link é " +fbLink,Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alert.show();
                    }
                });

                String instagram = snapshot.child("Instagram").getValue(String.class);

                insta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(EditarPostagemActivity.this);
                        alert.setTitle("Deseja alterar o link do seu Instagram?");
                        final EditText edtInsta = new EditText(EditarPostagemActivity.this);
                        edtInsta.setInputType(InputType.TYPE_CLASS_TEXT);
                        alert.setView(edtInsta);
                        edtInsta.setText(instagram);
                        alert.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String instaLink=edtInsta.getText().toString().trim();

                                updateLink2(instaLink);

                                //Toast.makeText(EditarPostagemActivity.this, "O link é " +fbLink,Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alert.show();
                    }
                });

                String twitter = snapshot.child("Twitter").getValue(String.class);

                tt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(EditarPostagemActivity.this);
                        alert.setTitle("Deseja alterar o link do seu Twitter?");
                        final EditText edtTT = new EditText(EditarPostagemActivity.this);
                        edtTT.setInputType(InputType.TYPE_CLASS_TEXT);
                        alert.setView(edtTT);
                        edtTT.setText(twitter);
                        alert.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String ttLink=edtTT.getText().toString().trim();

                                updateLink3(ttLink);

                                //Toast.makeText(EditarPostagemActivity.this, "O link é " +fbLink,Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alert.show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
/*
        recyclerView = findViewById(R.id.recyclerViewPost);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new PostagemAdapter(this, list);
        recyclerView.setAdapter(adapter);

        reference.child("imagens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostagemModel model = dataSnapshot.getValue(PostagemModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

 */


        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtNome.getText().toString();
                String about = edtSobre.getText().toString();
                String type = edtTipo.getText().toString();
                String contact = edtContato.getText().toString();
                String emaill = edtEmail.getText().toString();
                String place = edtLocal.getText().toString();
                String hour = edtHorario.getText().toString();
                String ness = edtNeces.getText().toString();
                updataData(name, about, type, contact, emaill, place, hour, ness);

                StorageReference imageFolder = FirebaseStorage.getInstance().getReference("Post");

                for(upload = 0; upload < ImageList.size(); upload++){

                    Uri IndividualImage = ImageList.get(upload);
                    StorageReference ImageName = imageFolder.child("Image" + IndividualImage.getLastPathSegment());

                    ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String url = String.valueOf(uri);

                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("imagem",url);
                                    reference.child("imagens").push().setValue(hashMap);


                                }
                            });

                        }
                    });

                }



            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void updataData(String name, String about, String type, String contact, String emaill, String place, String hour, String ness) {

        HashMap user = new HashMap();
        user.put("nomeOng", name);
        user.put("sobre", about);
        user.put("tipo", type);
        user.put("contato", contact);
        user.put("email", emaill);
        user.put("local", place);
        user.put("horario", hour);
        user.put("necessidade", ness);

        reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {

                if(task.isSuccessful()){

                    edtNome.setText("");
                    edtSobre.setText("");
                    edtTipo.setText("");
                    edtContato.setText("");
                    edtEmail.setText("");
                    edtLocal.setText("");
                    edtHorario.setText("");
                    edtNeces.setText("");

                    Toast.makeText(EditarPostagemActivity.this, "Dados alterados com sucesso", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(EditarPostagemActivity.this, OngperfilFragment.class));


                }else {
                    Toast.makeText(EditarPostagemActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateLink1(String fbLink){

        HashMap user = new HashMap();
        user.put("Facebook", fbLink);


        reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {

                if(task.isSuccessful()){

                    Toast.makeText(EditarPostagemActivity.this, "Link alterado com sucesso", Toast.LENGTH_LONG).show();
                    //Toast.makeText(EditarPostagemActivity.this, "Dados alterados com sucesso", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(EditarPostagemActivity.this, OngperfilFragment.class));


                }else {
                    Toast.makeText(EditarPostagemActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateLink2(String instaLink){

        HashMap user = new HashMap();
        user.put("Instagram", instaLink);


        reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {

                if(task.isSuccessful()){

                    Toast.makeText(EditarPostagemActivity.this, "Link alterado com sucesso", Toast.LENGTH_LONG).show();
                    //Toast.makeText(EditarPostagemActivity.this, "Dados alterados com sucesso", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(EditarPostagemActivity.this, OngperfilFragment.class));


                }else {
                    Toast.makeText(EditarPostagemActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateLink3(String ttLink){

        HashMap user = new HashMap();
        user.put("Twitter", ttLink);


        reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {

                if(task.isSuccessful()){

                    Toast.makeText(EditarPostagemActivity.this, "Link alterado com sucesso", Toast.LENGTH_LONG).show();
                    //Toast.makeText(EditarPostagemActivity.this, "Dados alterados com sucesso", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(EditarPostagemActivity.this, OngperfilFragment.class));


                }else {
                    Toast.makeText(EditarPostagemActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Code){
            if(resultCode == RESULT_OK){
                if(data.getClipData() != null){

                    int countClipData = data.getClipData().getItemCount();

                    int currentImageSelect = 0;

                    while (currentImageSelect < countClipData){

                        ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();

                        ImageList.add(ImageUri);

                        currentImageSelect = currentImageSelect +1;

                    }

                    alert.setVisibility(View.VISIBLE);
                    alert.setText("Você selecionou " + ImageList.size() + " imagens");

                    Toast.makeText(this, "Você selecionou " + ImageList.size() + " imagens", Toast.LENGTH_SHORT).show();

                }else

                {

                    Toast.makeText(this, "Selecione mais imagens", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

}