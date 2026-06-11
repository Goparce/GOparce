package com.example.goparce;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import org.json.JSONObject;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;

    DrawerLayout drawerLayout;

    Button btnMiUbicacion;
    Button btnMenu;

    Button btnPerfil;
    Button btnActividad;
    Button btnEventos;
    Button btnMurales;
    Button btnCupones;

    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // =====================================
        // 🔥 DRAWER
        // =====================================

        drawerLayout =
                findViewById(R.id.drawerLayout);

        // =====================================
        // 🔥 RECIBIR USUARIO
        // =====================================

        correoUsuario =
                getIntent().getStringExtra("correo");

        // =====================================
        // 🔥 UBICACIÓN
        // =====================================

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        // =====================================
        // 🔥 BOTONES PRINCIPALES
        // =====================================

        btnMiUbicacion =
                findViewById(R.id.btnMiUbicacion);

        btnMenu =
                findViewById(R.id.btnMenu);

        // =====================================
        // 🔥 BOTONES MENÚ
        // =====================================

        btnPerfil =
                findViewById(R.id.btnPerfil);

        btnActividad =
                findViewById(R.id.btnActividad);

        btnEventos =
                findViewById(R.id.btnEventos);

        btnMurales =
                findViewById(R.id.btnMurales);

        btnCupones =
                findViewById(R.id.btnCupones);

        // =====================================
        // 🔥 CENTRAR UBICACIÓN
        // =====================================

        btnMiUbicacion.setOnClickListener(v ->
                centrarUbicacion()
        );

        // =====================================
        // 🔥 ABRIR MENÚ LATERAL
        // =====================================

        btnMenu.setOnClickListener(v -> {

            drawerLayout.openDrawer(Gravity.LEFT);

        });

        // =====================================
        // 🔥 PERFIL
        // =====================================

        btnPerfil.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            PerfilActivity.class
                    );

            i.putExtra("correo", correoUsuario);

            startActivity(i);
        });

        // =====================================
        // 🔥 ACTIVIDAD
        // =====================================

        btnActividad.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            ActividadActivity.class
                    );

            i.putExtra("correo", correoUsuario);

            startActivity(i);
        });

        // =====================================
        // 🔥 EVENTOS
        // =====================================

        btnEventos.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            EventosActivity.class
                    );

            i.putExtra("correo", correoUsuario);

            startActivity(i);
        });

        // =====================================
        // 🔥 MURALES
        // =====================================

        btnMurales.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            MuralesActivity.class
                    );

            i.putExtra("correo", correoUsuario);

            startActivity(i);
        });

        // =====================================
        // 🔥 CUPONES
        // =====================================

        btnCupones.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            CuponesActivity.class
                    );

            i.putExtra("correo", correoUsuario);

            startActivity(i);
        });

        // =====================================
        // 🔥 MAPA
        // =====================================

        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager()
                                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    // =========================================
    // 🔥 MAP READY
    // =========================================

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        activarUbicacion();

        cargarEventos();

        centrarUbicacion();
    }

    // =========================================
    // 🔥 ACTIVAR UBICACIÓN
    // =========================================

    private void activarUbicacion() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    1
            );

            return;
        }

        // 🔥 PUNTO AZUL

        mMap.setMyLocationEnabled(true);

        // 🔥 QUITAR BOTÓN ROJO GOOGLE

        mMap.getUiSettings()
                .setMyLocationButtonEnabled(false);
    }

    // =========================================
    // 🔥 CENTRAR UBICACIÓN
    // =========================================

    private void centrarUbicacion() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        fusedLocationClient.getLastLocation()

                .addOnSuccessListener(location -> {

                    if (location != null) {

                        LatLng miUbicacion =
                                new LatLng(
                                        location.getLatitude(),
                                        location.getLongitude()
                                );

                        mMap.animateCamera(
                                CameraUpdateFactory
                                        .newLatLngZoom(
                                                miUbicacion,
                                                15
                                        )
                        );
                    }
                });
    }

    // =========================================
    // 🔥 CARGAR EVENTOS
    // =========================================

    private void cargarEventos() {

        String url =
                "http://192.168.128.8:3000/eventos";

        JsonArrayRequest request =
                new JsonArrayRequest(

                        Request.Method.GET,

                        url,

                        null,

                        response -> {

                            for (int i = 0; i < response.length(); i++) {

                                try {

                                    JSONObject evento =
                                            response.getJSONObject(i);

                                    double lat =
                                            evento.getDouble("lat");

                                    double lng =
                                            evento.getDouble("lng");

                                    String nombre =
                                            evento.getString("titulo");

                                    LatLng ubicacion =
                                            new LatLng(lat, lng);

                                    mMap.addMarker(
                                            new MarkerOptions()
                                                    .position(ubicacion)
                                                    .title(nombre)
                                    );

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }
                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this).add(request);
    }
}