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

public class MuralesActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    Button btnMenu;

    Button btnMapa;
    Button btnPerfil;
    Button btnActividad;
    Button btnEventos;
    Button btnCupones;

    ListView listView;

    ArrayList<Muro> listaMuros =
            new ArrayList<>();

    MuroAdapter adapter;

    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murales);

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
                findViewById(R.id.listMuros);

        // =====================================
        // 🔥 ADAPTER
        // =====================================

        adapter =
                new MuroAdapter(
                        this,
                        listaMuros
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
        // 🔥 CUPONES
        // =====================================

        btnCupones.setOnClickListener(v -> {

            Intent i =
                    new Intent(
                            this,
                            CuponesActivity.class
                    );

            i.putExtra(
                    "correo",
                    correoUsuario
            );

            startActivity(i);
        });

        // =====================================
        // 🔥 CARGAR MUROS
        // =====================================

        cargarMuros();

        // =====================================
        // 🔥 CLICK MURO
        // =====================================

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Muro m =
                    listaMuros.get(position);

            Intent intent =
                    new Intent(
                            this,
                            DetalleMuroActivity.class
                    );

            // =====================================
            // 🔥 DATOS MURO
            // =====================================

            intent.putExtra(
                    "usuarioId",
                    m.usuarioId
            );

            intent.putExtra(
                    "titulo",
                    m.titulo
            );

            intent.putExtra(
                    "correo",
                    correoUsuario
            );

            startActivity(intent);
        });
    }

    // =========================================
    // 🔥 CARGAR MUROS
    // =========================================

    private void cargarMuros() {

        String url =
                "http://192.168.128.8:3000/muros";

        JsonArrayRequest request =
                new JsonArrayRequest(

                        Request.Method.GET,

                        url,

                        null,

                        response -> {

                            listaMuros.clear();

                            for (int i = 0;
                                 i < response.length();
                                 i++) {

                                try {

                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    // =====================================
                                    // 🔥 DATOS
                                    // =====================================

                                    String id =
                                            obj.getString("_id");

                                    String titulo =
                                            obj.getString("titulo");

                                    String descripcion =
                                            obj.getString("descripcion");

                                    String usuarioId =
                                            obj.optString(
                                                    "usuario_id",
                                                    ""
                                            );

                                    int cantidadNoticias =
                                            obj.optInt(
                                                    "cantidadNoticias",
                                                    0
                                            );

                                    int cantidadServicios =
                                            obj.optInt(
                                                    "cantidadServicios",
                                                    0
                                            );

                                    String fechaCreacion =
                                            obj.optString(
                                                    "fecha_creacion",
                                                    "Sin fecha"
                                            );

                                    // =====================================
                                    // 🔥 CREAR MURO
                                    // =====================================

                                    listaMuros.add(

                                            new Muro(

                                                    id,

                                                    titulo,

                                                    descripcion,

                                                    usuarioId,

                                                    cantidadNoticias,

                                                    cantidadServicios,

                                                    fechaCreacion
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