package ar.com.juegoahorcado.gui.dominio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.domain.Rol;
// Importamos la lógica necesaria
import ar.com.juegoahorcado.domain.Palabra;
import ar.com.juegoahorcado.logic.JuegoAhorcado;
import ar.com.juegoahorcado.repository.Diccionario;
import ar.com.juegoahorcado.repository.GestorUsuarios;

/**
 *  * Clase: VentanaLoginGUI  * FUNCIÓN: Permite al usuario iniciar sesión en el
 * sistema con la estética oscura.
 
 */
public class VentanaLoginGUI extends JFrame {

// --- Definiciones de Estilo y Colores (Modo Oscuro Consistente) ---
    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45);
    private static final Color COLOR_TITULO = new Color(0, 255, 255); // Cian brillante
    private static final Color COLOR_INPUT_FONDO = new Color(45, 45, 60);
    private static final Color COLOR_INPUT_TEXTO = Color.WHITE;
    private static final Color COLOR_BORDE_ENFOQUE = new Color(70, 210, 160); // Verde para el enfoque
    private static final Color COLOR_BORDE_NORMAL = new Color(60, 60, 75);

    // --- CONSTANTES AÑADIDAS PARA JOPTIONPANE ---
  private static final Color COLOR_BOTON_JOPTION = COLOR_BORDE_ENFOQUE.darker(); // Verde menta oscuro
private static final Font FUENTE_INFO = new Font("Segoe UI", Font.PLAIN, 16);

// FUENTES
private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
  private static final Font FUENTE_LABEL = new Font("Segoe UI", Font.BOLD, 14);
  private static final Font FUENTE_INPUT = new Font("Segoe UI", Font.PLAIN, 16);
  private static final Font FUENTE_BOTONES = new Font("Segoe UI", Font.BOLD, 16);

// Colores de Botón
private static final Color COLOR_LOGIN = new Color(0, 150, 255); // Botón de acción principal (Azul)
 private static final Color COLOR_REGISTRO_LINK = new Color(200, 200, 200); // Botón de acción secundaria (Gris claro)

// CAMPOS DE INSTANCIA
private JTextField campoUsuario;
     private JPasswordField campoContrasena;
  private JButton botonIniciarSesion;

 public VentanaLoginGUI() {

      setTitle("🔑 Inicio de Sesión – ByteNine Edition");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setSize(550, 450);
    setLocationRelativeTo(null);
    setResizable(false);
   
JPanel panelPrincipal = new JPanel();
     panelPrincipal.setLayout(new BorderLayout(15, 30));
     panelPrincipal.setBackground(COLOR_FONDO_OSCURO);
    panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 50, 30, 50));
     // Título Centrado
 JLabel titulo = new JLabel("ACCESO PROGRAMADORES", JLabel.CENTER);
      titulo.setFont(FUENTE_TITULO);
      titulo.setForeground(COLOR_TITULO);
   panelPrincipal.add(titulo, BorderLayout.NORTH);
       // Panel Central para el Formulario (GridBagLayout para la parrilla de dos columnas)
JPanel panelFormulario = new JPanel(new GridBagLayout());
    panelFormulario.setBackground(COLOR_FONDO_OSCURO);
   GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(10, 5, 10, 5); // Espaciado entre componentes
        
gbc.fill = GridBagConstraints.HORIZONTAL;
     // --- Inicialización y Layout de Componentes --- 
 campoUsuario = crearCampoTexto();
        campoContrasena = crearCampoContraseña();
 
// Fila 0: Usuario
gbc.gridx = 0;
        gbc.gridy = 0;
     gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
     panelFormulario.add(crearEtiqueta("👤 Usuario o Email"), gbc);

gbc.gridx = 1;
        gbc.gridy = 0;
   gbc.weightx = 1.0;
     panelFormulario.add(campoUsuario, gbc);
   // Fila 1: Contraseña
gbc.gridx = 0;
        gbc.gridy = 1;
     gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        panelFormulario.add(crearEtiqueta("🔒 Contraseña"), gbc);
         gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
    panelFormulario.add(campoContrasena, gbc);
  
panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
       // Panel Inferior para Botones
 JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(COLOR_FONDO_OSCURO);
  
// Botón Iniciar Sesión (Acción principal)
botonIniciarSesion = crearBoton("➡️ Iniciar Sesión", COLOR_LOGIN, e -> iniciarSesion());
     
// Botón Ir a Registro (Acción secundaria)
JButton btnIrRegistro = crearBoton("➕ Registrar Nueva Cuenta", COLOR_REGISTRO_LINK, e -> {
          // Este es un placeholder. Cuando tengas la clase, descomenta el new VentanaRegistroGUI
mostrarMensaje("Redirigiendo a VentanaRegistroGUI (Asume su existencia).", "Registro", JOptionPane.INFORMATION_MESSAGE);
    });


     panelBotones.add(botonIniciarSesion);
      panelBotones.add(btnIrRegistro);
  panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
       add(panelPrincipal);
    

    }

// --- LÓGICA DE LOGIN (CORREGIDA REDIRECCIÓN EXPLÍCITA) ---

private void iniciarSesion() {
        String nombreOEmail = campoUsuario.getText().trim();
       String contrasena = new String(campoContrasena.getPassword());
      if (nombreOEmail.isEmpty() || contrasena.isEmpty()) {
          mostrarMensaje(
"😅 Debes ingresar tu usuario/email y contraseña.",
"Campos vacíos",
JOptionPane.WARNING_MESSAGE
        
        );
return;
    
     }

Usuario usuario = GestorUsuarios.login(nombreOEmail, contrasena);

 if (usuario

    != null) {

       // Login exitoso 🎉
mostrarMensaje(
"✨ ¡Bienvenido, " + usuario.getNombreUsuario() + "!\nHas iniciado sesión como " + usuario.getRol(),
"Inicio de Sesión Exitoso",
JOptionPane.INFORMATION_MESSAGE
    

    );

 dispose(); // Cierra login

  // =========================================================================
// 🎯 ZONA CORREGIDA: Redirección según el rol del usuario
// Hacemos una verificación explícita para Rol.ADMIN y Rol.JUGADOR.
// Esto asegura que solo los jugadores puedan acceder a su menú
// y cualquier otro rol no definido sea manejado como error.
 // =========================================================================
if (usuario.getRol () == Rol.ADMIN) {

       // REDIRECCIÓN ADMINISTRADOR: Se pasa el objeto Usuario al constructor.
new VentanaAdministracionGUI(usuario).setVisible(true);
    

} else if (usuario.getRol () == Rol.JUGADOR) {

     // REDIRECCIÓN JUGADOR COMÚN: Se pasa el objeto Usuario al constructor.
new VentanaMenuPrincipalGUI(usuario).setVisible(true);
    

     } else {

      // Manejo de rol inesperado (¡por si acaso!)
mostrarMensaje(
"🚫 Rol de usuario desconocido o no permitido para el acceso.",
 "Error de Acceso",
 JOptionPane.ERROR_MESSAGE
    

 );
}
// =========================================================================

} else {
// Error de credenciales
mostrarMensaje(
"🚫 Credenciales incorrectas.\nVerifica tu usuario (o email) y contraseña.",
"Error de Login",
JOptionPane.ERROR_MESSAGE
);
campoContrasena.setText(""); // Limpiar campo de contraseña
}
}

// --- MÉTODOS DE ESTILO Y AUXILIARES (Sin Cambios) ---

private void aplicarEstiloJOptionPane() {
UIManager.put("OptionPane.background", COLOR_FONDO_OSCURO);
UIManager.put("Panel.background", COLOR_FONDO_OSCURO);
 UIManager.put("OptionPane.messageForeground", Color.WHITE);
UIManager.put("OptionPane.font", FUENTE_INFO);
UIManager.put("Button.background", COLOR_BOTON_JOPTION);
UIManager.put("Button.foreground", Color.WHITE);
UIManager.put("Button.font", FUENTE_BOTONES.deriveFont(Font.PLAIN, 14f));
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
private void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
aplicarEstiloJOptionPane();
JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
restaurarEstiloJOptionPane();
}

private JLabel crearEtiqueta(String texto) {
JLabel label = new JLabel(texto);
label.setFont(FUENTE_LABEL);
label.setForeground(Color.WHITE);
return label;
}

private JTextField crearCampoTexto() {
JTextField textField = new JTextField(20);
textField.setFont(FUENTE_INPUT);
textField.setForeground(COLOR_INPUT_TEXTO);
textField.setBackground(COLOR_INPUT_FONDO);
textField.setCaretColor(COLOR_BORDE_ENFOQUE);
textField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 1),
 BorderFactory.createEmptyBorder(10, 15, 10, 15)
));
// Listener para cambiar el borde al enfocar
textField.addFocusListener(new FocusAdapter() {
 public void focusGained(FocusEvent evt) {
textField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE, 2),
BorderFactory.createEmptyBorder(9, 14, 9, 14)
));
}
public void focusLost(FocusEvent evt) {
textField.setBorder(BorderFactory.createCompoundBorder(
 BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 1),
 BorderFactory.createEmptyBorder(10, 15, 10, 15)
));
}
});
return textField;
}

private JPasswordField crearCampoContraseña() {
JPasswordField passwordField = new JPasswordField(20);
passwordField.setFont(FUENTE_INPUT);
 passwordField.setForeground(COLOR_INPUT_TEXTO);
passwordField.setBackground(COLOR_INPUT_FONDO);
passwordField.setCaretColor(COLOR_BORDE_ENFOQUE);
passwordField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 1),
BorderFactory.createEmptyBorder(10, 15, 10, 15)
 ));
passwordField.addFocusListener(new FocusAdapter() {
public void focusGained(FocusEvent evt) {
passwordField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE, 2),
BorderFactory.createEmptyBorder(9, 14, 9, 14)
));
}
public void focusLost(FocusEvent evt) {
passwordField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(COLOR_BORDE_NORMAL, 1),
BorderFactory.createEmptyBorder(10, 15, 10, 15)
));
}
});
return passwordField;
}

private JButton crearBoton(String texto, Color colorFondo, ActionListener listener) {
JButton button = new JButton(texto);
button.setBackground(colorFondo);
button.setForeground(Color.WHITE);

if (colorFondo.equals(COLOR_REGISTRO_LINK)) {
button.setForeground(Color.BLACK);
}

button.setFont(FUENTE_BOTONES);
button.setFocusPainted(false);
 button.setCursor(new Cursor(Cursor.HAND_CURSOR));

button.setPreferredSize(new Dimension(240, 45));

button.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(colorFondo.darker(), 1),
BorderFactory.createEmptyBorder(5, 20, 5, 20)
));

button.addActionListener(listener);
return button;
}

public static void main(String[] args){
// Inicialización de datos de prueba si es necesario.
SwingUtilities.invokeLater(() -> new VentanaLoginGUI().setVisible(true));
}
}

