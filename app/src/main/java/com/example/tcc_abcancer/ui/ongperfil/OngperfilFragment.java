package com.example.tcc_abcancer.ui.ongperfil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tcc_abcancer.EditarOngActivity;
import com.example.tcc_abcancer.EditarPostagemActivity;
import com.example.tcc_abcancer.MainActivity;
import com.example.tcc_abcancer.SobreActivity;
import com.example.tcc_abcancer.TermosActivity;
import com.example.tcc_abcancer.databinding.FragmentOngperfilBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class OngperfilFragment extends Fragment {

    private OngperfilViewModel ongperfilViewModel;
    private FragmentOngperfilBinding binding;
    AlertDialog.Builder alert;
    FirebaseAuth auth;
    DatabaseReference reference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ongperfilViewModel =
                new ViewModelProvider(this).get(OngperfilViewModel.class);

        alert = new AlertDialog.Builder(this.getActivity());

        binding = FragmentOngperfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("ONGS").child(auth.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nome = snapshot.child("NomeONG").getValue(String.class);
                binding.txtNomeM.setText(nome);
                String logo = snapshot.child("Logo").getValue(String.class);
                Picasso.get().load(logo).into(binding.imgPostOngPP);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

/*
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("ONGS");
        reference.child("userid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nome = snapshot.child("NomeONG").getValue(String.class);
                binding.txtNomeM.setText(nome);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

 */
/*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String nome = snapshot.child("NomeONG").getValue(String.class);
                binding.txtNomeM.setText(nome);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


 */


        binding.btnTermOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), TermosActivity.class);
                startActivity(in);
            }
        });



        binding.btnEdtOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), EditarOngActivity.class);
                startActivity(in);
            }
        });

        binding.btnPostOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), EditarPostagemActivity.class);
                startActivity(in);
            }
        });

        binding.btnSobreOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), SobreActivity.class);
                startActivity(in);
            }
        });

        binding.btnSairOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
            }
        });

        binding.btnExcluirOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.setTitle("Deletar sua conta")
                        .setMessage("Deseja deletar a sua conta?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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