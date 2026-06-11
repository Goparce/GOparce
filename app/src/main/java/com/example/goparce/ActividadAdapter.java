package com.example.goparce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ActividadAdapter extends ArrayAdapter<Evento> {

    Activity context;

    ArrayList<Evento> lista;

    public ActividadAdapter(
            Activity context,
            ArrayList<Evento> lista
    ) {

        super(
                context,
                R.layout.item_actividad,
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
                        R.layout.item_actividad,
                        null,
                        true
                );

        // =====================================
        // 🔥 COMPONENTES
        // =====================================

        TextView titulo =
                row.findViewById(R.id.titulo);

        TextView descripcion =
                row.findViewById(R.id.descripcion);

        TextView fecha =
                row.findViewById(R.id.fecha);

        // =====================================
        // 🔥 EVENTO
        // =====================================

        Evento e =
                lista.get(position);

        // =====================================
        // 🔥 DATOS
        // =====================================

        titulo.setText(
                "🎉 " + e.titulo
        );

        fecha.setText(
                "📅 " + e.fecha +
                        " ⏰ " + e.hora
        );

        descripcion.setText(

                "💰 " + e.tipoEvento +

                        "\n\n" +

                        e.descripcionCorta
        );

        return row;
    }
}