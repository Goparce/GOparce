package com.example.goparce;

import android.app.Activity;
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

public class CuponAdapter extends ArrayAdapter<Cupon> {

    Activity context;

    ArrayList<Cupon> lista;

    String correoUsuario;

    public CuponAdapter(

            Activity context,

            ArrayList<Cupon> lista,

            String correoUsuario
    ) {

        super(
                context,
                R.layout.item_cupon,
                lista
        );

        this.context = context;

        this.lista = lista;

        this.correoUsuario = correoUsuario;
    }

    @Override
    public View getView(

            int position,

            View convertView,

            ViewGroup parent
    ) {

        LayoutInflater inflater =
                context.getLayoutInflater();

        View row =
                inflater.inflate(
                        R.layout.item_cupon,
                        null,
                        true
                );

        TextView nombre =
                row.findViewById(R.id.nombre);

        TextView descripcion =
                row.findViewById(R.id.descripcion);

        TextView codigo =
                row.findViewById(R.id.codigo);

        TextView info =
                row.findViewById(R.id.info);

        Button btnUsar =
                row.findViewById(R.id.btnUsar);

        Cupon c =
                lista.get(position);

        // =====================================
        // 🔥 DATOS
        // =====================================

        nombre.setText(
                "🎟 " + c.nombre
        );

        descripcion.setText(
                c.descripcion
        );

        codigo.setText(
                "Código: " + c.codigo
        );

        int restantes =
                c.cantidad - c.adquiridos;

        info.setText(

                "Disponibles: " +

                        restantes +

                        " / " +

                        c.cantidad
        );

        // =====================================
        // 🔥 AGOTADO
        // =====================================

        if (restantes <= 0) {

            btnUsar.setEnabled(false);

            btnUsar.setText("Agotado");
        }

        // =====================================
        // 🔥 USAR CUPÓN
        // =====================================

        btnUsar.setOnClickListener(v -> {

            String url =
                    "http://192.168.128.8:3000/usar-cupon";

            JSONObject json =
                    new JSONObject();

            try {

                json.put(
                        "correo",
                        correoUsuario
                );

                json.put(
                        "cuponId",
                        c.id
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

                                        "Cupón obtenido 🎉",

                                        Toast.LENGTH_SHORT
                                ).show();

                                c.adquiridos++;

                                notifyDataSetChanged();
                            },

                            error -> Toast.makeText(

                                    context,

                                    "Error usando cupón",

                                    Toast.LENGTH_SHORT
                            ).show()
                    );

            Volley.newRequestQueue(context)
                    .add(request);
        });

        return row;
    }
}