package com.example.tcc_abcancer.ui.campanha;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.EditarCampanha;
import com.example.tcc_abcancer.PostagemActivity;
import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.UserCampanhaActivity;
import com.example.tcc_abcancer.ui.home.MyViewHolder;
import com.example.tcc_abcancer.ui.model.CampanhaModel;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CampanhaFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private FirebaseRecyclerOptions<CampanhaModel> options;
    private FirebaseRecyclerAdapter<CampanhaModel, CampanhaViewHolder> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_campanha, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewCamp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        reference = FirebaseDatabase.getInstance().getReference().child("Campanhas");


        options = new FirebaseRecyclerOptions.Builder<CampanhaModel>().setQuery(reference, CampanhaModel.class).build();
        adapter = new FirebaseRecyclerAdapter<CampanhaModel, CampanhaViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CampanhaViewHolder holder, int position, @NonNull @NotNull CampanhaModel model) {

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), UserCampanhaActivity.class);
                        intent.putExtra("key", getRef(position).getKey());
                        startActivity(intent);

                    }
                });


                holder.txtNomeOng.setText(model.getNome());
                Picasso.get().load(model.getFoto()).into(holder.img);

            }

            @NonNull
            @NotNull
            @Override
            public CampanhaViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item2, parent, false);


                return new CampanhaViewHolder(v);
            }


        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
