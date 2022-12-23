package com.example.tcc_abcancer;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tcc_abcancer.ui.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.InputMismatchException;


public class FragmentUser extends Fragment {

    private EditText NomeUser, CPFUser, EmailUser, SenhaUser, ConfSenhaUser;
    private Button btnCadUser, btnBack;
    private View view;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        NomeUser = view.findViewById(R.id.edtNomeUser);
        CPFUser = view.findViewById(R.id.edtCpfUser);
        EmailUser = view.findViewById(R.id.edtEmailUser);
        SenhaUser = view.findViewById(R.id.edtSenhaUser);
        ConfSenhaUser = view.findViewById(R.id.edtSenhaConfUser);
        btnCadUser = view.findViewById(R.id.btnCadUser);

        firebaseAuth = FirebaseAuth.getInstance();

        btnCadUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                UserModel userModel = new UserModel();

                userModel.setNome(NomeUser.getText().toString());
                userModel.setCpf(CPFUser.getText().toString());
                userModel.setEmail(EmailUser.getText().toString());
                userModel.setSenha( SenhaUser.getText().toString());
                String confsenhauser = ConfSenhaUser.getText().toString();


                if (userModel.getNome().isEmpty()) {
                    NomeUser.setError("Preencha o campo nome");
                    return;
                }
                if (userModel.getNome().length() > 30 ) {
                    NomeUser.setError("O nome não pode ter mais de 30 caracteres");
                    return;
                }
                if (userModel.getCpf().isEmpty()) {
                    CPFUser.setError("Preencha o campo CPF");
                    return;
                }
                if(!isCPF(CPFUser.getText().toString().trim())){
                    CPFUser.setError("CPF Inválido");
                    return;
                }
                if (userModel.getEmail().isEmpty()) {
                    EmailUser.setError("Preencha o campo email");
                    return;
                }
                if (userModel.getSenha().isEmpty()) {
                    SenhaUser.setError("Preencha o campo senha");
                    return;
                }
                if (confsenhauser.isEmpty()) {
                    ConfSenhaUser.setError("Preencha o campo confirmar senha");
                    return;
                }

                if (!userModel.getSenha().equals(confsenhauser)) {
                    ConfSenhaUser.setError("As senhas não estão iguais");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(userModel.getEmail(),userModel.getSenha()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                    public void onSuccess(AuthResult authResult) {
                        userModel.setId(firebaseAuth.getUid());
                        userModel.setPessoa("User");
                        userModel.salvar();
                        startActivity(new Intent(getActivity().getApplicationContext(), PrincipalActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

        return view;

    }

        public static boolean isCPF(String CPF) {
            CPF = CPF.replace(".", "");
            CPF = CPF.replace("-", "");
            if (CPF.equals("00000000000") ||
                    CPF.equals("11111111111") ||
                    CPF.equals("22222222222") || CPF.equals("33333333333") ||
                    CPF.equals("44444444444") || CPF.equals("55555555555") ||
                    CPF.equals("66666666666") || CPF.equals("77777777777") ||
                    CPF.equals("88888888888") || CPF.equals("99999999999") ||
                    (CPF.length() != 11))
                return(false);

            char dig10, dig11;
            int sm, i, r, num, peso;

            try {
                sm = 0;
                peso = 10;
                for (i=0; i<9; i++) {
                    num = (int)(CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig10 = '0';
                else dig10 = (char)(r + 48);

                sm = 0;
                peso = 11;
                for(i=0; i<10; i++) {
                    num = (int)(CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig11 = '0';
                else dig11 = (char)(r + 48);

                if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                    return(true);
                else return(false);
            } catch (InputMismatchException erro) {
                return(false);
            }
        }


    }
