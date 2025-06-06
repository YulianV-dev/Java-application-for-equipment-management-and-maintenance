package com.gestionequipos.servicios;

import com.gestionequipos.conexion.ConexionBD;
import com.gestionequipos.modelos.Periferico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerifericoService {

    public void registrar(Periferico p) {
        String sql = "INSERT INTO equipos (serial, tipo, marca, modelo, procesador, anio_compra, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getSerial());
            stmt.setString(2, p.getTipo());
            stmt.setString(3, p.getMarca());
            stmt.setString(4, p.getModelo());
            stmt.setString(5, p.getProcesador());
            stmt.setInt(6, p.getAnioCompra());
            stmt.setString(7, p.getEstado());
            stmt.setString(8, p.getObservaciones());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Periferico> listar() {
        List<Periferico> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos";
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Periferico p = new Periferico();
                p.setId(rs.getInt("id"));
                p.setSerial(rs.getString("serial"));
                p.setTipo(rs.getString("tipo"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setProcesador(rs.getString("procesador"));
                p.setAnioCompra(rs.getInt("anio_compra"));
                p.setEstado(rs.getString("estado"));
                p.setObservaciones(rs.getString("observaciones"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizar(Periferico p) {
        String sql = "UPDATE equipos SET serial=?, tipo=?, marca=?, modelo=?, procesador=?, anio_compra=?, estado=?, observaciones=? WHERE id=?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getSerial());
            stmt.setString(2, p.getTipo());
            stmt.setString(3, p.getMarca());
            stmt.setString(4, p.getModelo());
            stmt.setString(5, p.getProcesador());
            stmt.setInt(6, p.getAnioCompra());
            stmt.setString(7, p.getEstado());
            stmt.setString(8, p.getObservaciones());
            stmt.setInt(9, p.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM equipos WHERE id=?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Periferico> buscarPorSerial(String serial) {
        List<Periferico> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipos WHERE serial LIKE ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + serial + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Periferico p = new Periferico();
                p.setId(rs.getInt("id"));
                p.setSerial(rs.getString("serial"));
                p.setTipo(rs.getString("tipo"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setProcesador(rs.getString("procesador"));
                p.setAnioCompra(rs.getInt("anio_compra"));
                p.setEstado(rs.getString("estado"));
                p.setObservaciones(rs.getString("observaciones"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

} 

