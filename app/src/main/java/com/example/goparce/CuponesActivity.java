package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class CuponesActivity extends AppCompatActivity {

    // =====================================
    // 🔥 DRAWER
    // =====================================

    DrawerLayout drawerLayout;

    // =====================================
    // 🔥 BOTONES
    // =====================================

    Button btnMenu;

    Button btnMapa;
    Button btnPerfil;
    Button btnActividad;
    Button btnEventos;
    Button btnMurales;

    // =====================================
    // 🔥 LISTA
    // =====================================

    ListView listView;

    ArrayList<Cupon> lista =
            new ArrayList<>();

    CuponAdapter adapter;

    // =====================================
    // 🔥 USUARIO
    // =====================================

    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupones);

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

        btnActividad =
                findViewById(R.id.btnActividad);

        btnEventos =
                findViewById(R.id.btnEventos);

        btnMurales =
                findViewById(R.id.btnMurales);

        // =====================================
        // 🔥 USUARIO
        // =====================================

        correoUsuario =
                getIntent().getStringExtra("correo");

        // =====================================
        // 🔥 LISTVIEW
        // =====================================

        listView =
                findViewById(R.id.listCupones);

        // =====================================
        // 🔥 ADAPTER
        // =====================================

        adapter =
                new CuponAdapter(

                        this,

                        lista,

                        correoUsuario
                );

        listView.setAdapter(adapter);

        // =====================================
        // 🔥 MENÚ
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

            i.putExtra(
                    "correo",
                    correoUsuario
            );

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

            i.putExtra(
                    "correo",
                    correoUsuario
            );

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

            i.putExtra(
                    "correo",
                    correoUsuario
            );

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

            i.putExtra(
                    "correo",
                    correoUsuario
            );

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

            i.putExtra(
                    "correo",
                    correoUsuario
            );

            startActivity(i);
        });

        // =====================================
        // 🔥 CARGAR CUPONES
        // =====================================

        cargarCupones();
    }

    // =========================================
    // 🔥 CARGAR CUPONES
    // =========================================

    private void cargarCupones() {

        String url =
                "http://192.168.128.8:3000/cupones";

        JsonArrayRequest request =
                new JsonArrayRequest(

                        Request.Method.GET,

                        url,

                        null,

                        response -> {

                            lista.clear();

                            for (int i = 0;
                                 i < response.length();
                                 i++) {

                                try {

                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    int cantidad =
                                            Integer.parseInt(

                                                    obj.optString(
                                                            "cantidad",
                                                            "0"
                                                    )
                                            );

                                    lista.add(

                                            new Cupon(

                                                    obj.getString("_id"),

                                                    obj.getString("nombre"),

                                                    obj.getString("descripcion"),

                                                    obj.getString("codigo"),

                                                    obj.getString("valorPromo"),

                                                    cantidad,

                                                    obj.optInt(
                                                            "adquiridos",
                                                            0
                                                    ),

                                                    obj.optInt(
                                                            "maxUso",
                                                            1
                                                    )
                                            )
                                    );

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }

                            adapter.notifyDataSetChanged();
                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }
}