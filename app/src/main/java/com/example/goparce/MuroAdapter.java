package com.example.goparce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MuroAdapter extends ArrayAdapter<Muro> {

    Activity context;

    ArrayList<Muro> lista;

    public MuroAdapter(
            Activity context,
            ArrayList<Muro> lista
    ) {

        super(
                context,
                R.layout.item_muro,
                lista
        );

        this.context = context;

        this.lista = lista;
    }

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
                        R.layout.item_muro,
                        null,
                        true
                );

        // =====================================
        // 🔥 COMPONENTES
        // =====================================

        TextView titulo =
                row.findViewById(R.id.txtTitulo);

        TextView descripcion =
                row.findViewById(R.id.txtDescripcion);

        TextView publicaciones =
                row.findViewById(R.id.txtPublicaciones);

        TextView fecha =
                row.findViewById(R.id.txtFecha);

        TextView verMuro =
                row.findViewById(R.id.txtVerMuro);

        // =====================================
        // 🔥 MURO
        // =====================================

        Muro m =
                lista.get(position);

        // =====================================
        // 🔥 DESCRIPCIÓN CORTA
        // =====================================

        String descripcionCorta =
                m.descripcion.length() > 90

                        ? m.descripcion.substring(0, 90) + "..."

                        : m.descripcion;

        // =====================================
        // 🔥 DATOS
        // =====================================

        titulo.setText(
                "🎨 " + m.titulo
        );

        descripcion.setText(
                descripcionCorta
        );

        publicaciones.setText(

                "📰 Noticias: " + m.cantidadNoticias +

                        "\n" +

                        "🛠 Servicios: " + m.cantidadServicios
        );

        fecha.setText(
                "📅 " + m.fechaCreacion
        );

        verMuro.setText(
                "➡ Ver muro"
        );

        return row;
    }
}