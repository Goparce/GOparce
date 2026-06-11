package com.example.goparce;

public class Muro {

    public String id;

    public String titulo;

    public String descripcion;

    public String usuarioId;

    // =====================================
    // 🔥 NUEVOS DATOS
    // =====================================

    public int cantidadNoticias;

    public int cantidadServicios;

    public String fechaCreacion;

    // =====================================
    // 🔥 CONSTRUCTOR
    // =====================================

    public Muro(

            String id,

            String titulo,

            String descripcion,

            String usuarioId,

            int cantidadNoticias,

            int cantidadServicios,

            String fechaCreacion
    ) {

        this.id = id;

        this.titulo = titulo;

        this.descripcion = descripcion;

        this.usuarioId = usuarioId;

        this.cantidadNoticias = cantidadNoticias;

        this.cantidadServicios = cantidadServicios;

        this.fechaCreacion = fechaCreacion;
    }
}