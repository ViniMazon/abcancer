package com.example.tcc_abcancer;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class CriarPostActivity extends AppCompatActivity {


    private EditText edtNome, edtSobre, edtTipo, edtContato, edtEmail, edtLocal, edtHA, edtNec;
    private ImageView img1, img2, img3, img4, imgFB, imgTT, imgIG;
    private Button btnConf, btnAdd;
    private TextView alert;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private Uri ImageUri;
    private int upload = 0;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    ArrayList<Uri> ImageList = new ArrayList<Uri>();

    private static final int Gallery_Code = 1;
    Uri imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_post);

        alert = findViewById(R.id.alert);
        btnAdd = findViewById(R.id.btnAddP);
        edtNome = findViewById(R.id.edtNomeP);
        edtSobre = findViewById(R.id.edtSobreONGP);
        edtTipo = findViewById(R.id.edtTipoCP);
        edtContato = findViewById(R.id.edtContatoP);
        edtEmail = findViewById(R.id.edtEmailP);
        edtLocal = findViewById(R.id.edtLocalP);
        edtHA = findViewById(R.id.edtHoraP);
        edtNec = findViewById(R.id.edtNecesP);
        btnConf = findViewById(R.id.btnConfP);
        imgFB = findViewById(R.id.imageFace);
        imgIG = findViewById(R.id.imageInsta);
        imgTT = findViewById(R.id.imageTwi);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Imagem sendo enviada, aguarde...");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Post");
        mStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Post").child(firebaseAuth.getUid());
        //String key = databaseReference.child(firebaseAuth.getUid());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, Gallery_Code);

            }
        });

        imgIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CriarPostActivity.this);
                alert.setTitle("Adicione o link do seu Instagram!");
                final EditText edtIG = new EditText(CriarPostActivity.this);
                edtIG.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setView(edtIG);

                alert.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String igLink=edtIG.getText().toString().trim();
                        databaseReference.child("Instagram").setValue(igLink);
                        Toast.makeText(CriarPostActivity.this, "O link é " +igLink,Toast.LENGTH_SHORT).show();

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


        imgFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CriarPostActivity.this);
                alert.setTitle("Adicione o link do seu Facebook!");
                final EditText edtFB = new EditText(CriarPostActivity.this);
                edtFB.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setView(edtFB);

                alert.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String fbLink=edtFB.getText().toString().trim();
                        databaseReference.child("Facebook").setValue(fbLink);
                        Toast.makeText(CriarPostActivity.this, "O link é " +fbLink,Toast.LENGTH_SHORT).show();

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

        imgTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CriarPostActivity.this);
                alert.setTitle("Adicione o link do seu Facebook!");
                final EditText edtTT = new EditText(CriarPostActivity.this);
                edtTT.setInputType(InputType.TYPE_CLASS_TEXT);
                alert.setView(edtTT);

                alert.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String ttLink =edtTT.getText().toString().trim();
                        databaseReference.child("Twitter").setValue(ttLink);
                        Toast.makeText(CriarPostActivity.this, "O link é " +ttLink,Toast.LENGTH_SHORT).show();

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


        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PostagemModel models = new PostagemModel();

                String nome = edtNome.getText().toString().trim();
                String sobre = edtSobre.getText().toString().trim();
                String tipo = edtTipo.getText().toString().trim();
                String contato = edtContato.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String local = edtLocal.getText().toString().trim();
                String hor = edtHA.getText().toString().trim();
                String nec = edtNec.getText().toString().trim();

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



                                    // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");

                                    //  DatabaseReference newPostI = databaseReference.child("imagens").push();

                                    //DatabaseReference newPost = mRef.child("imagens").push();
                                    //DatabaseReference newPost = mRef.push();

                                    //newPost.child("imagens").child("teste").setValue(url);


                                    HashMap<String,String> hashMap = new HashMap<String, String>();
                                    hashMap.put("imagem",url);
                                    //models.set
                                    //String teste = String.valueOf(hashMap);

                                    databaseReference.child("id").setValue(firebaseAuth.getUid());
                                    //models.setImagens(uri.get);
                                    //PostagemModel models = new PostagemModel();
                                    //models.setStorageKey(ImageName.getName());
                                    databaseReference.child("imagens").push().setValue(hashMap);
                                    //models.setUri(uri.toString());
                                    //models.setImagens(hashMap.toString());
                                    databaseReference.child("unique").setValue(url);
                                    //models.setUnique();
                                    databaseReference.child("nomeOng").setValue(nome);
                                    databaseReference.child("sobre").setValue(sobre);
                                    databaseReference.child("tipo").setValue(tipo);
                                    databaseReference.child("contato").setValue(contato);
                                    databaseReference.child("email").setValue(email);
                                    databaseReference.child("local").setValue(local);
                                    databaseReference.child("horario").setValue(hor);
                                    databaseReference.child("necessidade").setValue(nec);


                                    startActivity(new Intent(CriarPostActivity.this, OngActivity.class));
                                    //databaseReference.child("pessoa").setValue("");
                                    progressDialog.dismiss();
                                    alert.setText("Image Uploaded Successfully");



                                }
                            });

                        }
                    });

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

    private void StoreLink(String url) {

        //  HashMap<String,String> hashMap = new HashMap<>();

        //databaseReference.child("imagem").push().setValue(hashMap);






        progressDialog.dismiss();
        alert.setText("Image Uploaded Successfully");

    }
}




