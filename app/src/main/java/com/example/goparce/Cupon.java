package com.example.goparce;

public class Cupon {

    public String id;
    public String nombre;
    public String descripcion;
    public String codigo;
    public String valorPromo;
    public int cantidad;
    public int adquiridos;
    public int maxUso;

    public Cupon(String id, String nombre, String descripcion,
                 String codigo, String valorPromo,
                 int cantidad, int adquiridos, int maxUso) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.valorPromo = valorPromo;
        this.cantidad = cantidad;
        this.adquiridos = adquiridos;
        this.maxUso = maxUso;
    }
}