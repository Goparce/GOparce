package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventosActivity extends AppCompatActivity {

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
    Button btnMurales;
    Button btnCupones;

    // =====================================
    // 🔥 FILTROS
    // =====================================

    LinearLayout layoutCategorias;

    Button btnTodos;

    // =====================================
    // 🔥 LISTVIEW
    // =====================================

    ListView listView;

    // =====================================
    // 🔥 LISTAS
    // =====================================

    ArrayList<Evento> listaEventos =
            new ArrayList<>();

    ArrayList<Evento> listaOriginal =
            new ArrayList<>();

    // =====================================
    // 🔥 ADAPTER
    // =====================================

    EventosAdapter adapter;

    // =====================================
    // 🔥 USUARIO
    // =====================================

    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        // =====================================
        // 🔥 DRAWER
        // =====================================

        drawerLayout =
                findViewById(R.id.drawerLayout);

        // =====================================
        // 🔥 USUARIO
        // =====================================

        correoUsuario =
                getIntent().getStringExtra("correo");

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

        btnMurales =
                findViewById(R.id.btnMurales);

        btnCupones =
                findViewById(R.id.btnCupones);

        // =====================================
        // 🔥 FILTROS
        // =====================================

        layoutCategorias =
                findViewById(R.id.layoutCategorias);

        btnTodos =
                findViewById(R.id.btnTodos);

        // =====================================
        // 🔥 LISTVIEW
        // =====================================

        listView =
                findViewById(R.id.listEventos);

        // =====================================
        // 🔥 ADAPTER
        // =====================================

        adapter =
                new EventosAdapter(
                        this,
                        listaEventos,
                        correoUsuario
                );

        listView.setAdapter(adapter);

        // =====================================
        // 🔥 TODOS
        // =====================================

        btnTodos.setOnClickListener(v -> {

            listaEventos.clear();

            listaEventos.addAll(listaOriginal);

            adapter.notifyDataSetChanged();
        });

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
        // 🔥 CARGAR EVENTOS
        // =====================================

        cargarEventos();
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

                            listaEventos.clear();

                            listaOriginal.clear();

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

                                    // =====================================
                                    // 🔥 DESCRIPCIÓN CORTA
                                    // =====================================

                                    String descripcionCorta =
                                            obj.optString(

                                                    "descripcionCorta",

                                                    descripcion.length() > 80

                                                            ? descripcion.substring(
                                                            0,
                                                            80
                                                    ) + "..."

                                                            : descripcion
                                            );

                                    String fecha =
                                            obj.getString("fecha");

                                    String hora =
                                            obj.getString("hora");

                                    String direccion =
                                            obj.getString("direccion");

                                    // =====================================
                                    // 🔥 GRATIS / PAGO
                                    // =====================================

                                    String tipoEvento =
                                            obj.optString(
                                                    "tipoEvento",
                                                    "Gratis"
                                            );

                                    // =====================================
                                    // 🔥 INTERESADOS
                                    // =====================================

                                    int interesados =
                                            obj.optInt(
                                                    "interesados",
                                                    0
                                            );

                                    // =====================================
                                    // 🔥 UBICACIÓN
                                    // =====================================

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

                                    // =====================================
                                    // 🔥 ETIQUETAS
                                    // =====================================

                                    JSONArray etiquetasJSON =
                                            obj.getJSONArray(
                                                    "etiquetas"
                                            );

                                    ArrayList<String> etiquetas =
                                            new ArrayList<>();

                                    for (int j = 0;
                                         j < etiquetasJSON.length();
                                         j++) {

                                        etiquetas.add(

                                                etiquetasJSON
                                                        .getString(j)
                                        );
                                    }

                                    // =====================================
                                    // 🔥 EVENTO
                                    // =====================================

                                    Evento evento =
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

                                                    etiquetas
                                            );

                                    listaEventos.add(evento);

                                    listaOriginal.add(evento);

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }

                            adapter.notifyDataSetChanged();

                            crearCategorias();
                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }

    // =========================================
    // 🔥 CREAR CATEGORÍAS
    // =========================================

    private void crearCategorias() {

        layoutCategorias.removeAllViews();

        // =====================================
        // 🔥 TODOS
        // =====================================

        layoutCategorias.addView(btnTodos);

        ArrayList<String> categorias =
                new ArrayList<>();

        for (Evento e : listaOriginal) {

            if (e.etiquetas.size() > 0) {

                String categoria =
                        e.etiquetas.get(0);

                if (!categorias.contains(categoria)) {

                    categorias.add(categoria);

                    Button btn =
                            new Button(this);

                    btn.setText(categoria);

                    btn.setOnClickListener(v -> {

                        filtrarCategoria(categoria);

                    });

                    layoutCategorias.addView(btn);
                }
            }
        }
    }

    // =========================================
    // 🔥 FILTRAR CATEGORÍA
    // =========================================

    private void filtrarCategoria(String categoria) {

        listaEventos.clear();

        for (Evento e : listaOriginal) {

            if (e.etiquetas.size() > 0) {

                if (e.etiquetas
                        .get(0)
                        .equalsIgnoreCase(categoria)) {

                    listaEventos.add(e);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}