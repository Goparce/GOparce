package com.example.goparce;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetalleMuroActivity extends AppCompatActivity {

    // =====================================
    // 🔥 COMPONENTES
    // =====================================

    TextView tituloMuro;

    ListView listNoticias;

    ListView listServicios;

    // =====================================
    // 🔥 LISTAS
    // =====================================

    ArrayList<Noticia> noticias =
            new ArrayList<>();

    ArrayList<Servicio> servicios =
            new ArrayList<>();

    // =====================================
    // 🔥 ADAPTERS
    // =====================================

    NoticiaAdapter adapterNoticias;

    ServicioAdapter adapterServicios;

    // =====================================
    // 🔥 DATOS
    // =====================================

    String usuarioId;

    String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_muro);

        // =====================================
        // 🔥 COMPONENTES
        // =====================================

        tituloMuro =
                findViewById(R.id.tituloMuro);

        listNoticias =
                findViewById(R.id.listNoticias);

        listServicios =
                findViewById(R.id.listServicios);

        // =====================================
        // 🔥 DATOS
        // =====================================

        usuarioId =
                getIntent().getStringExtra("usuarioId");

        titulo =
                getIntent().getStringExtra("titulo");

        // =====================================
        // 🔥 TÍTULO
        // =====================================

        tituloMuro.setText(
                "🧩 " + titulo
        );

        // =====================================
        // 🔥 ADAPTER NOTICIAS
        // =====================================

        adapterNoticias =
                new NoticiaAdapter(

                        this,

                        noticias
                );

        listNoticias.setAdapter(
                adapterNoticias
        );

        // =====================================
        // 🔥 ADAPTER SERVICIOS
        // =====================================

        adapterServicios =
                new ServicioAdapter(

                        this,

                        servicios
                );

        listServicios.setAdapter(
                adapterServicios
        );

        // =====================================
        // 🔥 CARGAR DATOS
        // =====================================

        cargarNoticias();

        cargarServicios();
    }

    // =========================================
    // 🔥 CARGAR NOTICIAS
    // =========================================

    private void cargarNoticias() {

        String url =
                "http://192.168.128.8:3000/noticias-muro?usuarioId="
                        + usuarioId;

        JsonArrayRequest request =
                new JsonArrayRequest(

                        Request.Method.GET,

                        url,

                        null,

                        response -> {

                            noticias.clear();

                            for (int i = 0;
                                 i < response.length();
                                 i++) {

                                try {

                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    String titulo =
                                            obj.optString(
                                                    "titulo",
                                                    ""
                                            );

                                    String descripcion =
                                            obj.optString(
                                                    "descripcion",
                                                    ""
                                            );

                                    String tipo =
                                            obj.optString(
                                                    "tipo",
                                                    ""
                                            );

                                    String link =
                                            obj.optString(
                                                    "link",
                                                    ""
                                            );

                                    noticias.add(

                                            new Noticia(

                                                    titulo,

                                                    descripcion,

                                                    tipo,

                                                    link
                                            )
                                    );

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }

                            adapterNoticias
                                    .notifyDataSetChanged();
                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }

    // =========================================
    // 🔥 CARGAR SERVICIOS
    // =========================================

    private void cargarServicios() {

        String url =
                "http://192.168.128.8:3000/servicios-muro?usuarioId="
                        + usuarioId;

        JsonArrayRequest request =
                new JsonArrayRequest(

                        Request.Method.GET,

                        url,

                        null,

                        response -> {

                            servicios.clear();

                            for (int i = 0;
                                 i < response.length();
                                 i++) {

                                try {

                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    String titulo =
                                            obj.optString(
                                                    "titulo",
                                                    ""
                                            );

                                    String descripcion =
                                            obj.optString(
                                                    "descripcion",
                                                    ""
                                            );

                                    String costo =
                                            obj.optString(
                                                    "costo",
                                                    "Gratis"
                                            );

                                    String cupos =
                                            obj.optString(
                                                    "cupos",
                                                    "Abierto"
                                            );

                                    servicios.add(

                                            new Servicio(

                                                    titulo,

                                                    descripcion,

                                                    costo,

                                                    cupos
                                            )
                                    );

                                } catch (Exception e) {

                                    e.printStackTrace();
                                }
                            }

                            adapterServicios
                                    .notifyDataSetChanged();
                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }
}