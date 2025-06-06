package com.gestionequipos.interfaz;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    public VentanaPrincipal() {
        setTitle("Gestión de Equipos - Principal");
        setSize(550, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("GESTIÓN DE EQUIPOS Y MANTENIMIENTO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(52, 73, 94));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(new Color(236, 240, 241));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JButton btnEquipos = new JButton("Gestión de Equipos");
        JButton btnMant = new JButton("Mantenimientos");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        Color btnColor = new Color(41, 128, 185);

        for (JButton btn : new JButton[]{btnEquipos, btnMant}) {
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBackground(btnColor);
            btn.setForeground(Color.BLACK);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(new Dimension(300, 45));
            btn.setMaximumSize(new Dimension(300, 45));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            panelBotones.add(btn);
            panelBotones.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        btnEquipos.addActionListener(e -> {
            FormularioPeriferico fp = new FormularioPeriferico();
            fp.setVisible(true);
        });

        btnMant.addActionListener(e -> {
            FormularioMantenimiento fm = new FormularioMantenimiento();
            fm.setVisible(true);
        });

        add(panelBotones, BorderLayout.CENTER);
        setVisible(true);
    }
}

