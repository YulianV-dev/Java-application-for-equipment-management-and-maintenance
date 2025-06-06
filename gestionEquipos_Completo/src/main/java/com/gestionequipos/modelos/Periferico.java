package com.gestionequipos.modelos;

public class Periferico {
    private int id;
    private String serial;
    private String tipo;
    private String marca;
    private String modelo;
    private String procesador;
    private int anioCompra;
    private String estado;
    private String observaciones;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSerial() { return serial; }
    public void setSerial(String serial) { this.serial = serial; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getProcesador() { return procesador; }
    public void setProcesador(String procesador) { this.procesador = procesador; }
    public int getAnioCompra() { return anioCompra; }
    public void setAnioCompra(int anioCompra) { this.anioCompra = anioCompra; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

