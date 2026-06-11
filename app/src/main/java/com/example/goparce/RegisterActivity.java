package com.example.goparce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etNombre;
    EditText etApellido;
    EditText etCelular;
    EditText etCorreo;
    EditText etPassword;
    EditText etConfirmPassword;

    Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // =====================================
        // 🔥 INPUTS
        // =====================================

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCelular = findViewById(R.id.etCelular);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        // =====================================
        // 🔥 BOTÓN
        // =====================================

        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(v -> validarCampos());
    }

    private void validarCampos() {

        String nombre =
                etNombre.getText().toString().trim();

        String apellido =
                etApellido.getText().toString().trim();

        String celular =
                etCelular.getText().toString().trim();

        String correo =
                etCorreo.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

        String confirmPassword =
                etConfirmPassword.getText().toString().trim();

        // =====================================
        // 🔥 VALIDAR NOMBRE
        // =====================================

        if (nombre.isEmpty()) {

            etNombre.setError("Ingresa tu nombre");
            etNombre.requestFocus();
            return;
        }

        // =====================================
        // 🔥 VALIDAR APELLIDO
        // =====================================

        if (apellido.isEmpty()) {

            etApellido.setError("Ingresa tu apellido");
            etApellido.requestFocus();
            return;
        }

        // =====================================
        // 🔥 VALIDAR CELULAR
        // =====================================

        if (celular.isEmpty()) {

            etCelular.setError("Ingresa tu celular");
            etCelular.requestFocus();
            return;
        }

        // SOLO NÚMEROS

        if (!celular.matches("[0-9]+")) {

            etCelular.setError("Solo números");
            etCelular.requestFocus();
            return;
        }

        // EXACTAMENTE 10 DÍGITOS

        if (celular.length() != 10) {

            etCelular.setError("Debe tener 10 dígitos");
            etCelular.requestFocus();
            return;
        }

        // =====================================
        // 🔥 VALIDAR CORREO
        // =====================================

        if (correo.isEmpty()) {

            etCorreo.setError("Ingresa tu correo");
            etCorreo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {

            etCorreo.setError("Correo inválido");
            etCorreo.requestFocus();
            return;
        }

        // =====================================
        // 🔥 VALIDAR PASSWORD
        // =====================================

        if (password.isEmpty()) {

            etPassword.setError("Ingresa una contraseña");
            etPassword.requestFocus();
            return;
        }

        // MÍNIMO 6

        if (password.length() < 6) {

            etPassword.setError("Mínimo 6 caracteres");
            etPassword.requestFocus();
            return;
        }

        // DEBE TENER NÚMERO

        if (!password.matches(".*[0-9].*")) {

            etPassword.setError("Debe contener al menos 1 número");
            etPassword.requestFocus();
            return;
        }

        // DEBE TENER MAYÚSCULA

        if (!password.matches(".*[A-Z].*")) {

            etPassword.setError("Debe contener una mayúscula");
            etPassword.requestFocus();
            return;
        }

        // =====================================
        // 🔥 CONFIRMAR PASSWORD
        // =====================================

        if (!password.equals(confirmPassword)) {

            etConfirmPassword.setError("Las contraseñas no coinciden");
            etConfirmPassword.requestFocus();
            return;
        }

        // =====================================
        // 🔥 ENVIAR A GUSTOS
        // =====================================

        Intent intent =
                new Intent(RegisterActivity.this,
                        GustosActivity.class);

        intent.putExtra("nombre", nombre);
        intent.putExtra("apellido", apellido);
        intent.putExtra("celular", celular);
        intent.putExtra("correo", correo);
        intent.putExtra("password", password);

        startActivity(intent);
    }
}