package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class ActividadActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    Button btnMenu;

    Button btnMapa;
    Button btnPerfil;
    Button btnEventos;
    Button btnMurales;
    Button btnCupones;

    ListView listView;

    ArrayList<Evento> listaEventos =
            new ArrayList<>();

    ActividadAdapter adapter;

    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);

        // =====================================
        // 🔥 DRAWER
        // =====================================

        drawerLayout =
                findViewById(R.id.drawerLayout);

        // =====================================
        // 🔥 BOTONES
        // =====================================

        btnMenu =
                findViewById(R.id.btnMenu);

        btnMapa =
                findViewById(R.id.btnMapa);

        btnPerfil =
                findViewById(R.id.btnPerfil);

        btnEventos =
                findViewById(R.id.btnEventos);

        btnMurales =
                findViewById(R.id.btnMurales);

        btnCupones =
                findViewById(R.id.btnCupones);

        // =====================================
        // 🔥 USUARIO
        // =====================================

        correoUsuario =
                getIntent().getStringExtra("correo");

        // =====================================
        // 🔥 LISTVIEW
        // =====================================

        listView =
                findViewById(R.id.listActividad);

        // =====================================
        // 🔥 ADAPTER
        // =====================================

        adapter =
                new ActividadAdapter(
                        this,
                        listaEventos
                );

        listView.setAdapter(adapter);

        // =====================================
        // 🔥 ABRIR MENÚ
        // =====================================

        btnMenu.setOnClickListener(v -> {

            drawerLayout.openDrawer(Gravity.LEFT);

        });

        // =====================================
        // 🔥 MAPA
        // =====================================

        btnMapa.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            MapActivity.class
                    );

            i.putExtra("correo", correoUsuario);

            startActivity(i);
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
        // 🔥 CARGAR ACTIVIDAD
        // =====================================

        cargarActividad(correoUsuario);
    }

    // =========================================
    // 🔥 CARGAR ACTIVIDAD
    // =========================================

    private void cargarActividad(String correo) {

        String url =
                "http://192.168.128.8:3000/mis-eventos?correo="
                        + correo;

        JsonArrayRequest request =
                new JsonArrayRequest(

                        Request.Method.GET,

                        url,

                        null,

                        response -> {

                            listaEventos.clear();

                            for (int i = 0;
                                 i < response.length();
                                 i++) {

                                try {

                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    String id =
                                            obj.getString("_id");

                                    String titulo =
                                            obj.getString("titulo");

                                    String descripcion =
                                            obj.getString("descripcion");

                                    String descripcionCorta =
                                            descripcion.length() > 80
                                                    ? descripcion.substring(0, 80) + "..."
                                                    : descripcion;

                                    String fecha =
                                            obj.getString("fecha");

                                    String hora =
                                            obj.getString("hora");

                                    String direccion =
                                            obj.optString(
                                                    "direccion",
                                                    ""
                                            );

                                    String tipoEvento =
                                            obj.optString(
                                                    "tipoEvento",
                                                    "Gratis"
                                            );

                                    int interesados =
                                            obj.optInt(
                                                    "interesados",
                                                    0
                                            );

                                    double lat =
                                            obj.optDouble(
                                                    "lat",
                                                    0
                                            );

                                    double lng =
                                            obj.optDouble(
                                                    "lng",
                                                    0
                                            );

                                    listaEventos.add(

                                            new Evento(

                                                    id,

                                                    titulo,

                                                    descripcion,

                                                    descripcionCorta,

                                                    fecha,

                                                    hora,

                                                    direccion,

                                                    tipoEvento,

                                                    interesados,

                                                    lat,

                                                    lng,

                                                    new ArrayList<>()
                                            )
                                    );

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }

                            adapter.notifyDataSetChanged();
                        },

                        error -> Toast.makeText(
                                this,
                                "Error cargando actividad",
                                Toast.LENGTH_LONG
                        ).show()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }
}