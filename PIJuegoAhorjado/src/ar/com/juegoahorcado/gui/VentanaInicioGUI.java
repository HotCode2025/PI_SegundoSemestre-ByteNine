
package ar.com.juegoahorcado.gui;

import javax.swing.*;// Importa clases esenciales de Swing (JFrame, JPanel, JButton, etc.)
import java.awt.*;// Importa clases de AWT para gráficos, colores y fuentes
import java.awt.event.ActionListener;// Importa la interfaz para manejar clics de botones
import java.awt.geom.RoundRectangle2D;// Importa la clase para dibujar rectángulos redondeados

public class VentanaInicioGUI extends JFrame{
 /**
 * Clase: VentanaInicioGUI
 * Va a representa la pantalla principal de bienvenida del modo gráfico del juego Ahorjado.
 * Desde aquí el usuario puede iniciar sesión, registrarse o salir del juego.
 * * Versión mejorada con estética profesional, botones centrados, colores distintivos y tema oscuro.
 */
    // --- Definiciones de Estilo y Colores (Modo Oscuro Programador) ---
    // Colores base para la interfaz de tema oscuro, similar a un entorno de desarrollo (IDE).
    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45); // Gris oscuro/Azul medianoche para fondo
    private static final Color COLOR_TITULO = new Color(220, 220, 255); // Blanco/Azul claro para texto principal
    private static final Color COLOR_TEXTO_CLARO = Color.WHITE; // Color general del texto

   // Colores hexadecimales para el nombre del equipo "ByteNine" (simulando colores de sintaxis Java).
    private static final String COLOR_JAVA_NARANJA = "#FF8000"; // Naranja de Java para "BYTE"
    private static final String COLOR_JAVA_AZUL_CLARO = "#5BC0DE";// Azul claro para "NINE" (contraste)
// Fuentes para la interfaz.
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 36);
    private static final Font FUENTE_FRASES = new Font("Verdana", Font.ITALIC, 18);
    private static final Color COLOR_FRASES_INSPIRACION = new Color(0, 255, 255); // Cian brillante

    private static final Font FUENTE_BOTONES = new Font("Segoe UI", Font.BOLD, 18);

    // Colores Distintivos para los 3 Botones (Ajustados al fondo oscuro)
    private static final Color COLOR_PRIMARIO = new Color(100, 180, 255);   // Azul claro (Login)
    private static final Color COLOR_SECUNDARIO = new Color(70, 210, 160); // Verde menta (Registro)
    private static final Color COLOR_PELIGRO = new Color(255, 120, 120);    // Rojo suave (Salir)

     // ------------------- CONSTRUCTOR -------------------
    public VentanaInicioGUI() {
         // --- Configuración básica del JFrame ---
        setTitle("🎮 Ahorcado Visual – ByteNine Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Cierra la aplicación al cerrar la ventana.
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza la ventana para aprovechar el espacio.
        setLocationRelativeTo(null);// Centra la ventana en la pantalla (aunque esté maximizada, es buena práctica).

        // --- Panel Principal (Contenedor base) ---
        // Usa BorderLayout para dividir la ventana en Norte (título), Centro (contenido) y Sur.
        JPanel panelPrincipal = new JPanel(new BorderLayout(40, 40)); 
        panelPrincipal.setBackground(COLOR_FONDO_OSCURO); // Nuevo fondo oscuro
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); 

        // ---------- Título Superior (ByteNine con colores Java) ----------
        // Se usa HTML para aplicar diferentes colores a partes del texto
        // Se usa COLOR_JAVA_AZUL_CLARO para "NINE"
        String tituloTexto = String.format(
            "<html><div style='text-align: center; color: %s;'>🧠 AHORCADO – <span style='color:%s;'>BYTE</span><span style='color:%s;'>NINE</span> EDITION</div></html>",
            toHex(COLOR_TITULO), COLOR_JAVA_NARANJA, COLOR_JAVA_AZUL_CLARO);
        
        JLabel titulo = new JLabel(tituloTexto, JLabel.CENTER);
        titulo.setFont(FUENTE_TITULO);
        // El color ya se define en el HTML
        panelPrincipal.add(titulo, BorderLayout.NORTH);// Coloca el título en la parte superior.

        // -- Panel Central: Imagen y Botones ----
        // Usa GridLayout(1, 2) para dividir el centro en dos columnas iguales 
        //(Imagen a la izquierda, Botones a la derecha).
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 40, 0)); // Divide el centro en dos columnas
        panelCentro.setBackground(COLOR_FONDO_OSCURO);

         // A. Panel de la Imagen/Gráfico (Izquierda)
        // Instancia la clase interna para dibujar la horca personalizada.
        JPanel panelImagenAhorcado = new PanelAhorcadoPersonalizado();
        panelCentro.add(panelImagenAhorcado);
        
      // B. Panel de Contenido (Mensaje y Botones) (Derecha)
        // Usa GridBagLayout para centrar verticalmente la frase y el panel de botones.
        JPanel panelContenidoDerecha = new JPanel(new GridBagLayout());
        panelContenidoDerecha.setBackground(COLOR_FONDO_OSCURO);
        GridBagConstraints gbc = new GridBagConstraints();// Objeto para configurar la posición en GridBagLayout.

        // 1. Frase Motivacional (Color de inspiración)
        String fraseTexto = String.format(
            "<html><div style='text-align: center; color: %s;'>💬 Aprender a programar es colgarse...<br>¡pero del conocimiento!</div></html>",
            toHex(COLOR_FRASES_INSPIRACION));

        JLabel frase = new JLabel(fraseTexto, JLabel.CENTER);
        frase.setFont(FUENTE_FRASES);
         // Configuración para la frase (fila 0, centrado, con margen inferior)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0); 
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenidoDerecha.add(frase, gbc);

        // 2. Panel que contiene los 3 Botones
        // Usa BoxLayout (Y_AXIS) para apilar los botones verticalmente.
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS)); 
        panelBotones.setOpaque(false); // Hace que el fondo del panel sea transparente (igual al padre).
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50)); 
        
   // --- Creación de los Botones ---
        // Llama al método auxiliar 'crearBoton' y define su acción con expresiones lambda.
        JButton btnLogin = crearBoton("🔑 Iniciar Sesión", COLOR_PRIMARIO, e -> {
            this.dispose(); // Cierra la ventana actual.
             new VentanaLoginGUI().setVisible(true); // Abre la ventana inicio o inicia sesion directamente si ya esta registrado
        });

        JButton btnRegistro = crearBoton("📝 Registrarse", COLOR_SECUNDARIO, e -> {
            this.dispose(); // Cierra la ventana actual. 
             new VentanaRegistroGUI().setVisible(true); // Abre la ventana de Registro.
        });

        JButton btnSalir = crearBoton("🚪 Salir del Juego", COLOR_PELIGRO, e -> {
            // Muestra un diálogo de confirmación antes de salir.
            int confirmar = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que quieres salir?", 
                "Salir", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            if (confirmar == JOptionPane.YES_OPTION) {
                dispose(); // Cierra la ventana.
                System.exit(0); // Finaliza la aplicación.
            }
        });

        // Añadir botones al panel de botones, separándolos con espacio vertical fijo (struts).
        panelBotones.add(btnLogin);
        panelBotones.add(Box.createVerticalStrut(20)); 
        panelBotones.add(btnRegistro);
        panelBotones.add(Box.createVerticalStrut(20)); 
        panelBotones.add(btnSalir);
        // Configuración para el panel de botones (fila 1, centrado)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); 
        gbc.anchor = GridBagConstraints.CENTER;
        panelContenidoDerecha.add(panelBotones, gbc);// Agrega el panel de botones al panel derecho.
        
        panelCentro.add(panelContenidoDerecha);// Agrega el panel derecho al panel central.

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);// Coloca el contenido central en el medio.

        // Agrega el panel principal al JFrame
        add(panelPrincipal);
        setVisible(true); // Hace visible la ventana.
    }
    
    
        // ------- MÉTODOS AUXILIARES ------
    
    
      //Convierte un objeto Color a su representación hexadecimal String (Ej: #RRGGBB).
     // Esto es necesario para usar colores en el código HTML de los JLabels.
     
   
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

 
       
     // Método auxiliar para crear botones con un estilo unificado y color personalizado.
     //Define tamaño fijo, fuente, cursor y un borde compuesto para un aspecto pulido.
    private JButton crearBoton(String texto, Color colorFondo, ActionListener listener) {
        JButton button = new JButton(texto);
        button.setBackground(colorFondo);
        button.setForeground(Color.BLACK); // Texto negro para mejor contraste con colores claros de fondo
        button.setFont(FUENTE_BOTONES);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));// Elimina el recuadro de foco al hacer clic.
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  // Necesario para BoxLayout (centra horizontalmente).

        // Tamaño y borde(fijo para uniformidad)
        button.setPreferredSize(new Dimension(280, 60));
        button.setMinimumSize(new Dimension(280, 60));
        button.setMaximumSize(new Dimension(280, 60));
        // Borde estilizado (línea alrededor con el color oscuro).
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.addActionListener(listener);// Asigna la acción definida por la lambda.
        return button;
    }

    
    
    // --------CLASE INTERNA (Dibujo) ----------
    
    /*
    *Clase interna para dibujar el Ahorcado (una representación conceptual).
     Extiende JPanel y sobrescribe paintComponent para el dibujo personalizado.
     */
     
    private class PanelAhorcadoPersonalizado extends JPanel {
        
        public PanelAhorcadoPersonalizado() {
            // El borde y el fondo ahora coinciden con el fondo oscuro de la ventana
            setBorder(BorderFactory.createEmptyBorder()); 
            setBackground(COLOR_FONDO_OSCURO); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
             // Habilita el suavizado de bordes para líneas y formas (Anti-aliasing).
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Dibujar Ahorcado estilo Programador (una estructura de código)
            
            // 1. Base (Console/IDE Base)
            g2d.setColor(new Color(60, 60, 75)); // Gris oscuro más claro que el fondo
            g2d.fill(new RoundRectangle2D.Double(w * 0.1, h * 0.85, w * 0.8, h * 0.05, 10, 10));

             // 2. Columna (Simulando un Stack Trace o barra vertical)
            g2d.setColor(new Color(100, 100, 120)); // Gris medio
            g2d.fillRect((int)(w * 0.2), (int)(h * 0.1), (int)(w * 0.03), (int)(h * 0.75));

            // 3. LA J DE JAVA COMO HORCA
            // La "J" de Java es un arco simple con un punto.
            int jX = (int)(w * 0.45);
            int jY = (int)(h * 0.2);
            int jWidth = (int)(w * 0.3);
            int jHeight = (int)(h * 0.3);

            g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(new Color(255, 165, 0)); // Naranja Java (Horca)
            
            // Arco de la J(similar a un gancho
            g2d.drawArc(jX, jY, jWidth, jHeight, 0, -180);
            
            // Línea vertical que baja
            // Dibuja la línea vertical superior de la J.
            g2d.drawLine(jX + jWidth, jY + jHeight / 2, jX + jWidth, (int)(h * 0.15));

            // El cuerpo del ahorcado cuelga del punto más alto de la J (la esquina superior derecha)
            int puntoSujecionX = jX + jWidth;
            int puntoSujecionY = (int)(h * 0.15);

            // 4. Cuerda (Simulando un Breakpoint o Error)
            g2d.setColor(new Color(255, 60, 60)); // Rojo vivo (Error/Breakpoint)
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(puntoSujecionX, puntoSujecionY, puntoSujecionX, (int)(h * 0.25));

             // 5. La Figura (El Programador "Colgado")
            int cabezaY = (int)(h * 0.25);
            int cabezaTam = (int)(w * 0.08);

            g2d.setColor(new Color(100, 180, 255)); // Azul Primario (Claro para el fondo oscuro)
            
            // Cabeza (Ahora es la cabeza del programador, no el ícono)
            g2d.setStroke(new BasicStroke(5));
            g2d.drawOval((int)(puntoSujecionX - cabezaTam / 2), cabezaY, cabezaTam, cabezaTam);

            // Cuerpo (El Código)
            g2d.setStroke(new BasicStroke(5));
            int cuerpoY = cabezaY + cabezaTam;
            g2d.drawLine(puntoSujecionX, cuerpoY, puntoSujecionX, (int)(h * 0.6));
            
            // Brazos (simulando atajos de teclado o frustración)
            g2d.drawLine(puntoSujecionX, (int)(h * 0.4), (int)(puntoSujecionX - w * 0.05), (int)(h * 0.5));
            g2d.drawLine(puntoSujecionX, (int)(h * 0.4), (int)(puntoSujecionX + w * 0.05), (int)(h * 0.5));

             // Piernas (simulando flujo de datos o final del código)
            int piernasY = (int)(h * 0.6);
            g2d.drawLine(puntoSujecionX, piernasY, (int)(puntoSujecionX - w * 0.04), (int)(h * 0.75));
            g2d.drawLine(puntoSujecionX, piernasY, (int)(puntoSujecionX + w * 0.04), (int)(h * 0.75));
            
            // Mensaje de Carga / Comentario final
            g2d.setColor(new Color(180, 180, 200)); // Gris claro para el texto
            g2d.setFont(new Font("Monospaced", Font.ITALIC, 14));
            g2d.drawString("// Evitando el StackOverflow...", (int)(w * 0.25), (int)(h * 0.8));
        }
    }
 // ------------------- PUNTO DE ENTRADA -------------------
    public static void main(String[] args) {
          // Asegura que la creación y gestión de la interfaz de usuario se ejecute
        // en el hilo de despacho de eventos (Event Dispatch Thread - EDT) de Swing.
        SwingUtilities.invokeLater(() -> new VentanaInicioGUI().setVisible(true));
    }
    
}
