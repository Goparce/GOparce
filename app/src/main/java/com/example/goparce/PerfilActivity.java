package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class PerfilActivity extends AppCompatActivity {

    // =====================================
    // 🔥 DRAWER
    // =====================================

    DrawerLayout drawerLayout;

    // =====================================
    // 🔥 BOTONES
    // =====================================

    Button btnMenu;

    Button btnMapa;
    Button btnActividad;
    Button btnEventos;
    Button btnMurales;
    Button btnCupones;

    Button btnGuardar;

    Button btnEditarCelular;
    Button btnEditarCorreo;

    // =====================================
    // 🔥 INPUTS
    // =====================================

    EditText etNombre;
    EditText etApellido;
    EditText etCelular;
    EditText etCorreo;

    // =====================================
    // 🔥 TEXTOS
    // =====================================

    TextView tvGustos;

    // =====================================
    // 🔥 USUARIO
    // =====================================

    String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

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

        btnActividad =
                findViewById(R.id.btnActividad);

        btnEventos =
                findViewById(R.id.btnEventos);

        btnMurales =
                findViewById(R.id.btnMurales);

        btnCupones =
                findViewById(R.id.btnCupones);

        btnGuardar =
                findViewById(R.id.btnGuardar);

        btnEditarCelular =
                findViewById(R.id.btnEditarCelular);

        btnEditarCorreo =
                findViewById(R.id.btnEditarCorreo);

        // =====================================
        // 🔥 INPUTS
        // =====================================

        etNombre =
                findViewById(R.id.etNombre);

        etApellido =
                findViewById(R.id.etApellido);

        etCelular =
                findViewById(R.id.etCelular);

        etCorreo =
                findViewById(R.id.etCorreo);

        // =====================================
        // 🔥 TEXTVIEW
        // =====================================

        tvGustos =
                findViewById(R.id.tvGustos);

        // =====================================
        // 🔥 BLOQUEAR INPUTS
        // =====================================

        etNombre.setEnabled(false);

        etApellido.setEnabled(false);

        etCelular.setEnabled(false);

        etCorreo.setEnabled(false);

        // =====================================
        // 🔥 MENÚ
        // =====================================

        btnMenu.setOnClickListener(v -> {

            drawerLayout.openDrawer(Gravity.LEFT);

        });

        // =====================================
        // 🔥 NAVEGACIÓN
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
        // 🔥 EDITAR CELULAR
        // =====================================

        btnEditarCelular.setOnClickListener(v -> {

            etCelular.setEnabled(true);

            etCelular.requestFocus();

            btnGuardar.setVisibility(View.VISIBLE);
        });

        // =====================================
        // 🔥 EDITAR CORREO
        // =====================================

        btnEditarCorreo.setOnClickListener(v -> {

            etCorreo.setEnabled(true);

            etCorreo.requestFocus();

            btnGuardar.setVisibility(View.VISIBLE);
        });

        // =====================================
        // 🔥 CARGAR PERFIL
        // =====================================

        obtenerPerfil();

        // =====================================
        // 🔥 GUARDAR
        // =====================================

        btnGuardar.setOnClickListener(v ->
                validarDatos()
        );
    }

    // =========================================
    // 🔥 OBTENER PERFIL
    // =========================================

    private void obtenerPerfil() {

        String url =
                "http://192.168.128.8:3000/perfil";

        JSONObject json =
                new JSONObject();

        try {

            json.put(
                    "correo",
                    correoUsuario
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest request =
                new JsonObjectRequest(

                        Request.Method.POST,

                        url,

                        json,

                        response -> {

                            try {

                                String nombre =
                                        response.optString(
                                                "nombre",
                                                ""
                                        );

                                String apellido =
                                        response.optString(
                                                "apellido",
                                                ""
                                        );

                                String celular =
                                        response.optString(
                                                "celular",
                                                ""
                                        );

                                String correo =
                                        response.optString(
                                                "correo",
                                                ""
                                        );

                                JSONArray gustos =
                                        response.getJSONArray(
                                                "gustos"
                                        );

                                // =====================================
                                // 🔥 DATOS
                                // =====================================

                                etNombre.setText(nombre);

                                etApellido.setText(apellido);

                                etCelular.setText(celular);

                                etCorreo.setText(correo);

                                // =====================================
                                // 🔥 GUSTOS LIMPIOS
                                // =====================================

                                StringBuilder gustosTexto =
                                        new StringBuilder();

                                for (int i = 0;
                                     i < gustos.length();
                                     i++) {

                                    String gusto =
                                            gustos.getString(i);

                                    // 🔥 ELIMINAR EMOJIS

                                    gusto =
                                            gusto.replaceAll(
                                                    "[^a-zA-ZÁÉÍÓÚáéíóúñÑ ]",
                                                    ""
                                            );

                                    gustosTexto.append(
                                            "• "
                                    );

                                    gustosTexto.append(
                                            gusto.trim()
                                    );

                                    gustosTexto.append(
                                            "\n"
                                    );
                                }

                                tvGustos.setText(
                                        gustosTexto.toString()
                                );

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        },

                        error -> Toast.makeText(

                                this,

                                "Error cargando perfil",

                                Toast.LENGTH_LONG
                        ).show()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }

    // =========================================
    // 🔥 VALIDAR
    // =========================================

    private void validarDatos() {

        String celular =
                etCelular.getText()
                        .toString()
                        .trim();

        String correo =
                etCorreo.getText()
                        .toString()
                        .trim();

        // =====================================
        // 🔥 CELULAR
        // =====================================

        if (!celular.matches("[0-9]+")) {

            etCelular.setError(
                    "Solo números"
            );

            return;
        }

        if (celular.length() != 10) {

            etCelular.setError(
                    "Debe tener 10 dígitos"
            );

            return;
        }

        // =====================================
        // 🔥 EMAIL
        // =====================================

        if (!Patterns.EMAIL_ADDRESS
                .matcher(correo)
                .matches()) {

            etCorreo.setError(
                    "Correo inválido"
            );

            return;
        }

        actualizarPerfil(
                celular,
                correo
        );
    }

    // =========================================
    // 🔥 ACTUALIZAR
    // =========================================

    private void actualizarPerfil(

            String celular,

            String correoNuevo
    ) {

        String url =
                "http://192.168.128.8:3000/actualizar-perfil";

        JSONObject json =
                new JSONObject();

        try {

            json.put(
                    "correoActual",
                    correoUsuario
            );

            json.put(
                    "correoNuevo",
                    correoNuevo
            );

            json.put(
                    "celular",
                    celular
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest request =
                new JsonObjectRequest(

                        Request.Method.POST,

                        url,

                        json,

                        response -> {

                            Toast.makeText(

                                    this,

                                    "Perfil actualizado 🔥",

                                    Toast.LENGTH_LONG
                            ).show();

                            correoUsuario =
                                    correoNuevo;

                            // 🔥 BLOQUEAR NUEVAMENTE

                            etCelular.setEnabled(false);

                            etCorreo.setEnabled(false);

                            btnGuardar.setVisibility(
                                    View.GONE
                            );
                        },

                        error -> Toast.makeText(

                                this,

                                "Error actualizando",

                                Toast.LENGTH_LONG
                        ).show()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }
}