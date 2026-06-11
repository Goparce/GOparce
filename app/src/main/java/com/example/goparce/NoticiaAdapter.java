package com.example.goparce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoticiaAdapter extends ArrayAdapter<Noticia> {

    Activity context;

    ArrayList<Noticia> lista;

    public NoticiaAdapter(

            Activity context,

            ArrayList<Noticia> lista
    ) {

        super(
                context,
                R.layout.item_noticia,
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
                        R.layout.item_noticia,
                        null,
                        true
                );

        TextView titulo =
                row.findViewById(R.id.txtTituloNoticia);

        TextView descripcion =
                row.findViewById(R.id.txtDescripcionNoticia);

        TextView tipo =
                row.findViewById(R.id.txtTipoNoticia);

        Noticia n =
                lista.get(position);

        titulo.setText(
                "📰 " + n.titulo
        );

        descripcion.setText(
                n.descripcion
        );

        tipo.setText(
                "📌 " + n.tipo
        );

        return row;
    }
}