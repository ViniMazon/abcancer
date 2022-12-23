package com.example.tcc_abcancer.ui.busca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tcc_abcancer.PostagemActivity;
import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.databinding.FragmentBuscarBinding;
import com.example.tcc_abcancer.ui.home.MyViewHolder;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class BuscarFragment extends Fragment {

    private BuscarViewModel buscarViewModel;
    //private FragmentBuscarBinding binding;
    private List<PostagemModel> postagemModels;
    private FirebaseRecyclerOptions<PostagemModel> options;
    private FirebaseRecyclerAdapter<PostagemModel, MyViewHolder> adapter;
    private RecyclerView recyclerViewV;
    private DatabaseReference ref;
    private EditText search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_buscar, container, false);


        recyclerViewV = rootView.findViewById(R.id.recyclerViewBuscar);
        recyclerViewV.setHasFixedSize(true);
        recyclerViewV.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        search = rootView.findViewById(R.id.edtSearch);

        ref = FirebaseDatabase.getInstance().getReference().child("Post");

        LoadData("");

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString()!=null){
                    LoadData(editable.toString());
                }
                else {
                    LoadData("");
                }

            }
        });

        return rootView;
    }

    private void LoadData(String data) {

        Query query = ref.orderByChild("tipo").startAt(data).endAt(data + "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<PostagemModel>().setQuery(query, PostagemModel.class).build();
        adapter = new FirebaseRecyclerAdapter<PostagemModel, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position, @NonNull @NotNull PostagemModel model) {

                final String key = getRef(position).getKey();
                final String foto = model.getImagem();

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), PostagemActivity.class);

                        //intent.putExtra("key", key);
                        intent.putExtra("chave", getRef(position).getKey());
                        startActivity(intent);

                    }
                });

                holder.txtNomeOng.setText(model.getNomeOng());
                Picasso.get().load(model.getLogo()).into(holder.imgOng);


            }

            @NonNull
            @NotNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_buscar, parent, false);


                return new MyViewHolder(v);
            }


        };


        adapter.startListening();
        recyclerViewV.setAdapter(adapter);
    }

}