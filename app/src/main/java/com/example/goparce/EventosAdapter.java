package com.example.goparce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class EventosAdapter extends ArrayAdapter<Evento> {

    // =====================================
    // 🔥 VARIABLES
    // =====================================

    Activity context;

    ArrayList<Evento> lista;

    String correoUsuario;

    // =====================================
    // 🔥 CONSTRUCTOR
    // =====================================

    public EventosAdapter(

            Activity context,

            ArrayList<Evento> lista,

            String correoUsuario
    ) {

        super(
                context,
                R.layout.item_evento,
                lista
        );

        this.context = context;

        this.lista = lista;

        this.correoUsuario = correoUsuario;
    }

    // =====================================
    // 🔥 GET VIEW
    // =====================================

    @Override
    public View getView(

            int position,

            View view,

            ViewGroup parent
    ) {

        LayoutInflater inflater =
                context.getLayoutInflater();

        View row =
                inflater.inflate(

                        R.layout.item_evento,

                        null,

                        true
                );

        // =====================================
        // 🔥 COMPONENTES
        // =====================================

        TextView txtTitulo =
                row.findViewById(R.id.txtTitulo);

        TextView txtDescripcion =
                row.findViewById(R.id.txtDescripcion);

        TextView txtCategoria =
                row.findViewById(R.id.txtCategoria);

        TextView txtFecha =
                row.findViewById(R.id.txtFecha);

        TextView txtDistancia =
                row.findViewById(R.id.txtDistancia);

        TextView txtTipo =
                row.findViewById(R.id.txtTipo);

        TextView txtInteresados =
                row.findViewById(R.id.txtInteresados);

        Button btnVerMas =
                row.findViewById(R.id.btnVerMas);

        // =====================================
        // 🔥 EVENTO
        // =====================================

        Evento e =
                lista.get(position);

        // =====================================
        // 🔥 TITULO
        // =====================================

        txtTitulo.setText(
                "🎉 " + e.titulo
        );

        // =====================================
        // 🔥 DESCRIPCIÓN
        // =====================================

        txtDescripcion.setText(
                e.descripcionCorta
        );

        // =====================================
        // 🔥 CATEGORÍA
        // =====================================

        txtCategoria.setText(
                "🎯 " + e.categoriaPrincipal
        );

        // =====================================
        // 🔥 FECHA
        // =====================================

        txtFecha.setText(

                "📅 " +

                        e.fecha +

                        " ⏰ " +

                        e.hora
        );

        // =====================================
        // 🔥 DISTANCIA
        // =====================================

        txtDistancia.setText(
                "📍 Cerca de ti"
        );

        // =====================================
        // 🔥 TIPO
        // =====================================

        txtTipo.setText(
                "💰 " + e.tipoEvento
        );

        // =====================================
        // 🔥 INTERESADOS
        // =====================================

        txtInteresados.setText(

                "🔥 " +

                        e.interesados +

                        " interesados"
        );

        // =====================================
        // 🔥 VER MÁS
        // =====================================

        btnVerMas.setOnClickListener(v -> {

            mostrarDetalleEvento(e);

        });

        return row;
    }

    // =========================================
    // 🔥 MODAL EVENTO
    // =========================================

    private void mostrarDetalleEvento(Evento evento) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);

        View view =
                context.getLayoutInflater()
                        .inflate(
                                R.layout.dialog_evento,
                                null
                        );

        builder.setView(view);

        AlertDialog dialog =
                builder.create();

        // =====================================
        // 🔥 COMPONENTES
        // =====================================

        TextView txtTitulo =
                view.findViewById(R.id.txtTituloDetalle);

        TextView txtDescripcion =
                view.findViewById(R.id.txtDescripcionDetalle);

        TextView txtFecha =
                view.findViewById(R.id.txtFechaDetalle);

        TextView txtDireccion =
                view.findViewById(R.id.txtDireccionDetalle);

        TextView txtEtiquetas =
                view.findViewById(R.id.txtEtiquetasDetalle);

        TextView txtInteresados =
                view.findViewById(R.id.txtInteresadosDetalle);

        Button btnInteresa =
                view.findViewById(R.id.btnInteresa);

        Button btnUbicacion =
                view.findViewById(R.id.btnUbicacion);

        Button btnCerrar =
                view.findViewById(R.id.btnCerrar);

        // =====================================
        // 🔥 DATOS
        // =====================================

        txtTitulo.setText(
                evento.titulo
        );

        txtDescripcion.setText(
                evento.descripcion
        );

        txtFecha.setText(

                "📅 " +

                        evento.fecha +

                        " ⏰ " +

                        evento.hora
        );

        txtDireccion.setText(

                "📍 " +

                        evento.direccion
        );

        txtInteresados.setText(

                "🔥 " +

                        evento.interesados +

                        " interesados"
        );

        // =====================================
        // 🔥 ETIQUETAS LIMPIAS
        // =====================================

        StringBuilder etiquetasTexto =
                new StringBuilder();

        for (String tag : evento.etiquetas) {

            etiquetasTexto.append("• ");

            etiquetasTexto.append(tag);

            etiquetasTexto.append("\n");
        }

        txtEtiquetas.setText(
                etiquetasTexto.toString()
        );

        // =====================================
        // 🔥 BOTÓN INTERESA
        // =====================================

        btnInteresa.setOnClickListener(v -> {

            guardarInteres(evento);

        });

        // =====================================
        // 🔥 VER UBICACIÓN
        // =====================================

        btnUbicacion.setOnClickListener(v -> {

            String uri =

                    "google.navigation:q=" +

                            evento.lat +

                            "," +

                            evento.lng;

            Intent intent =
                    new Intent(

                            Intent.ACTION_VIEW,

                            Uri.parse(uri)
                    );

            intent.setPackage(
                    "com.google.android.apps.maps"
            );

            context.startActivity(intent);
        });

        // =====================================
        // 🔥 CERRAR
        // =====================================

        btnCerrar.setOnClickListener(v -> {

            dialog.dismiss();

        });

        dialog.show();
    }

    // =========================================
    // 🔥 GUARDAR INTERÉS
    // =========================================

    private void guardarInteres(Evento evento) {

        String url =
                "http://192.168.128.8:3000/interes-evento";

        JSONObject json =
                new JSONObject();

        try {

            json.put(
                    "usuarioCorreo",
                    correoUsuario
            );

            json.put(
                    "eventoId",
                    evento.id
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

                                    context,

                                    "Evento agregado a Mi Actividad 🔥",

                                    Toast.LENGTH_LONG
                            ).show();
                        },

                        error -> {

                            Toast.makeText(

                                    context,

                                    "Error guardando interés",

                                    Toast.LENGTH_LONG
                            ).show();
                        }
                );

        Volley.newRequestQueue(context)
                .add(request);
    }
}