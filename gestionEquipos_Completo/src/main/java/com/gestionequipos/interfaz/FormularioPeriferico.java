package com.gestionequipos.interfaz;

import com.gestionequipos.modelos.Periferico;
import com.gestionequipos.servicios.PerifericoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioPeriferico extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtSerial, txtTipo, txtMarca, txtModelo, txtProcesador, txtAnio, txtEstado, txtBuscar;
    private JTextArea txtObs;
    private JTable tabla;
    private DefaultTableModel modelo;
    private PerifericoService service = new PerifericoService();
    private int idSeleccionado = -1;

    public FormularioPeriferico() {
        setTitle("Gestión de Equipos");
        setSize(1100, 720);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Panel superior que contiene el formulario y botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- FORMULARIO ----------
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Registrar / Editar Equipo"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        txtSerial = new JTextField(15);
        txtTipo = new JTextField(15);
        txtMarca = new JTextField(15);
        txtModelo = new JTextField(15);
        txtProcesador = new JTextField(15);
        txtAnio = new JTextField(6);
        txtEstado = new JTextField(15);
        txtObs = new JTextArea(3, 30);
        JScrollPane obsScroll = new JScrollPane(txtObs);

        JTextField[] arr = {txtSerial, txtTipo, txtMarca, txtModelo, txtProcesador, txtAnio, txtEstado};
        String[] labels = {"Serial:", "Tipo:", "Marca:", "Modelo:", "Procesador:", "Año compra:", "Estado:"};

        for (int i = 0; i < labels.length; i++) {
            c.gridx = 0;
            c.gridy = i;
            pnlForm.add(new JLabel(labels[i]), c);
            c.gridx = 1;
            pnlForm.add(arr[i], c);
        }

        c.gridx = 0;
        c.gridy = 7;
        pnlForm.add(new JLabel("Observaciones:"), c);
        c.gridx = 1;
        pnlForm.add(obsScroll, c);

        // ---------- BOTONES ----------
        JPanel pnlBtns = new JPanel();
        pnlBtns.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlBtns.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnBuscar = new JButton("Buscar");
        txtBuscar = new JTextField(15);

        pnlBtns.add(btnGuardar);
        pnlBtns.add(btnActualizar);
        pnlBtns.add(btnEliminar);
        pnlBtns.add(btnLimpiar);
        pnlBtns.add(Box.createHorizontalStrut(20));
        pnlBtns.add(new JLabel("Buscar por serial:"));
        pnlBtns.add(txtBuscar);
        pnlBtns.add(btnBuscar);

        // Agregar los paneles al panel superior
        panelSuperior.add(pnlForm);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(pnlBtns);

        // ---------- TABLA ----------
        modelo = new DefaultTableModel(
            new String[]{"ID", "Serial", "Tipo", "Marca", "Modelo", "Proc.", "Año", "Estado", "Obs."}, 0
        );
        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Listado de Equipos"));
        scrollTabla.setPreferredSize(new Dimension(1000, 300));

        // Agregar al JFrame
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        getContentPane().add(scrollTabla, BorderLayout.CENTER);

        // ---------- EVENTOS ----------
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnBuscar.addActionListener(e -> buscar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int r = tabla.getSelectedRow();
            if (r >= 0) {
                idSeleccionado = (int) modelo.getValueAt(r, 0);
                txtSerial.setText((String) modelo.getValueAt(r, 1));
                txtTipo.setText((String) modelo.getValueAt(r, 2));
                txtMarca.setText((String) modelo.getValueAt(r, 3));
                txtModelo.setText((String) modelo.getValueAt(r, 4));
                txtProcesador.setText((String) modelo.getValueAt(r, 5));
                txtAnio.setText(modelo.getValueAt(r, 6).toString());
                txtEstado.setText((String) modelo.getValueAt(r, 7));
                txtObs.setText((String) modelo.getValueAt(r, 8));
            }
        });

        refrescarTabla();
        setVisible(true);
    }

    // Métodos existentes
    private void guardar() {
        try {
            Periferico p = new Periferico();
            p.setSerial(txtSerial.getText());
            p.setTipo(txtTipo.getText());
            p.setMarca(txtMarca.getText());
            p.setModelo(txtModelo.getText());
            p.setProcesador(txtProcesador.getText());
            p.setAnioCompra(Integer.parseInt(txtAnio.getText()));
            p.setEstado(txtEstado.getText());
            p.setObservaciones(txtObs.getText());
            service.registrar(p);
            refrescarTabla();
            limpiar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void actualizar() {
        try {
            if (idSeleccionado == -1) return;
            Periferico p = new Periferico();
            p.setId(idSeleccionado);
            p.setSerial(txtSerial.getText());
            p.setTipo(txtTipo.getText());
            p.setMarca(txtMarca.getText());
            p.setModelo(txtModelo.getText());
            p.setProcesador(txtProcesador.getText());
            p.setAnioCompra(Integer.parseInt(txtAnio.getText()));
            p.setEstado(txtEstado.getText());
            p.setObservaciones(txtObs.getText());
            service.actualizar(p);
            refrescarTabla();
            limpiar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminar() {
        if (idSeleccionado != -1) {
            service.eliminar(idSeleccionado);
            refrescarTabla();
            limpiar();
        }
    }

    private void limpiar() {
        idSeleccionado = -1;
        txtSerial.setText("");
        txtTipo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtProcesador.setText("");
        txtAnio.setText("");
        txtEstado.setText("");
        txtObs.setText("");
        tabla.clearSelection();
        txtBuscar.setText("");
    }

    private void refrescarTabla() {
        modelo.setRowCount(0);
        List<Periferico> list = service.listar();
        for (Periferico p : list) {
            modelo.addRow(new Object[]{
                p.getId(), p.getSerial(), p.getTipo(), p.getMarca(),
                p.getModelo(), p.getProcesador(), p.getAnioCompra(),
                p.getEstado(), p.getObservaciones()
            });
        }
    }

    private void buscar() {
        String serial = txtBuscar.getText().trim();
        if (serial.isEmpty()) {
            refrescarTabla();
        } else {
            modelo.setRowCount(0);
            List<Periferico> resultados = service.buscarPorSerial(serial);
            for (Periferico p : resultados) {
                modelo.addRow(new Object[]{
                    p.getId(), p.getSerial(), p.getTipo(), p.getMarca(),
                    p.getModelo(), p.getProcesador(), p.getAnioCompra(),
                    p.getEstado(), p.getObservaciones()
                });
            }
        }
    }
}


