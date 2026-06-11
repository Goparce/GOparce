package com.example.goparce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ServicioAdapter extends ArrayAdapter<Servicio> {

    Activity context;

    ArrayList<Servicio> lista;

    public ServicioAdapter(

            Activity context,

            ArrayList<Servicio> lista
    ) {

        super(
                context,
                R.layout.item_servicio,
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
                        R.layout.item_servicio,
                        null,
                        true
                );

        TextView titulo =
                row.findViewById(R.id.txtTituloServicio);

        TextView descripcion =
                row.findViewById(R.id.txtDescripcionServicio);

        TextView costo =
                row.findViewById(R.id.txtCostoServicio);

        TextView cupos =
                row.findViewById(R.id.txtCuposServicio);

        Servicio s =
                lista.get(position);

        titulo.setText(
                "🛠 " + s.titulo
        );

        descripcion.setText(
                s.descripcion
        );

        costo.setText(
                "💰 " + s.costo
        );

        cupos.setText(
                "📌 " + s.cupos
        );

        return row;
    }
}