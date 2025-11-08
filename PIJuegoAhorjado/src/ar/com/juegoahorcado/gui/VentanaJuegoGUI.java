
package ar.com.juegoahorcado.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ar.com.juegoahorcado.domain.Palabra;
import ar.com.juegoahorcado.domain.Partida;
import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.logic.JuegoAhorcado;
import ar.com.juegoahorcado.repository.Diccionario;

/*
 * Función: Interfaz gráfica principal donde el usuario juega al Ahorcado.
 */

public class VentanaJuegoGUI extends JFrame {
    
    // --- Colores y fuentes ---
    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45);
    private static final Color COLOR_LETRA_NORMAL = Color.WHITE;
    private static final Color COLOR_LETRA_DESCUBIERTA = new Color(70, 210, 160); // Turquesa/Verde Principal
    private static final Color COLOR_LETRA_ERRONEA = new Color(255, 60, 60);
    private static final Color COLOR_PANEL_INFO = new Color(45, 45, 60);
    private static final Color COLOR_PISTA_AZUL = new Color(135, 206, 250);
    private static final Color COLOR_BOTON = new Color(70, 210, 160).darker(); // Turquesa/Verde Oscuro
    
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FUENTE_PALABRA = new Font("Consolas", Font.BOLD, 48);
    private static final Font FUENTE_INFO = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font FUENTE_INPUT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_PUNTUACION = new Font("Segoe UI", Font.BOLD, 20); 
    private static final Font FUENTE_PUNTUACION_TOTAL = new Font("Segoe UI", Font.BOLD, 24); 

    private static final int PUNTOS_GANAR = 10;
    private static final int PUNTOS_PERDER = -5;
    private static final int PUNTOS_META_FINAL = 1000;

    // --- Dependencias (juego NO es final) ---
    private final Usuario usuarioActual;
    private JuegoAhorcado juego; 

    // --- Componentes GUI ---
    private JLabel palabraOcultaLabel;
    private JLabel pistaLabel;
    private JLabel intentosRestantesLabel;
    private JTextField campoLetra;
    private PanelDibujoAhorcado panelDibujo; 
    
    // --- Componentes para Puntuación en Pantalla ---
    private JLabel lblPuntosTotal;
    private JLabel lblPuntosMeta;
    private JLabel lblUltimoTurno; 
    
    // Usaremos un panel para el último turno para controlar su altura y evitar el "salto"
    private JPanel panelUltimoTurno; 

    public VentanaJuegoGUI(Usuario usuario, JuegoAhorcado juego) {
        this.usuarioActual = usuario;
        this.juego = juego;

        setTitle("🎮 Ahorcado POO - Jugando como " + usuarioActual.getNombreUsuario());
        setSize(1200, 800); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(COLOR_FONDO_OSCURO);

        inicializarComponentes();
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
        actualizarPantalla();
        setVisible(true);
    }

    private void inicializarComponentes() {
        
        // --- Panel de Puntuación (OESTE/IZQUIERDA) ---
        JPanel panelPuntuacion = new JPanel();
        panelPuntuacion.setLayout(new BoxLayout(panelPuntuacion, BoxLayout.Y_AXIS));
        panelPuntuacion.setBackground(COLOR_PANEL_INFO);
        panelPuntuacion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_LETRA_DESCUBIERTA, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // **ESTABILIDAD: Se añade un tamaño fijo al panel lateral para evitar saltos**
        panelPuntuacion.setMinimumSize(new Dimension(250, 760));
        panelPuntuacion.setPreferredSize(new Dimension(250, 760));

        // Título del panel
        JLabel lblTituloPuntos = new JLabel("Puntuación del Jugador");
        lblTituloPuntos.setFont(FUENTE_PUNTUACION_TOTAL);
        lblTituloPuntos.setForeground(Color.WHITE);
        lblTituloPuntos.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Puntuación Total
        lblPuntosTotal = new JLabel("Total: " + usuarioActual.getPuntuacionTotal() + " pts");
        lblPuntosTotal.setFont(FUENTE_PUNTUACION_TOTAL.deriveFont(Font.BOLD, 36f));
        lblPuntosTotal.setForeground(COLOR_LETRA_DESCUBIERTA); 
        lblPuntosTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Puntos Restantes para Meta
        lblPuntosMeta = new JLabel("Meta: " + (PUNTOS_META_FINAL - usuarioActual.getPuntuacionTotal()) + " pts restantes");
        lblPuntosMeta.setFont(FUENTE_INFO);
        lblPuntosMeta.setForeground(Color.LIGHT_GRAY);
        lblPuntosMeta.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Separador visual
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.DARK_GRAY);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        // Resultados del Último Turno (Mantenemos en un panel para estabilidad)
        JLabel lblUltimoTurnoTitulo = new JLabel("Resultados de Turno");
        lblUltimoTurnoTitulo.setFont(FUENTE_PUNTUACION);
        lblUltimoTurnoTitulo.setForeground(Color.WHITE); 
        lblUltimoTurnoTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblUltimoTurno = new JLabel("¡Comienza la partida!");
        lblUltimoTurno.setFont(FUENTE_INFO.deriveFont(Font.ITALIC, 14f));
        lblUltimoTurno.setForeground(COLOR_LETRA_DESCUBIERTA); 
        lblUltimoTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Agregar componentes al panel de puntuación
        panelPuntuacion.add(lblTituloPuntos);
        panelPuntuacion.add(Box.createVerticalStrut(10));
        panelPuntuacion.add(lblPuntosTotal);
        panelPuntuacion.add(Box.createVerticalStrut(5));
        panelPuntuacion.add(lblPuntosMeta);
        panelPuntuacion.add(Box.createVerticalStrut(20));
        panelPuntuacion.add(separator);
        panelPuntuacion.add(Box.createVerticalStrut(15));
        panelPuntuacion.add(lblUltimoTurnoTitulo);
        panelPuntuacion.add(Box.createVerticalStrut(10));
        panelPuntuacion.add(lblUltimoTurno);
        panelPuntuacion.add(Box.createVerticalGlue()); // Empuja el contenido hacia arriba

        // --- Panel superior (NORTE) ---
        JPanel panelSuperior = new JPanel(new GridLayout(3, 1, 0, 5));
        panelSuperior.setBackground(COLOR_PANEL_INFO);
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("JUEGO DEL AHORCADO", SwingConstants.CENTER);
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_LETRA_DESCUBIERTA);

        pistaLabel = new JLabel("💡 Pista: " + juego.getPalabraSecreta().getPista(), SwingConstants.CENTER);
        pistaLabel.setFont(FUENTE_INFO);
        pistaLabel.setForeground(COLOR_PISTA_AZUL);

        intentosRestantesLabel = new JLabel("", SwingConstants.CENTER);
        intentosRestantesLabel.setFont(FUENTE_INFO);
        intentosRestantesLabel.setForeground(COLOR_LETRA_ERRONEA);

        panelSuperior.add(titulo);
        panelSuperior.add(pistaLabel);
        panelSuperior.add(intentosRestantesLabel);

        // --- Panel central (CENTRO) ---
        JPanel panelCentro = new JPanel(new BorderLayout(15, 15));
        panelCentro.setBackground(COLOR_FONDO_OSCURO);

        panelDibujo = new PanelDibujoAhorcado(JuegoAhorcado.MAX_INTENTOS); 
        panelDibujo.setBorder(BorderFactory.createLineBorder(COLOR_PANEL_INFO, 2));
        panelCentro.add(panelDibujo, BorderLayout.CENTER);

        palabraOcultaLabel = new JLabel(formatearPalabra(juego.getPalabraOculta()), SwingConstants.CENTER);
        palabraOcultaLabel.setFont(FUENTE_PALABRA);
        palabraOcultaLabel.setForeground(COLOR_LETRA_NORMAL);
        panelCentro.add(palabraOcultaLabel, BorderLayout.SOUTH);

        // --- Panel inferior (SUR) ---
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(COLOR_PANEL_INFO);
        panelInferior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JLabel lblLetra = new JLabel("Introduce una letra o palabra:");
        lblLetra.setFont(FUENTE_INFO);
        lblLetra.setForeground(Color.LIGHT_GRAY);

        campoLetra = new JTextField(10); 
        campoLetra.setFont(FUENTE_INPUT);
        campoLetra.setHorizontalAlignment(JTextField.CENTER);
        campoLetra.setForeground(COLOR_LETRA_DESCUBIERTA);
        campoLetra.setBackground(COLOR_FONDO_OSCURO);
        campoLetra.setBorder(BorderFactory.createLineBorder(COLOR_LETRA_DESCUBIERTA, 1));
        campoLetra.setCaretColor(Color.WHITE);

        JButton botonAdivinar = new JButton("Adivinar");
        botonAdivinar.setFont(FUENTE_BOTON);
        botonAdivinar.setPreferredSize(new Dimension(150, 45)); 
        botonAdivinar.setBackground(COLOR_BOTON);
        botonAdivinar.setForeground(Color.WHITE);
        botonAdivinar.setFocusPainted(false);
        
        JButton botonVolver = new JButton("Volver al Menú 🚪"); 
        botonVolver.setFont(FUENTE_BOTON.deriveFont(Font.PLAIN, 16f));
        botonVolver.setPreferredSize(new Dimension(200, 45));
        botonVolver.setBackground(COLOR_BOTON); 
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusPainted(false);
        botonVolver.addActionListener(e -> volverAMenuPrincipal());

        AdivinarListener listener = new AdivinarListener();
        botonAdivinar.addActionListener(listener);
        campoLetra.addActionListener(listener);

        panelInferior.add(lblLetra);
        panelInferior.add(campoLetra);
        panelInferior.add(botonAdivinar);
        panelInferior.add(botonVolver); 

        // Añadir todos los paneles al JFrame
        add(panelPuntuacion, BorderLayout.WEST); 
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void actualizarPantalla() {
        palabraOcultaLabel.setText(formatearPalabra(juego.getPalabraOculta()));

        String letrasProbadas = juego.getLetrasIntentadas().toString().replace("[", "").replace("]", "");

        if (juego.getIntentosRestantes() <= 2) {
            intentosRestantesLabel.setForeground(COLOR_LETRA_ERRONEA);
        } else {
            intentosRestantesLabel.setForeground(Color.YELLOW);
        }

        // Actualizar el panel de intentos
        String estadoHtml = String.format(
                "<html><div style='text-align: center; line-height: 1.5;'>"
                + "<p style='margin:0;'>🔥 INTENTOS RESTANTES: <b>%d / %d</b></p>"
                + "<p style='margin:0;'>Letras probadas: <b>%s</b></p>"
                + "</div></html>",
                juego.getIntentosRestantes(),
                JuegoAhorcado.MAX_INTENTOS,
                letrasProbadas.isEmpty() ? "Ninguna" : letrasProbadas
        );
        intentosRestantesLabel.setText(estadoHtml);
        
        // **ACTUALIZACIÓN CLAVE: Puntuación Total y Meta**
        lblPuntosTotal.setText("Total: " + usuarioActual.getPuntuacionTotal() + " pts");
        int puntosRestantes = PUNTOS_META_FINAL - usuarioActual.getPuntuacionTotal();
        lblPuntosMeta.setText("Meta: " + (puntosRestantes > 0 ? puntosRestantes : 0) + " pts restantes");
        if (puntosRestantes <= 0) {
            lblPuntosMeta.setForeground(COLOR_LETRA_DESCUBIERTA);
        } else if (puntosRestantes < 100) {
            lblPuntosMeta.setForeground(Color.ORANGE);
        } else {
            lblPuntosMeta.setForeground(Color.LIGHT_GRAY);
        }

        int erroresCometidos = JuegoAhorcado.MAX_INTENTOS - juego.getIntentosRestantes();
        panelDibujo.setErroresCometidos(erroresCometidos);
    }

    /**
     * Resetea y actualiza todos los componentes de la GUI con la nueva instancia de juego.
     */
    private void reiniciarVentana() {
        // Restaurar el estado de la GUI
        pistaLabel.setText("💡 Pista: " + this.juego.getPalabraSecreta().getPista());
        palabraOcultaLabel.setForeground(COLOR_LETRA_NORMAL); 
        campoLetra.setText(""); 
        campoLetra.setEnabled(true);
        campoLetra.requestFocusInWindow();
        
        // Resetear el estado visual del turno
        lblUltimoTurno.setText("¡Comienza la partida!");
        lblUltimoTurno.setForeground(COLOR_LETRA_DESCUBIERTA); 
        
        panelDibujo.setErroresCometidos(0); 
        actualizarPantalla();
        revalidate();
        repaint();
    }
    
    private void volverAMenuPrincipal() {
        dispose(); 
        new VentanaMenuPrincipalGUI(usuarioActual).setVisible(true); 
    }
    
    private void mostrarHistorialFinal() {
        volverAMenuPrincipal(); 
    }


    private void finalizarPartida() {
        campoLetra.setEnabled(false); 

        String palabraSecreta = juego.getPalabraSecreta().getPalabra();
        boolean esVictoria = juego.isJuegoGanado();
        int puntosObtenidos = esVictoria ? PUNTOS_GANAR : PUNTOS_PERDER;

        Partida partida = new Partida(palabraSecreta, esVictoria, puntosObtenidos);
        usuarioActual.registrarPartida(partida);

        usuarioActual.setPuntuacionTotal(usuarioActual.getPuntuacionTotal() + puntosObtenidos);

        palabraOcultaLabel.setForeground(esVictoria ? COLOR_LETRA_DESCUBIERTA : COLOR_LETRA_ERRONEA);
        if (!esVictoria) {
            palabraOcultaLabel.setText(formatearPalabra(palabraSecreta));
        }

        aplicarEstiloByteNineJOptionPane();
        
        // El JOptionPane se enfoca solo en el resultado del juego y la siguiente acción
        int opcion = JOptionPane.showConfirmDialog(this,
                (esVictoria ? "🎉 ¡Ganaste!" : "😢 Has perdido")
                + "\nPalabra: " + palabraSecreta
                + "\nPuntos obtenidos: " + puntosObtenidos
                + "\n¿Quieres jugar otra ronda?",
                esVictoria ? "Victoria" : "Derrota",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        restaurarEstiloJOptionPane();

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Palabra nuevaPalabra = Diccionario.obtenerPalabraAleatoria();
                this.juego = new JuegoAhorcado(usuarioActual, nuevaPalabra); 
                reiniciarVentana(); 
            } catch (Exception e) {
                 aplicarEstiloByteNineJOptionPane();
                 JOptionPane.showMessageDialog(this, "Error al iniciar nueva partida: " + e.getMessage(),
                      "Error", JOptionPane.ERROR_MESSAGE);
                 restaurarEstiloJOptionPane();
                 volverAMenuPrincipal(); 
            }
        } else {
            volverAMenuPrincipal();
        }
    }
    
    private class AdivinarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String entrada = campoLetra.getText().toUpperCase().trim();
            campoLetra.setText("");

            if (entrada.isEmpty()) {
                aplicarEstiloByteNineJOptionPane();
                JOptionPane.showMessageDialog(VentanaJuegoGUI.this, "La entrada no puede estar vacía.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                restaurarEstiloJOptionPane();
                return;
            }

            String mensajeResultado;
            if (entrada.length() == 1) {
                char letra = entrada.charAt(0);
                if (!Character.isLetter(letra)) {
                    aplicarEstiloByteNineJOptionPane();
                    JOptionPane.showMessageDialog(VentanaJuegoGUI.this, "Debes ingresar una letra válida (A-Z).", "Error de Entrada", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                mensajeResultado = juego.procesarLetra(letra);
            } else {
                mensajeResultado = juego.procesarPalabra(entrada);
            }

            // Actualización del panel lateral (feedback constante)
            if (mensajeResultado.contains("EXITO")) {
                lblUltimoTurno.setText("✅ Letra Correcta.");
                lblUltimoTurno.setForeground(COLOR_LETRA_DESCUBIERTA);
            } else if (mensajeResultado.contains("INCORRECTO")) {
                lblUltimoTurno.setText("❌ Incorrecto. Pierdes un intento."); 
                lblUltimoTurno.setForeground(COLOR_LETRA_ERRONEA);
            } else if (mensajeResultado.contains("ADVERTENCIA")) {
                lblUltimoTurno.setText("⚠️ Ya probaste esa letra.");
                lblUltimoTurno.setForeground(Color.ORANGE); 
            } else {
                lblUltimoTurno.setText(mensajeResultado);
                lblUltimoTurno.setForeground(Color.WHITE);
            }
            
            actualizarPantalla();

            if (juego.juegoTerminado()) {
                finalizarPartida();
            } else {
                // RESTAURADO: Muestra el feedback de JOptionPane por turno
                aplicarEstiloByteNineJOptionPane();
                JOptionPane.showMessageDialog(VentanaJuegoGUI.this,
                            mensajeResultado.replace("ADVERTENCIA: ", "⚠️ ")
                                    .replace("EXITO: ", "✅ ")
                                    .replace("INCORRECTO: ", "❌ "),
                            "Resultado del Turno", JOptionPane.INFORMATION_MESSAGE);
                restaurarEstiloJOptionPane();
            }
        }
    }

    private String formatearPalabra(String palabra) {
        return palabra.replace("", " ").trim();
    }

    // --- MÉTODOS DE ESTILO DE JOPTIONPANE RESTAURADOS Y MANTENIDOS ---
    private void aplicarEstiloByteNineJOptionPane() {
        UIManager.put("OptionPane.background", COLOR_FONDO_OSCURO);
        UIManager.put("Panel.background", COLOR_FONDO_OSCURO);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("OptionPane.font", FUENTE_INFO);
        UIManager.put("Button.background", COLOR_BOTON);
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
    
}
