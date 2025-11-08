package ar.com.juegoahorcado.gui;

import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.domain.Palabra;
import ar.com.juegoahorcado.logic.JuegoAhorcado;
import ar.com.juegoahorcado.repository.Diccionario;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Ventana Central que aparece después de que un usuario (Jugador o Admin)
 * inicia sesión correctamente.
 */
public class VentanaMenuPrincipalGUI extends JFrame {

    private Usuario usuarioActual;
    
    // Colores de tu paleta
    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45);
    private static final Color COLOR_BOTON = new Color(100, 180, 255);
    private static final Color COLOR_HEADER = new Color(255, 193, 7);
    
    // Estilos de JOptionPane (Centralizados aquí)
    private static final Color COLOR_BOTON_JOPTION = new Color(70, 210, 160).darker();
    private static final Font FUENTE_INFO = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 18);
    
    private static final Dimension TAMANO_BOTON = new Dimension(280, 50);
    
    private static final String[] FRASES_MEME = {
        // ... (Tu lista de frases se mantiene aquí)
        "¡A codear se ha dicho! Pero primero, el menú. 😉",
        "Tu código me recuerda a la Capilla Sixtina. ¡Juega!",
        "La vida es corta. Depura rápido. 🏃‍♂️",
        "¡Hola! Espero que tu próximo commit sea perfecto. 🤞",
        "El café funciona si le pones código. ¡A jugar!",
        "Hoy es un buen día para que NO te ahorquen. 🥳",
        "¿Ganar? ¡Es pan comido! (Si no te falta la 'A').",
        "Dale, que el 'null pointer' puede esperar. 🧘",
        "No hagas que tu CPU se ponga triste. Juega bien. 🤖"
    };

    public VentanaMenuPrincipalGUI(Usuario usuario) {
        this.usuarioActual = usuario;
        
        setTitle("Menú Principal | Jugador: " + usuario.getNombreUsuario());
        setSize(450, 550);
        setLocationRelativeTo(null);
        // Cambiamos el comportamiento por defecto al presionar X para que solo oculte
        // Si queremos que al cerrar la X se mantenga el comportamiento de volver a inicio,
        // deberíamos redefinir el WindowListener, pero por ahora mantenemos EXIT_ON_CLOSE.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        getContentPane().setBackground(COLOR_FONDO_OSCURO);

        // --- Estructura y Componentes (Se mantienen igual) ---

        // Contenedor Principal
        JPanel panelContenedor = new JPanel(new BorderLayout(0, 20));
        panelContenedor.setBackground(COLOR_FONDO_OSCURO);
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        panelHeader.setBackground(COLOR_FONDO_OSCURO);
        
        JLabel lblBienvenida = new JLabel("¡Hola, " + usuario.getNombreUsuario() + "!", JLabel.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(COLOR_HEADER);
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblMeme = new JLabel(obtenerFraseMeme(), JLabel.CENTER);
        lblMeme.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblMeme.setForeground(Color.LIGHT_GRAY);
        lblMeme.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelHeader.add(lblBienvenida);
        panelHeader.add(Box.createVerticalStrut(10));
        panelHeader.add(lblMeme);

        panelContenedor.add(panelHeader, BorderLayout.NORTH);

        // Panel de Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO_OSCURO);
        
        int separacion = 15;

        // Creación de Botones
        JButton btnJugar = crearBoton("JUGAR AHORCADO 🕹️");
        JButton btnRanking = crearBoton("VER RANKING 🏆");
        JButton btnHistorial = crearBoton("VER HISTORIAL 📜");
        JButton btnVolverInicio = crearBoton("CERRAR SESIÓN (Volver a Inicio) 🚪");

        // Añadir Botones
        panelBotones.add(Box.createVerticalStrut(separacion));
        panelBotones.add(centrarBoton(btnJugar));
        panelBotones.add(Box.createVerticalStrut(separacion));
        panelBotones.add(centrarBoton(btnRanking));
        panelBotones.add(Box.createVerticalStrut(separacion));
        panelBotones.add(centrarBoton(btnHistorial));
        panelBotones.add(Box.createVerticalStrut(separacion * 2));
        panelBotones.add(centrarBoton(btnVolverInicio));
        
        panelContenedor.add(panelBotones, BorderLayout.CENTER);

        add(panelContenedor);
        
        // --- Lógica de los Botones (Listeners) ---
        btnJugar.addActionListener(e -> abrirVentanaJuego());
        btnRanking.addActionListener(e -> abrirVentanaRanking());
        btnHistorial.addActionListener(e -> abrirVentanaHistorial());
        btnVolverInicio.addActionListener(e -> volverAInicio()); // Llama al método corregido

        setVisible(true);
    }
    
    // --- MÉTODOS DE ESTILO PARA JOPTIONPANE ---

    private void aplicarEstiloJOptionPane() {
        UIManager.put("OptionPane.background", COLOR_FONDO_OSCURO);
        UIManager.put("Panel.background", COLOR_FONDO_OSCURO);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("OptionPane.font", FUENTE_INFO);
        UIManager.put("Button.background", COLOR_BOTON_JOPTION);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", FUENTE_BOTON.deriveFont(Font.PLAIN, 14f));
        UIManager.put("Button.border", BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    private void restaurarEstiloJOptionPane() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.font", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.font", null);
        UIManager.put("Button.border", null);
    }
    
    // --- Métodos de Navegación ---
    
    private void abrirVentanaJuego() {
        try {
            Palabra palabraSecreta = Diccionario.obtenerPalabraAleatoria();
            
            if (palabraSecreta == null) {
                aplicarEstiloJOptionPane();
                JOptionPane.showMessageDialog(this, 
                    "❌ Error: No se pudo cargar ninguna palabra del diccionario. Imposible iniciar el juego.",
                    "Error de Juego", JOptionPane.ERROR_MESSAGE);
                restaurarEstiloJOptionPane();
                return;
            }
            
            JuegoAhorcado motorJuego = new JuegoAhorcado(usuarioActual, palabraSecreta);
            
            this.dispose();
            new VentanaJuegoGUI(usuarioActual, motorJuego).setVisible(true);

        } catch (Exception ex) {
            aplicarEstiloJOptionPane();
            JOptionPane.showMessageDialog(this, 
                "Error al iniciar el juego: " + ex.getMessage() + "\n(Verifique que Diccionario y JuegoAhorcado existan y funcionen).",
                "Error fatal de Juego", JOptionPane.ERROR_MESSAGE);
            restaurarEstiloJOptionPane();
        }
    }
    
    private void abrirVentanaRanking() {
        // Asumo que VentanaRankingGUI solo necesita ser visible, no reemplaza el menú.
        new VentanaRankingGUI().setVisible(true); 
    }
    
    private void abrirVentanaHistorial() {
        this.dispose();
        new VentanaHistorialGUI(usuarioActual).setVisible(true); 
    }
    
    /**
     * CORRECCIÓN: Cierra la ventana actual y abre la VentanaInicioGUI.
     */
    private void volverAInicio() {
        aplicarEstiloJOptionPane();
        
        // Usamos un JOptionPane de confirmación antes de cerrar la sesión
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro de que quieres cerrar la sesión?", 
            "Cerrar Sesión", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        restaurarEstiloJOptionPane();

        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose(); 
            // CORRECCIÓN APLICADA: Descomentamos y ejecutamos la creación de la ventana de inicio.
            new VentanaInicioGUI().setVisible(true); // Asumiendo que esta es tu clase de Login/Registro
        }
    }
    
    // --- Métodos auxiliares ---

    private String obtenerFraseMeme() {
        Random rand = new Random();
        int indice = rand.nextInt(FRASES_MEME.length);
        return FRASES_MEME[indice];
    }
    
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(TAMANO_BOTON);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
    
    private JPanel centrarBoton(JButton boton) {
        JPanel wrapper = new JPanel();
        wrapper.setBackground(COLOR_FONDO_OSCURO);
        wrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(boton);
        return wrapper;
    }
}