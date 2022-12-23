package com.example.tcc_abcancer.ui.mais;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tcc_abcancer.EditarActivity;
import com.example.tcc_abcancer.MainActivity;
import com.example.tcc_abcancer.PostagemActivity;
import com.example.tcc_abcancer.SobreActivity;
import com.example.tcc_abcancer.TermosActivity;
import com.example.tcc_abcancer.databinding.FragmentMaisBinding;
import com.example.tcc_abcancer.ui.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.os.Build.VERSION_CODES.R;

public class MaisFragment extends Fragment {

    private MaisViewModel maisViewModel;
    private FragmentMaisBinding binding;
    AlertDialog.Builder alert;
    LayoutInflater inflater;
    FirebaseAuth auth;
    DatabaseReference reference;
    Task<Void> ref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       maisViewModel =
                new ViewModelProvider(this).get(MaisViewModel.class);

       auth = FirebaseAuth.getInstance();

       reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid());

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

               String nome = snapshot.child("nome").getValue(String.class);
               binding.txtNomeM.setText(nome);

           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });


        alert = new AlertDialog.Builder(this.getActivity());
        inflater = this.getLayoutInflater();

        binding = FragmentMaisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnEdtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent in = new Intent(getActivity(), EditarActivity.class);
              startActivity(in);
            }
        });

        binding.btnTermoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), TermosActivity.class);
                startActivity(in);
            }
        });

        binding.btnSobreUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), SobreActivity.class);
                startActivity(in);
            }
        });

        binding.btnSairUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
            }
        });

        binding.btnExcluirUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.setTitle("Deletar sua conta")
                        .setMessage("Deseja deletar a sua conta?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseUser user = auth.getCurrentUser();

                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    
                                    public void onSuccess(Void unused) {

                                        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).removeValue();

                                        Toast.makeText(getActivity(), "Conta deletada", Toast.LENGTH_SHORT).show();
                                        auth.signOut();
                                        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(getActivity(), "Não foi possível excluir", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancelar", null).create().show();


            }
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}