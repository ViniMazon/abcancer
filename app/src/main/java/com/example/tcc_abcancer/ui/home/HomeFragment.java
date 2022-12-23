package com.example.tcc_abcancer.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc_abcancer.PostagemActivity;
import com.example.tcc_abcancer.PrincipalActivity;
import com.example.tcc_abcancer.R;


import com.example.tcc_abcancer.ui.adapter.NovaOngAdapter;
import com.example.tcc_abcancer.ui.model.CampanhaModel;
import com.example.tcc_abcancer.ui.model.OngsModel;
import com.example.tcc_abcancer.ui.model.PostagemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    RecyclerView recyclerView;
    NovaOngAdapter novaOngAdapter;
    List<OngsModel> ongsModelList;
    private List<PostagemModel> postagemModels;
    private FirebaseRecyclerOptions<PostagemModel> options;
    private FirebaseRecyclerAdapter<PostagemModel, MyViewHolder> adapter;
    private FirebaseRecyclerOptions<CampanhaModel> optionsC;
    private FirebaseRecyclerAdapter<CampanhaModel, MyViewHolder> adapterC;
    private RecyclerView recyclerViewV, recyclerViewPostagem;
    private FirebaseAuth auth;
    private Context mContext;
    private TextView txt;
    FusedLocationProviderClient fusedLocationProviderClient;
    private EditText search;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewV = rootView.findViewById(R.id.recyclerViewHome);
        recyclerViewV.setHasFixedSize(true);
        recyclerViewV.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        txt = rootView.findViewById(R.id.txtTesteLocal);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("ONGS");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Post");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Campanhas");
        mStorage = FirebaseStorage.getInstance();


            options = new FirebaseRecyclerOptions.Builder<PostagemModel>().setQuery(ref.orderByChild("dist"), PostagemModel.class).build();
            adapter = new FirebaseRecyclerAdapter<PostagemModel, MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position, @NonNull @NotNull PostagemModel model) {

                    //final String imagem = model.getImagem();

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
                    holder.txtDist.setText(model.getDist());
                    Picasso.get().load(model.getLogo()).into(holder.imgOng);


                }

                @NonNull
                @NotNull
                @Override
                public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home, parent, false);


                    return new MyViewHolder(v);
                }


            };


            adapter.startListening();
            recyclerViewV.setAdapter(adapter);




        return rootView;

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //txt.setText(addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

