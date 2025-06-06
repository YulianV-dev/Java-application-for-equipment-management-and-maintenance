package com.gestionequipos.modelos;

import java.sql.Timestamp;

public class Mantenimiento {
    private int id;
    private int idEquipo;
    private String tipo;
    private String descripcion;
    private Timestamp fechaHora;
    private String persona;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }
    public String getPersona() { return persona; }
    public void setPersona(String persona) { this.persona = persona; }

}