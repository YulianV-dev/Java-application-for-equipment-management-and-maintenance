package com.gestionequipos.interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import com.gestionequipos.modelos.Mantenimiento;
import com.gestionequipos.servicios.MantenimientoService;

public class FormularioMantenimiento extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtIdEquipo, txtDescripcion, txtPersona, txtBuscar;
    private JComboBox<String> cbTipo;
    private JButton btnAgregar, btnCancelar, btnActualizar, btnLimpiar, btnBuscar, btnBorrar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private MantenimientoService servicio = new MantenimientoService();

    public FormularioMantenimiento() {
        setTitle("Gestión de Mantenimientos");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        Color fondo = new Color(245, 245, 245);
        Color azul = new Color(0, 120, 215);
        Color rojo = new Color(200, 50, 50);
        Color verde = new Color(34, 139, 34);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(fondo);


        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar / Editar Mantenimiento"));
        formulario.setBackground(fondo);

        JLabel lblIdEquipo = new JLabel("ID del Equipo:");
        JLabel lblTipo = new JLabel("Tipo:");
        JLabel lblDescripcion = new JLabel("Descripción:");
        JLabel lblPersona = new JLabel("Responsable:");

        txtIdEquipo = new JTextField();
        txtDescripcion = new JTextField();
        txtPersona = new JTextField();

        cbTipo = new JComboBox<>(new String[]{"Correctivo", "Preventivo"});

        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        for (JComponent comp : new JComponent[]{lblIdEquipo, lblTipo, lblDescripcion, lblPersona,
                txtIdEquipo, txtDescripcion, txtPersona, cbTipo}) {
            comp.setFont(fuente);
        }

        formulario.add(lblIdEquipo);
        formulario.add(txtIdEquipo);
        formulario.add(lblTipo);
        formulario.add(cbTipo);
        formulario.add(lblDescripcion);
        formulario.add(txtDescripcion);
        formulario.add(lblPersona);
        formulario.add(txtPersona);


        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(fondo);

        btnAgregar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnLimpiar = new JButton("Limpiar");
        btnCancelar = new JButton("Cerrar");

        for (JButton btn : new JButton[]{btnAgregar, btnActualizar, btnLimpiar, btnCancelar}) {
            btn.setFont(fuente);
            btn.setFocusPainted(false);
            btn.setForeground(Color.BLACK);
        }

        btnAgregar.setBackground(azul);
        btnActualizar.setBackground(verde);
        btnLimpiar.setBackground(new Color(100, 100, 100));
        btnCancelar.setBackground(rojo);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);


        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout(10, 10));
        panelSuperior.setBackground(fondo);
        panelSuperior.add(formulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);


        modelo = new DefaultTableModel(new String[]{"ID", "ID Equipo", "Tipo", "Descripción", "Responsable", "Fecha"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de Mantenimientos"));


        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBackground(fondo);

        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBorrar = new JButton("Eliminar");

        btnBuscar.setBackground(azul);
        btnBuscar.setForeground(Color.black);
        btnBorrar.setBackground(rojo);
        btnBorrar.setForeground(Color.BLACK);

        panelBusqueda.add(new JLabel("Buscar por ID Equipo:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnBorrar);


        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout(5, 5));
        panelInferior.setBackground(fondo);
        panelInferior.add(panelBusqueda, BorderLayout.NORTH);
        panelInferior.add(scroll, BorderLayout.CENTER);


        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelInferior, BorderLayout.CENTER);
        setContentPane(panelPrincipal);

        agregarEventos();
        cargarDatos();
    }

    private void agregarEventos() {
        btnAgregar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnCancelar.addActionListener(e -> dispose());
        btnBuscar.addActionListener(e -> buscar());
        btnBorrar.addActionListener(e -> borrar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtIdEquipo.setText(tabla.getValueAt(fila, 1).toString());
                cbTipo.setSelectedItem(tabla.getValueAt(fila, 2));
                txtDescripcion.setText(tabla.getValueAt(fila, 3).toString());
                txtPersona.setText(tabla.getValueAt(fila, 4).toString());
            }
        });
    }

    private void guardar() {
        try {
            int idEquipo = Integer.parseInt(txtIdEquipo.getText().trim());
            String tipo = cbTipo.getSelectedItem().toString();
            String descripcion = txtDescripcion.getText().trim();
            String persona = txtPersona.getText().trim();

            if (descripcion.isEmpty() || persona.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Mantenimiento m = new Mantenimiento();
            m.setIdEquipo(idEquipo);
            m.setTipo(tipo);
            m.setDescripcion(descripcion);
            m.setPersona(persona);

            servicio.registrar(m);
            JOptionPane.showMessageDialog(this, "Mantenimiento guardado exitosamente");
            limpiar();
            cargarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }


        JOptionPane.showMessageDialog(this, "Este módulo no permite actualizar mantenimientos (registro histórico inmodificable)", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void borrar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el mantenimiento seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            servicio.eliminar(id);
            cargarDatos();
            limpiar();
        }
    }

    private void buscar() {
        try {
            int idBuscar = Integer.parseInt(txtBuscar.getText().trim());
            modelo.setRowCount(0);
            for (Mantenimiento m : servicio.listar()) {
                if (m.getIdEquipo() == idBuscar) {
                    modelo.addRow(new Object[]{
                            m.getId(), m.getIdEquipo(), m.getTipo(), m.getDescripcion(), m.getPersona(), m.getFechaHora()
                    });
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        for (Mantenimiento m : servicio.listar()) {
            modelo.addRow(new Object[]{
                    m.getId(),
                    m.getIdEquipo(),
                    m.getTipo(),
                    m.getDescripcion(),
                    m.getPersona(),
                    m.getFechaHora()
            });
        }
    }

    private void limpiar() {
        txtIdEquipo.setText("");
        txtDescripcion.setText("");
        txtPersona.setText("");
        cbTipo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}

