package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GustosActivity extends AppCompatActivity {

    LinearLayout containerCategorias;
    Button btnFinalizar;

    ArrayList<CheckBox> listaCheckBox = new ArrayList<>();

    String nombre;
    String apellido;
    String celular;
    String correo;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gustos);

        // =====================================
        // 🔥 COMPONENTES
        // =====================================

        containerCategorias = findViewById(R.id.containerCategorias);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        // =====================================
        // 🔥 RECIBIR DATOS DEL REGISTRO
        // =====================================

        nombre = getIntent().getStringExtra("nombre");
        apellido = getIntent().getStringExtra("apellido");
        celular = getIntent().getStringExtra("celular");
        correo = getIntent().getStringExtra("correo");
        password = getIntent().getStringExtra("password");

        // =====================================
        // 🔥 CARGAR CATEGORÍAS
        // =====================================

        obtenerCategorias();

        // =====================================
        // 🔥 BOTÓN FINALIZAR
        // =====================================

        btnFinalizar.setOnClickListener(v -> validarSeleccion());
    }

    // =========================================
    // 🔥 OBTENER CATEGORÍAS
    // =========================================

    private void obtenerCategorias() {

        String url = "http://192.168.128.8:3000/categorias";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    containerCategorias.removeAllViews();
                    listaCheckBox.clear();

                    try {

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject categoria =
                                    response.getJSONObject(i);

                            // 🔥 SOLO CATEGORÍA PRINCIPAL

                            String nombre =
                                    categoria.getString("nombre");

                            String icono =
                                    categoria.optString("icono", "📌");

                            CheckBox checkBox =
                                    new CheckBox(this);

                            checkBox.setText(icono + " " + nombre);

                            checkBox.setTextSize(18);

                            checkBox.setPadding(
                                    20,
                                    20,
                                    20,
                                    20
                            );

                            containerCategorias.addView(checkBox);

                            listaCheckBox.add(checkBox);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                },

                error -> {

                    error.printStackTrace();

                    Toast.makeText(
                            this,
                            "Error conectando con servidor",
                            Toast.LENGTH_LONG
                    ).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    // =========================================
    // 🔥 VALIDAR GUSTOS
    // =========================================

    private void validarSeleccion() {

        int contador = 0;

        JSONArray gustos = new JSONArray();

        for (CheckBox cb : listaCheckBox) {

            if (cb.isChecked()) {

                contador++;

                gustos.put(cb.getText().toString());
            }
        }

        // =====================================
        // 🔥 VALIDAR MÍNIMO
        // =====================================

        if (contador == 0) {

            Toast.makeText(
                    this,
                    "Selecciona al menos 1 gusto",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // =====================================
        // 🔥 VALIDAR MÁXIMO
        // =====================================

        if (contador > 3) {

            Toast.makeText(
                    this,
                    "Máximo 3 gustos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // =====================================
        // 🔥 ENVIAR DATOS
        // =====================================

        enviarDatos(gustos);
    }

    // =========================================
    // 🔥 ENVIAR REGISTRO
    // =========================================

    private void enviarDatos(JSONArray gustos) {

        btnFinalizar.setEnabled(false);

        String url = "http://192.168.128.8:3000/registro";

        JSONObject json = new JSONObject();

        try {

            json.put("nombre", nombre);

            json.put("apellido", apellido);

            json.put("celular", celular);

            json.put("correo", correo);

            json.put("password", password);

            json.put("gustos", gustos);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(

                Request.Method.POST,

                url,

                json,

                response -> {

                    try {

                        String usuarioId =
                                response.getString("usuarioId");

                        Toast.makeText(
                                this,
                                "Registro completado 🎉\n" + usuarioId,
                                Toast.LENGTH_LONG
                        ).show();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    // =====================================
                    // 🔥 IR AL MAPA
                    // =====================================

                    Intent intent =
                            new Intent(
                                    GustosActivity.this,
                                    MapActivity.class
                            );

                    intent.putExtra("correo", correo);

                    startActivity(intent);

                    finish();
                },

                error -> {

                    btnFinalizar.setEnabled(true);

                    error.printStackTrace();

                    try {

                        String respuesta =
                                new String(error.networkResponse.data);

                        JSONObject obj =
                                new JSONObject(respuesta);

                        String mensaje =
                                obj.getString("error");

                        Toast.makeText(
                                this,
                                mensaje,
                                Toast.LENGTH_LONG
                        ).show();

                    } catch (Exception e) {

                        Toast.makeText(
                                this,
                                "Error registrando usuario",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}