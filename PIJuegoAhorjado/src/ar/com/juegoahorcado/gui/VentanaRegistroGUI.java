package ar.com.juegoahorcado.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Importaciones de su dominio
import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.repository.GestorUsuarios;
import ar.com.juegoahorcado.domain.Rol; 

/**
 * Clase: VentanaRegistroGUI
 * FUNCIÓN: Permite al usuario registrarse en el sistema.
 * TAMAÑO FINAL AJUSTADO a 580px de ancho para evitar que el botón se corte
 * en el entorno de previsualización.
 */
public class VentanaRegistroGUI extends JFrame {

    // --- Definiciones de Estilo y Colores ---
    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45); 
    private static final Color COLOR_TITULO = new Color(220, 220, 255); 
    private static final Color COLOR_INPUT_FONDO = new Color(45, 45, 60); 
    private static final Color COLOR_INPUT_TEXTO = Color.WHITE;
    private static final Color COLOR_BORDE_ENFOQUE = new Color(0, 255, 255); 

    // FUENTES
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24); 
    private static final Font FUENTE_LABEL = new Font("Segoe UI", Font.BOLD, 12); 
    private static final Font FUENTE_INPUT = new Font("Segoe UI", Font.PLAIN, 14); 
    private static final Font FUENTE_BOTONES = new Font("Segoe UI", Font.BOLD, 14); 

    // Colores de Botón
    private static final Color COLOR_REGISTRO = new Color(70, 210, 160); // Botón de acción principal (Crear/Guardar)
    private static final Color COLOR_VOLVER = new Color(200, 200, 200); // Botón de acción secundaria (Volver/Cancelar)

    // CAMPOS DE INSTANCIA
    private JTextField campoNombreCompleto; 
    private JTextField campoEmail, campoNombreUsuario, campoEdad;
    private JPasswordField campoContrasena, campoConfirmarContrasena;

    public VentanaRegistroGUI() {
        setTitle("📝 Registro de Nuevo Jugador – ByteNine");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // TAMAÑO FINAL OPTIMIZADO: 580x520 (Ancho drásticamente reducido para el Preview)
        setSize(580, 520); 
        setLocationRelativeTo(null); 
        setResizable(false);
        
        // Panel Principal con fondo oscuro
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(15, 5)); 
        panelPrincipal.setBackground(COLOR_FONDO_OSCURO);
        // Padding lateral reducido para ganar espacio
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30)); 

        // Título
        JLabel titulo = new JLabel("Crea tu Cuenta de Programador 💡", JLabel.CENTER);
        titulo.setFont(FUENTE_TITULO); 
        titulo.setForeground(COLOR_TITULO);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel Central para el Formulario (GridBagLayout para la parrilla de dos columnas)
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_FONDO_OSCURO);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Espaciado vertical y horizontal entre elementos 
        gbc.insets = new Insets(3, 5, 3, 5); 

        // Inicialización de campos 
        campoNombreCompleto = crearCampoTexto();
        campoEmail = crearCampoTexto();
        campoNombreUsuario = crearCampoTexto();
        campoEdad = crearCampoTexto();
        campoContrasena = crearCampoContraseña();
        campoConfirmarContrasena = crearCampoContraseña();
        
        // Array de Etiquetas para iterar
        JLabel[] labels = {
            crearEtiqueta("👤 Nombre y Apellido"), 
            crearEtiqueta("💻 Nickname (Nombre de Usuario Único)"),
            crearEtiqueta("📧 Correo Electrónico"),
            crearEtiqueta("🎂 Edad (Min. 18 años)"),
            crearEtiqueta("🔒 Contraseña"),
            crearEtiqueta("✅ Confirmar Contraseña")
        };

        // Array de Campos para iterar
        JComponent[] fields = {
            campoNombreCompleto,
            campoNombreUsuario,
            campoEmail,
            campoEdad,
            campoContrasena,
            campoConfirmarContrasena
        };


        // RECORRIDO PARA EL LAYOUT DE DOS COLUMNAS
        for (int i = 0; i < labels.length; i++) {
            // Columna 0: Etiqueta (Izquierda)
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
            gbc.fill = GridBagConstraints.NONE; // No expandir
            gbc.weightx = 0; 
            panelFormulario.add(labels[i], gbc);

            // Columna 1: Campo (Derecha)
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST; 
            gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir campo
            gbc.weightx = 1.0; 
            panelFormulario.add(fields[i], gbc);
        }
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // Panel Inferior para Botones (Centrados)
        // FlowLayout centralizado con espaciado moderado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5)); 
        panelBotones.setBackground(COLOR_FONDO_OSCURO);
        
        // Botón Volver (Cancela el registro y vuelve al login)
        JButton btnVolver = crearBoton("⏪ Volver al Inicio", COLOR_VOLVER, e -> {
            this.dispose(); 
            // CORRECCIÓN: Usar VentanaInicioGUI en lugar de VentanaLoginGUI
            new VentanaInicioGUI().setVisible(true); 
        });

        // Botón Registrar (¡Este es el botón de GUARDAR/CREAR!)
        JButton btnRegistrar = crearBoton("Registrar Programador", COLOR_REGISTRO, e -> registrarUsuario());
        
        panelBotones.add(btnVolver);
        panelBotones.add(btnRegistrar); 

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }

    /**
     * Lógica de registro de usuario. 
     * Guarda el usuario y abre la VentanaInicioGUI.
     */
    private void registrarUsuario() {
        // --- 1. Extracción de Datos ---
        String nombreCompleto = campoNombreCompleto.getText().trim();
        String email = campoEmail.getText().trim();
        String nombreUsuario = campoNombreUsuario.getText().trim();
        String edadStr = campoEdad.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();
        String contrasenaConfirmacion = new String(campoConfirmarContrasena.getPassword()).trim();
        
        String[] partesNombre = nombreCompleto.split(" ", 2);
        String nombre = partesNombre.length > 0 ? partesNombre[0] : "";
        String apellido = partesNombre.length > 1 ? partesNombre[1] : ""; 

        // --- 2. Validación de Campos Vacíos ---
        if (nombreCompleto.isEmpty() || email.isEmpty() || nombreUsuario.isEmpty() || edadStr.isEmpty() || contrasena.isEmpty() || contrasenaConfirmacion.isEmpty()) {
            mostrarMensaje("😅 Faltan campos por ingresar.\nPor favor, completa todos los datos solicitados.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- 3. Validación de Contraseñas ---
        if (!contrasena.equals(contrasenaConfirmacion)) {
            mostrarMensaje("❌ Las contraseñas no coinciden. Por favor, verifícalas.", "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
            campoContrasena.setText("");
            campoConfirmarContrasena.setText("");
            return;
        }

        // --- 4. Validación de Edad ---
        int edad = -1;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 18) {
                mostrarMensaje("😟 Debes ser mayor de 18 años para registrarte.", "Error de Edad", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("❌ La edad debe ser un número entero válido.", "Error de Edad", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- 5. Validar Duplicados ---
        if (GestorUsuarios.existeUsuario(email, nombreUsuario)) {
             mostrarMensaje("❌ Error: El nombre de usuario o email ya están registrados.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
             return;
        }

        // --- 6. Creación y Registro del Usuario ---
        try {
            Usuario nuevoUsuario = new Usuario(nombre, apellido, edad, email, nombreUsuario, contrasena, Rol.JUGADOR);
            GestorUsuarios.agregarUsuario(nuevoUsuario);

            mostrarMensaje("🎉 ¡Registro exitoso! Ya puedes iniciar sesión.", "Éxito al Crear Programador", JOptionPane.INFORMATION_MESSAGE);
            
            dispose(); 
            // CORRECCIÓN: Usar VentanaInicioGUI en lugar de VentanaLoginGUI
            new VentanaInicioGUI().setVisible(true); 

        } catch (Exception e) {
            System.err.println("Error interno en registro: " + e.getMessage());
            mostrarMensaje("❌ Ocurrió un error inesperado. Inténtalo de nuevo.", "Error Interno", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
    }
    
    // --- Métodos Auxiliares de Estilo ---

    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_LABEL);
        label.setForeground(COLOR_TITULO);
        return label;
    }

    private JTextField crearCampoTexto() {
        // Reducido el ancho preferido para ajustarse a la ventana más compacta
        JTextField textField = new JTextField(15); 
        textField.setFont(FUENTE_INPUT);
        textField.setForeground(COLOR_INPUT_TEXTO);
        textField.setBackground(COLOR_INPUT_FONDO);
        textField.setCaretColor(COLOR_BORDE_ENFOQUE); 
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12) 
        ));
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE, 2),
                    BorderFactory.createEmptyBorder(7, 11, 7, 11) 
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        return textField;
    }
    
    private JPasswordField crearCampoContraseña() {
        // Reducido el ancho preferido para ajustarse a la ventana más compacta
        JPasswordField passwordField = new JPasswordField(15); 
        passwordField.setFont(FUENTE_INPUT);
        passwordField.setForeground(COLOR_INPUT_TEXTO);
        passwordField.setBackground(COLOR_INPUT_FONDO);
        passwordField.setCaretColor(COLOR_BORDE_ENFOQUE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12) 
        ));
         passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE, 2),
                    BorderFactory.createEmptyBorder(7, 11, 7, 11) 
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        return passwordField;
    }

    private JButton crearBoton(String texto, Color colorFondo, ActionListener listener) {
        JButton button = new JButton(texto);
        button.setBackground(colorFondo);
        button.setForeground(Color.BLACK); 
        button.setFont(FUENTE_BOTONES); 
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Reducción del tamaño para que ambos quepan
        button.setPreferredSize(new Dimension(220, 40)); 
        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 1),
            BorderFactory.createEmptyBorder(5, 20, 5, 20) 
        ));

        button.addActionListener(listener);
        return button;
    }
}