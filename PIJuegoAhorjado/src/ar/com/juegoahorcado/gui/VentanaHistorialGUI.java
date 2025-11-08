package ar.com.juegoahorcado.gui;

import ar.com.juegoahorcado.domain.Partida;
import ar.com.juegoahorcado.domain.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaHistorialGUI extends JFrame {

    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(100, 180, 255);

    private Usuario usuario;

    public VentanaHistorialGUI(Usuario usuario) {
        this.usuario = usuario;
        setTitle("📜 Historial de Partidas – " + usuario.getNombreUsuario());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(COLOR_FONDO_OSCURO);

        // --- Título y Puntuación Total ---
        JLabel titulo = new JLabel("Historial de Partidas de " + usuario.getNombreUsuario(), JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(COLOR_TEXTO);

        JLabel puntuacion = new JLabel("Puntuación Total: " + usuario.getPuntuacionTotal(), JLabel.CENTER);
        puntuacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        puntuacion.setForeground(COLOR_TEXTO);

        JPanel panelArriba = new JPanel(new GridLayout(2, 1));
        panelArriba.setBackground(COLOR_FONDO_OSCURO);
        panelArriba.add(titulo);
        panelArriba.add(puntuacion);

        add(panelArriba, BorderLayout.NORTH);

        // --- Tabla del Historial ---
        String[] columnas = {"# Partida", "Palabra", "Resultado", "Puntos"};
        ArrayList<Partida> partidas = usuario.getRegistroPartidas();

        Object[][] datos = new Object[partidas.size()][4];
        for (int i = 0; i < partidas.size(); i++) {
            Partida p = partidas.get(i);
            datos[i][0] = i + 1;
            datos[i][1] = p.getPalabraAdivinada();
            datos[i][2] = p.getResultado() ? "GANADA" : "PERDIDA";
            datos[i][3] = p.getPuntos();
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setBackground(COLOR_FONDO_OSCURO.darker());
        tabla.setForeground(COLOR_TEXTO);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setBackground(COLOR_BOTON);
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(tabla);
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

    // --- Método de prueba rápido ---
    public static void main(String[] args) {
        // Usuario de ejemplo
        Usuario u = new Usuario("Ana", "Perez", 25, "ana@email.com", "ana25", "1234", ar.com.juegoahorcado.domain.Rol.JUGADOR);
        u.setPuntuacionTotal(120);
        u.registrarPartida(new Partida("JAVA", true, 50));
        u.registrarPartida(new Partida("SWING", false, 0));
        u.registrarPartida(new Partida("AHORCADO", true, 70));

        SwingUtilities.invokeLater(() -> new VentanaHistorialGUI(u));
    }
}
