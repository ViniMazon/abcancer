package com.example.tcc_abcancer.ui.campaign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.CriarCampanhaActivity;
import com.example.tcc_abcancer.EditarActivity;
import com.example.tcc_abcancer.EditarCampanha;
import com.example.tcc_abcancer.PostagemActivity;
import com.example.tcc_abcancer.R;
import com.example.tcc_abcancer.ui.campanha.CampanhaViewHolder;
import com.example.tcc_abcancer.ui.model.CampanhaModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CampaignFragment extends Fragment {

    private FloatingActionButton btnIr;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private FirebaseRecyclerOptions<CampanhaModel> options;
    private FirebaseRecyclerAdapter<CampanhaModel, CampaignViewHolder> adapter;
    private FirebaseAuth firebaseAuth;
    private long maxid;
    private String teste;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){



        View rootView = inflater.inflate(R.layout.fragment_campaign, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerViewCampOng);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        firebaseAuth = FirebaseAuth.getInstance();
        teste = firebaseAuth.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Campanha").child(firebaseAuth.getUid());
         //String teste = "Zhdo0K7e8vW2h3m26m65mpTDrUi2";

        btnIr = rootView.findViewById(R.id.btnCriarCamp);

        btnIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getActivity(), CriarCampanhaActivity.class);
                startActivity(in);

            }
        });


        options = new FirebaseRecyclerOptions.Builder<CampanhaModel>().setQuery(reference, CampanhaModel.class).build();
        adapter = new FirebaseRecyclerAdapter<CampanhaModel, CampaignViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull CampaignViewHolder holder, int position, @NonNull @NotNull CampanhaModel model) {

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), EditarCampanha.class);
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
            public CampaignViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item2, parent, false);


                return new CampaignViewHolder(v);
            }


        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);



        return rootView;

/*



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                teste = snapshot.getValue(CampanhaModel.class).getId();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



                //Query query = reference.child(teste);
                options = new FirebaseRecyclerOptions.Builder<CampanhaModel>().setQuery(reference, CampanhaModel.class).build();
                adapter = new FirebaseRecyclerAdapter<CampanhaModel, CampaignViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull CampaignViewHolder holder, int position, @NonNull @NotNull CampanhaModel model) {

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), EditarCampanha.class);
                        intent.putExtra("key", getRef(position).getKey());
                        startActivity(intent);

                    }
                });


                        holder.txtNomeOng.setText(model.getDesc());
                        Picasso.get().load(model.getFoto()).into(holder.img);

                    }

                    @NonNull
                    @NotNull
                    @Override
                    public CampaignViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item2, parent, false);


                        return new CampaignViewHolder(v);
                    }


                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


        //Query query = reference.child("id").equalTo(firebaseAuth.getUid());


        return rootView;

 */


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}



