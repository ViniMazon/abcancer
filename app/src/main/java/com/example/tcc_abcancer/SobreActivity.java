package com.example.tcc_abcancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

public class SobreActivity extends AppCompatActivity implements OnMapReadyCallback {

    boolean isPermissionGranted;
    GoogleMap mMap;
    private ImageView face, tt, insta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        face = findViewById(R.id.imageView9);
        tt = findViewById(R.id.imageView11);
        insta = findViewById(R.id.imageView10);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        supportMapFragment.getMapAsync(this);

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.facebook.com/ABC%C3%A2ncer-102974298856694/");
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/_abcancer_/");
            }
        });

        //checkMyPermission();

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }

    /*
        private void checkMyPermission() {

            Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    Toast.makeText(SobreActivity.this, "Permissão concedidad", Toast.LENGTH_SHORT).show();
                    isPermissionGranted = true;
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), "");
                    intent.setData(uri);
                    startActivity(intent);
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();
        }
    */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMyLocationEnabled(true);


        //mMap.getUiSettings().setZoomControlsEnabled(true);

// minha posição
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);

// Add a marker in Sydney and move the camera
        LatLng brasil = new LatLng(-23.63803504139517, -46.578020802644616);
        mMap.addMarker(new MarkerOptions().position(brasil).title("ETEC Jorge Street"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(brasil));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(brasil)
                .zoom(15)
                .bearing(5)
                .tilt(5)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition( cameraPosition ));
    }

}