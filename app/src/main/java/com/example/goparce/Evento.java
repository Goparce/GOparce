package com.example.goparce;

import java.util.ArrayList;

public class Evento {

    // =====================================
    // 🔥 DATOS
    // =====================================

    public String id;

    public String titulo;

    public String descripcion;

    public String descripcionCorta;

    public String fecha;

    public String hora;

    public String direccion;

    public String tipoEvento;

    public int interesados;

    // =====================================
    // 🔥 UBICACIÓN
    // =====================================

    public double lat;

    public double lng;

    // =====================================
    // 🔥 ETIQUETAS
    // =====================================

    public ArrayList<String> etiquetas;

    // =====================================
    // 🔥 NUEVO
    // =====================================

    public String distancia;

    public String categoriaPrincipal;

    // =====================================
    // 🔥 CONSTRUCTOR
    // =====================================

    public Evento(

            String id,

            String titulo,

            String descripcion,

            String descripcionCorta,

            String fecha,

            String hora,

            String direccion,

            String tipoEvento,

            int interesados,

            double lat,

            double lng,

            ArrayList<String> etiquetas
    ) {

        this.id = id;

        this.titulo = titulo;

        this.descripcion = descripcion;

        this.descripcionCorta = descripcionCorta;

        this.fecha = fecha;

        this.hora = hora;

        this.direccion = direccion;

        this.tipoEvento = tipoEvento;

        this.interesados = interesados;

        this.lat = lat;

        this.lng = lng;

        this.etiquetas = etiquetas;

        // =====================================
        // 🔥 CATEGORÍA PRINCIPAL
        // =====================================

        if (etiquetas.size() > 0) {

            categoriaPrincipal =
                    etiquetas.get(0);

        } else {

            categoriaPrincipal =
                    "General";
        }

        // =====================================
        // 🔥 DISTANCIA
        // =====================================

        distancia =
                "Calculando...";
    }
}