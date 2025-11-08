
package ar.com.juegoahorcado.gui;

import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.repository.GestorUsuarios;
import ar.com.juegoahorcado.domain.Rol;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VentanaRankingGUI extends JFrame {

    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_HEADER = new Color(255, 193, 7); // Dorado para Ranking
    private static final Color COLOR_BOTON = new Color(100, 180, 255);

    public VentanaRankingGUI() {
        setTitle("🏆 Ranking de Jugadores");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_FONDO_OSCURO);

        // --- Título ---
        JLabel titulo = new JLabel("TOP JUGADORES (Puntuación Total)", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(COLOR_HEADER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // --- Obtener y Ordenar Usuarios ---
        // 🚨 Obtiene la lista, que ya está garantizada de no ser NULL por getUsuariosRegistrados()
        // (Utiliza métodos ya existentes en GestorUsuarios y la clase Rol)
// --- Obtener y Filtrar Usuarios desde el Repositorio (CORREGIDO) ---
// 1. Obtiene TODOS los usuarios (Administradores y Jugadores) desde la capa de repositorio.
        List<Usuario> todosLosUsuarios = GestorUsuarios.getTodosLosUsuarios();

// 2. Filtra la lista para incluir SOLO usuarios con el Rol de JUGADOR.
        List<Usuario> rankingList = new ArrayList<>();
        for (Usuario u : todosLosUsuarios) {
            if (u.getRol() == Rol.JUGADOR) {
                rankingList.add(u);
            }
        }

        // Manejo de lista vacía
        if (rankingList.isEmpty()) {
            JLabel mensaje = new JLabel("No hay usuarios registrados para el ranking.", JLabel.CENTER);
            mensaje.setFont(new Font("Segoe UI", Font.BOLD, 20));
            mensaje.setForeground(COLOR_TEXTO);
            add(mensaje, BorderLayout.CENTER);
            setVisible(true);
            return;
        }

        // Clonar y ordenar la lista (Usa getPuntuacionTotal, que ya confirmamos que existe)
        List<Usuario> usuariosOrdenados = new ArrayList<>(rankingList);
        Collections.sort(usuariosOrdenados, Comparator.comparing(Usuario::getPuntuacionTotal).reversed());

        // --- Configuración de la Tabla ---
        String[] columnas = {"Posición", "Nombre de Usuario", "Puntuación Total"};

        int maxFilas = usuariosOrdenados.size();
        Object[][] datos = new Object[maxFilas][3];

        for (int i = 0; i < maxFilas; i++) {
            Usuario u = usuariosOrdenados.get(i);
            datos[i][0] = i + 1;
            datos[i][1] = u.getNombreUsuario();
            datos[i][2] = u.getPuntuacionTotal();
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);

        // Estilo de la tabla
        tabla.setBackground(COLOR_FONDO_OSCURO.darker());
        tabla.setForeground(COLOR_TEXTO);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setBackground(COLOR_HEADER.darker());
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Renderizador para Resaltar Posiciones y centrar texto
        tabla.setDefaultRenderer(Object.class, new RankingCellRenderer());

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(COLOR_FONDO_OSCURO.darker());
        add(scroll, BorderLayout.CENTER);

        // --- Botón Cerrar ---
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setBackground(COLOR_BOTON);
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelAbajo = new JPanel();
        panelAbajo.setBackground(COLOR_FONDO_OSCURO);
        panelAbajo.add(btnCerrar);

        add(panelAbajo, BorderLayout.SOUTH);

        setVisible(true);
    }

    // --- Renderizador para Resaltar Posiciones ---
    private static class RankingCellRenderer extends DefaultTableCellRenderer {

        private static final Color COLOR_BASE = COLOR_FONDO_OSCURO.darker();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Alineación de texto
            if (column == 0 || column == 2) {
                setHorizontalAlignment(JLabel.CENTER);
            } else {
                setHorizontalAlignment(JLabel.LEFT);
            }

            // Colores especiales para el TOP 3
            if (row == 0) { // 1er Lugar
                c.setBackground(new Color(255, 215, 0, 100)); // Oro suave
            } else if (row == 1) { // 2do Lugar
                c.setBackground(new Color(192, 192, 192, 100)); // Plata suave
            } else if (row == 2) { // 3er Lugar
                c.setBackground(new Color(205, 127, 50, 100)); // Bronce suave
            } else {
                c.setBackground(COLOR_BASE);
            }

            c.setForeground(Color.WHITE);

            return c;
        }
    }
}

