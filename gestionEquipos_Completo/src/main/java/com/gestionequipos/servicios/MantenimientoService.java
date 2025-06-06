package com.gestionequipos.servicios;

import com.gestionequipos.conexion.ConexionBD;
import com.gestionequipos.modelos.Mantenimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoService {


    public void registrar(Mantenimiento m) {
        String sql = "INSERT INTO mantenimientos (id_equipo, tipo, descripcion, persona) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getIdEquipo());
            ps.setString(2, m.getTipo());
            ps.setString(3, m.getDescripcion());
            ps.setString(4, m.getPersona());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Mantenimiento> listar() {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos ORDER BY fecha_hora DESC";
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Mantenimiento m = new Mantenimiento();
                m.setId(rs.getInt("id"));
                m.setIdEquipo(rs.getInt("id_equipo"));
                m.setTipo(rs.getString("tipo"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setPersona(rs.getString("persona"));
                m.setFechaHora(rs.getTimestamp("fecha_hora"));
                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public void eliminar(int id) {
        String sql = "DELETE FROM mantenimientos WHERE id = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
