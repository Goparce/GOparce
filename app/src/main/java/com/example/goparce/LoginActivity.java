package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText etCorreo, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 🔥 VALIDACIÓN PARA EVITAR CRASH
        if (etCorreo == null || etPassword == null || btnLogin == null) {
            Toast.makeText(this, "Error en layout (IDs mal conectados)", Toast.LENGTH_LONG).show();
            return;
        }

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {

        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.128.8:3000/login";

        JSONObject json = new JSONObject();

        try {
            json.put("correo", correo);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                json,

                // ✅ LOGIN EXITOSO
                response -> {
                    Toast.makeText(this, "Bienvenido 🔥", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    intent.putExtra("correo", correo);
                    startActivity(intent);
                    finish();
                },

                // ❌ ERROR
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}