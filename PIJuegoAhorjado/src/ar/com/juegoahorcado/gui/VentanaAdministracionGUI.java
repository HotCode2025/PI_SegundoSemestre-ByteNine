package ar.com.juegoahorcado.gui;

import javax.swing.*;//importamos el kit para crear interfaz grafica, botones,paneles, campos de texto,etc
import javax.swing.table.DefaultTableModel;//para manejar las tablas de usuarios y palabras
import java.awt.*;//para color, fondo, dimension y gestores de diseños que usa swing
import java.awt.event.WindowAdapter;//para manejar los eventos de cerrar ventana
import java.awt.event.WindowEvent;//para representar un evento de ventana se usas con windows adapter
import java.util.List;//para  colecciones de objetos(la lista de mensajes inspiradores).
import java.util.Arrays;//para manipular arreglos ,incluyendo la conversión de un array en una List(utilizado para MENSAJES_INSPIRADORES)
import java.util.Random;//para generar los numeros seudoaleatorios, necesarios para seleccionar un mensaje inspirador de forma aleatoria.
import java.awt.event.ActionListener; // define los metodos para manejar las  acciones en los componentes JButton(botones)

// Importaciones necesarias de Dominio, Repositorio (conectan la GUI con la lógica de negocio y la capa de datos)
import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.domain.Palabra; 
import ar.com.juegoahorcado.domain.Rol;
import ar.com.juegoahorcado.repository.Diccionario; 
import ar.com.juegoahorcado.repository.GestorUsuarios;



  //---Clase: VentanaAdministracionGUI
  //Sera la interfaz principal para usuarios con rol ADMIN.(hereda de JFrame)
 
public class VentanaAdministracionGUI extends JFrame {
    
    //ATRIBUTOS
    
    //Atributos de Clase (Constantes de Estilo y Mensajes)
//Las variables son definidas como static final y se usan a lo largo de la clase
    //para mantener la consistencia visual y la logica de la aplicación.

    // --- Definiciones de Estilo y Colores
    private static final Color COLOR_FONDO_OSCURO = new Color(30, 30, 45);//Color base para el fondo de la ventana y paneles principales. 
    private static final Color COLOR_BORDE_ENFOQUE = new Color(70, 210, 160); //Color brillante de acento, para botones, bordes y elementos importantes.
    private static final Color COLOR_TITULO_ADMIN = new Color(255, 100, 100);//Color de énfasis para títulos y botones de acción crítica (ejempl Eliminar) 
    private static final Color COLOR_INPUT_FONDO = new Color(45, 45, 60); //para el fondo de los campos de texto y las tablas.
    private static final Color COLOR_INPUT_TEXTO = Color.WHITE;//Color del texto dentro de los campos de entrada.
    
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);//fuente para el titulo principal de la ventana
    private static final Font FUENTE_PESTANA = new Font("Segoe UI", Font.BOLD, 14);//Fuente para las pestañas y encabezados de tabla.
    private static final Font FUENTE_LABEL = new Font("Segoe UI", Font.BOLD, 14);//fuente pala las JLabels
    private static final Font FUENTE_INPUT = new Font("Segoe UI", Font.PLAIN, 14);//Fuente para los campos de texto.
    private static final Font FUENTE_BOTONES = new Font("Segoe UI", Font.BOLD, 14);//Fuente para los botones de accion
    
    // --- Mensajes Inspiradores Rotativos para los administradores ---
    //Lista de mensajes predefinidos para mostrar de forma aleatoria en el Dashboard.
    private static final List<String> MENSAJES_INSPIRADORES = Arrays.asList(
        "¡Hola, Creador! 👋 ¡Vamos a hacer de este juego la próxima sensación viral! Tu ingenio es el código.",
        "El código es poesía, y tú eres el poeta. ¡Que el ranking sea épico!",
        "Aquí es donde creamos la magia. Mantén el Diccionario fresco y a los jugadores contentos.",
        "La vida es corta, pero la lista de tareas del administrador es infinita. ¡Empecemos con las palabras!"
    );
    
    //----Atributos de Instancia(Campos y componentes)----
    /*Estoscampos (private) almacenan el estado de la ventana y las referencias 
     a los componentes de Swing para su manipulación.
     */
    
    //Relacionados con diccionario
    private DefaultTableModel modeloTablaPalabras;//Modelo de datos de la tabla para la gestión de palabras.
    private JTable tablaPalabras;//Componente visual que muestra las palabras del diccionario.
    private JTextField campoNuevaPalabra;// campo para ingresar la palabra a añadir al diccionario
    private JTextField campoNuevaPista;//Campo para ingresar la pista de la palabra.
    
    //Relacionados con usuarios
    private DefaultTableModel modeloTablaUsuarios;//Modelo de datos de la tabla para la gestión de usuarios.
    private JTable tablaUsuarios;// componente visual que muestra el listado de usuarios.
    private JTextField campoNombreUsuarioAdmin;//Campo para el nombre de usuario del nuevo administrador
    private JTextField campoEmailAdmin;//Campo para el email del nuevo administrador.
    private JPasswordField campoContrasenaAdmin;//Campo para la contraseña del nuevo administrador.
    private JSpinner spinnerEdadAdmin; //Selector numérico para la edad del nuevo administrador.
    //Objeto Usuario logueado actualmente con rol ADMIN,  para validación.
    private Usuario usuarioAdmin;

   //CONSTRUCTOR
//Inicializa la ventana de administración. 
    //Asigna el usuario que ha iniciado sesión, 
   
    public VentanaAdministracionGUI(Usuario usuario) {
        this.usuarioAdmin = usuario;
        //establecemos el título, tamaño y configura el manejo del cierre de la ventana 
        setTitle("⚙️ Panel de Administración | Usuario: " + usuario.getNombreUsuario());
        setSize(1200, 750); // Tamaño optimizado
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Usamos el listener para manejar el cierre
        // Crea y organiza las tres pestañas principales (JTabbedPane).
        configurarCierreVentana();

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO_OSCURO);

        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("BIENVENIDO, ADMINISTRADOR " + usuarioAdmin.getNombreUsuario().toUpperCase(), JLabel.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TITULO_ADMIN);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- JTabbedPane para Organizar la Interfaz ---
        JTabbedPane tabbedPane = new JTabbedPane();
        aplicarEstiloPestanas(tabbedPane); 
        //pestañas
        tabbedPane.addTab("📊 Dashboard/Estadísticas", crearPanelDashboard());
        tabbedPane.addTab("👥 Gestión de Usuarios", crearPanelGestionUsuarios());
        tabbedPane.addTab("📚 Gestión de Diccionario", crearPanelGestionDiccionario());

        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        add(panelPrincipal);
    }
    //METODOS DE CLASE
    
    //1. METODOS DE INICIALIZACION Y DASHBOARD(Pestaña 1)
    
    //Selecciona aleatoriamente un mensaje de la lista MENSAJES_INSPIRADORES.
    private String obtenerMensajeInspiradorAleatorio() {
        Random rand = new Random();
        int indice = rand.nextInt(MENSAJES_INSPIRADORES.size());
        return MENSAJES_INSPIRADORES.get(indice);//va a retornar un string
    }
    
    
    
// implementacion: dashboard/estadisticas 
    //Construye el panel principal de estadísticas: //mostramso metricas (total de palabras,
    //usuarios, etc.) y el ranking de jugadores. 
    //Contiene el botón de "Cerrar Sesión".
    private JPanel crearPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- Mensaje Inspirador Rotativo (Norte) ---
        JTextArea lblInspiracion = new JTextArea(obtenerMensajeInspiradorAleatorio());
        lblInspiracion.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
        lblInspiracion.setForeground(COLOR_BORDE_ENFOQUE);
        lblInspiracion.setBackground(COLOR_FONDO_OSCURO.brighter());
        lblInspiracion.setWrapStyleWord(true);
        lblInspiracion.setLineWrap(true);
        lblInspiracion.setEditable(false);
        lblInspiracion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.add(lblInspiracion, BorderLayout.NORTH);
       
        // --- Grid Central: Métricas Clave (Ajuste para centrar y limitar el estiramiento) ---
        JPanel panelMetricasGrid = new JPanel(new GridLayout(2, 2, 40, 40)); 
        panelMetricasGrid.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());

        // Métrica 1: Total de Palabras 
        int totalPalabras = Diccionario.obtenerTotalPalabras(); 
        panelMetricasGrid.add(crearTarjetaMétrica("📚 Diccionario Activo", String.valueOf(totalPalabras), "Palabras Listas para el Juego"));
        
        List<Usuario> todosLosUsuarios = GestorUsuarios.getTodosLosUsuarios();
        
        // Métrica 2: Total de Usuarios
        int totalUsuarios = todosLosUsuarios.size();
        panelMetricasGrid.add(crearTarjetaMétrica("👥 Usuarios Registrados", String.valueOf(totalUsuarios), "Jugadores y Administradores"));

        // Métrica 3: Administradores
        long totalAdmins = todosLosUsuarios.stream().filter(u -> u.getRol() == Rol.ADMIN).count();
        panelMetricasGrid.add(crearTarjetaMétrica("🔑 Creadores del Sistema", String.valueOf(totalAdmins), "Cuentas con Rol ADMIN"));

        // Métrica 4: Jugadores
        long totalJugadores = todosLosUsuarios.stream().filter(u -> u.getRol() == Rol.JUGADOR).count();
        panelMetricasGrid.add(crearTarjetaMétrica("🎮 Comunidad Jugadora", String.valueOf(totalJugadores), "Usuarios con Rol JUGADOR"));

        // Contenedor que centra el Grid y limita su ancho
        JPanel panelMetricasContenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMetricasContenedor.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        
        // Fija el tamaño del Grid para que no se estire
        panelMetricasGrid.setPreferredSize(new Dimension(900, 300)); 
        
        panelMetricasContenedor.add(panelMetricasGrid);
        panel.add(panelMetricasContenedor, BorderLayout.CENTER);

        // --- Ranking (Panel Intermedio) ---
        JTextArea txtRanking = new JTextArea(GestorUsuarios.generarRanking());
        txtRanking.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtRanking.setForeground(Color.LIGHT_GRAY);
        txtRanking.setBackground(COLOR_INPUT_FONDO);
        txtRanking.setEditable(false);
        JScrollPane scrollRanking = new JScrollPane(txtRanking);
        scrollRanking.setPreferredSize(new Dimension(scrollRanking.getPreferredSize().width, 250));
        scrollRanking.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_TITULO_ADMIN), 
            "🏆 TOP JUGADORES (Ranking Actual)", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP, 
            FUENTE_LABEL, 
            COLOR_TITULO_ADMIN)
        );
        
        // --- Botón de Cerrar Sesión (Pie del Dashboard) ---
        JButton btnCerrarSesion = crearBoton("🚪 Cerrar Sesión y Volver al Login", COLOR_TITULO_ADMIN, e -> cerrarSesion());
        JPanel panelPie = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panelPie.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        panelPie.add(btnCerrarSesion);
        
        JPanel panelDashboardSur = new JPanel(new BorderLayout(0, 15));
        panelDashboardSur.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        panelDashboardSur.add(scrollRanking, BorderLayout.NORTH);
        panelDashboardSur.add(panelPie, BorderLayout.SOUTH);
        
        panel.add(panelDashboardSur, BorderLayout.SOUTH); 

        return panel;
    }
    //Generamos  un componente visual tipo "tarjeta" estilizada para mostrar una métrica clave.
    private JPanel crearTarjetaMétrica(String titulo, String valor, String subtitulo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(COLOR_INPUT_FONDO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE.darker(), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTitulo = new JLabel(titulo, JLabel.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitulo.setForeground(COLOR_BORDE_ENFOQUE);

        JLabel lblValor = new JLabel(valor, JLabel.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValor.setForeground(Color.WHITE);
        lblValor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel lblSubtitulo = new JLabel(subtitulo, JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.GRAY);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblValor, BorderLayout.CENTER);
        panel.add(lblSubtitulo, BorderLayout.SOUTH);
        return panel;
    }
    
    
    //2. METODOS DE GESTION DE USUARIOS(Pestaña 2).Implementacion
    /*Construye el panel de gestión de usuarios, que incluye el formulario para 
    añadir nuevos administradores y la tabla de listado de todos los usuarios.*/
    private JPanel crearPanelGestionUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(COLOR_FONDO_OSCURO.brighter().brighter()); 
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Panel Norte: Formulario para Agregar Administrador ---
        JPanel panelAnadirAdmin = crearPanelAnadirAdministrador();
        panel.add(panelAnadirAdmin, BorderLayout.NORTH);

        // --- Panel Central: Tabla de Usuarios ---
        JPanel panelTablaUsuarios = new JPanel(new BorderLayout(10, 10));
        panelTablaUsuarios.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        
        modeloTablaUsuarios = new DefaultTableModel(new Object[]{"ID", "Usuario", "Email", "Rol", "Puntos"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = crearTablaEstilizada(modeloTablaUsuarios);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE.darker()), 
            "👥 Listado de Usuarios (Jugadores y Administradores)", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP, 
            FUENTE_LABEL, 
            Color.WHITE)
        );
        
        panelTablaUsuarios.add(scrollPane, BorderLayout.CENTER);

        // --- Panel Sur: Botón de Eliminar ---
        JButton btnEliminarUsuario = crearBoton("🗑️ Eliminar Usuario Seleccionado", COLOR_TITULO_ADMIN, e -> eliminarUsuarioSeleccionado());
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        panelEliminar.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        panelEliminar.add(btnEliminarUsuario);
        panelTablaUsuarios.add(panelEliminar, BorderLayout.SOUTH);

        panel.add(panelTablaUsuarios, BorderLayout.CENTER);

        cargarDatosUsuarios();
        
        return panel;
    }
    //Construye el formulario para registrar un nuevo administrador, 
    //aplicando un diseño GridBagLayout.
    private JPanel crearPanelAnadirAdministrador() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO_OSCURO);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE.darker()), 
            "➕ Registrar Nuevo Administrador", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP, 
            FUENTE_LABEL, 
            COLOR_BORDE_ENFOQUE)
        );
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Componentes estilizados
        gbc.gridx = 0; gbc.gridy = 0; panel.add(crearEtiqueta("Usuario (Nick):"), gbc);
        gbc.gridx = 1; campoNombreUsuarioAdmin = crearCampoTexto(15); panel.add(campoNombreUsuarioAdmin, gbc);
        
        gbc.gridx = 2; panel.add(crearEtiqueta("Email:"), gbc);
        gbc.gridx = 3; campoEmailAdmin = crearCampoTexto(20); panel.add(campoEmailAdmin, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(crearEtiqueta("Contraseña:"), gbc);
        gbc.gridx = 1; campoContrasenaAdmin = new JPasswordField(15); campoContrasenaAdmin = (JPasswordField) aplicarEstiloCampo(campoContrasenaAdmin); panel.add(campoContrasenaAdmin, gbc);

        gbc.gridx = 2; panel.add(crearEtiqueta("Edad:"), gbc);
        gbc.gridx = 3; spinnerEdadAdmin = new JSpinner(new SpinnerNumberModel(20, 18, 99, 1)); spinnerEdadAdmin = (JSpinner) aplicarEstiloCampo(spinnerEdadAdmin); panel.add(spinnerEdadAdmin, gbc);
        
        // Botón con color de enfoque
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 2; gbc.fill = GridBagConstraints.VERTICAL; gbc.anchor = GridBagConstraints.CENTER;
        JButton btnRegistrarAdmin = crearBoton("🔑 Registrar Admin", COLOR_BORDE_ENFOQUE, e -> registrarNuevoAdministrador());
        panel.add(btnRegistrarAdmin, gbc);

        return panel;
    }
      //Consulta GestorUsuarios.getTodosLosUsuarios() y rellena el modeloTablaUsuarios.
    private void cargarDatosUsuarios() {
        modeloTablaUsuarios.setRowCount(0);

        for (Usuario u : GestorUsuarios.getTodosLosUsuarios()) {
            modeloTablaUsuarios.addRow(new Object[]{
                u.getId(), 
                u.getNombreUsuario(), 
                u.getEmail(), 
                u.getRol().toString(), 
                u.getPuntuacionTotal()
            });
        }
    }
    //Lógica de negocio para validar la entrada, crear un nuevo Usuario con 
    //Rol.ADMIN y guardarlo en el GestorUsuarios.
    private void registrarNuevoAdministrador() {
        String nombreUsuario = campoNombreUsuarioAdmin.getText().trim();
        String email = campoEmailAdmin.getText().trim();
        String contrasena = String.valueOf(campoContrasenaAdmin.getPassword()); 
        int edad = (Integer) spinnerEdadAdmin.getValue();
        
        if (nombreUsuario.isEmpty() || email.isEmpty() || contrasena.length() < 4) {
            mostrarMensaje("⚠️ Error de Formulario: Todos los campos son obligatorios y la contraseña debe tener al menos 4 caracteres.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (GestorUsuarios.existeUsuario(email, nombreUsuario)) {
            mostrarMensaje("❌ Error de Registro: El nombre de usuario o el email ya están en uso.", "Duplicado", JOptionPane.ERROR_MESSAGE);
            return;
        }

       
        // Se crean Nombre y Apellido a partir del Nick para coincidir con el constructor de Usuario
        String nombre = nombreUsuario.split(" ")[0].toUpperCase(); 
        String apellido = (nombreUsuario.contains(" ")) ? nombreUsuario.split(" ")[nombreUsuario.split(" ").length - 1].toUpperCase() : "ADMIN";
        
        // Creamos un nuevo usuario
        Usuario nuevoAdmin = new Usuario(nombre, apellido, edad, email, nombreUsuario, contrasena, Rol.ADMIN);
      
        
        GestorUsuarios.agregarUsuario(nuevoAdmin);
        
        modeloTablaUsuarios.addRow(new Object[]{
            nuevoAdmin.getId(), 
            nuevoAdmin.getNombreUsuario(), 
            nuevoAdmin.getEmail(), 
            nuevoAdmin.getRol().toString(), 
            nuevoAdmin.getPuntuacionTotal()
        });
        
        campoNombreUsuarioAdmin.setText("");
        campoEmailAdmin.setText("");
        campoContrasenaAdmin.setText("");
        spinnerEdadAdmin.setValue(20);
        mostrarMensaje("✅ Administrador '" + nombreUsuario + "' registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }
    //Maneja la eliminación del usuario seleccionado en la tabla,
    //incluyendo una validacion de seguridad (no permitir la autoeliminacion).
    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            mostrarMensaje("👆 Por favor, selecciona una fila de la tabla para eliminar un usuario.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer idUsuario = (Integer) modeloTablaUsuarios.getValueAt(filaSeleccionada, 0);
        String nombreUsuario = modeloTablaUsuarios.getValueAt(filaSeleccionada, 1).toString();
        
        if (idUsuario.equals(this.usuarioAdmin.getId())) {
            mostrarMensaje("🚫 Operación Denegada: No puedes eliminar tu propia cuenta de administrador.", "Error de Seguridad", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Usuario usuarioAEliminar = GestorUsuarios.buscarUsuario(idUsuario);

        aplicarEstiloJOptionPane();
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas eliminar al usuario '" + nombreUsuario + "' (ID: " + idUsuario + ")? Esta acción es irreversible.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        restaurarEstiloJOptionPane();
        
        if (confirmacion == JOptionPane.YES_OPTION && usuarioAEliminar != null) {
            boolean exito = GestorUsuarios.eliminarUsuario(usuarioAEliminar); 
            
            if (exito) {
                modeloTablaUsuarios.removeRow(filaSeleccionada);
                mostrarMensaje("🗑️ Usuario '" + nombreUsuario + "' eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarMensaje("❌ Error: No se pudo eliminar al usuario en el repositorio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    // 3-METODOS DE GESTION DE DICCIONARIO (pestaña 3)
    /*Construye el panel de gestión de palabras, que incluye el formulario 
    para añadir y la tabla de listado del diccionario.*/
    private JPanel crearPanelGestionDiccionario() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(COLOR_FONDO_OSCURO.brighter().brighter()); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelAnadir = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelAnadir.setBackground(COLOR_FONDO_OSCURO);
        panelAnadir.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_ENFOQUE.darker()), 
            "➕ Añadir Nueva Palabra al Diccionario", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP, 
            FUENTE_LABEL, 
            COLOR_BORDE_ENFOQUE)
        );

        campoNuevaPalabra = crearCampoTexto(15);
        campoNuevaPista = crearCampoTexto(20);
        JButton btnAnadir = crearBoton("💾 Guardar Palabra", COLOR_BORDE_ENFOQUE, e -> anadirPalabra());

        panelAnadir.add(crearEtiqueta("Palabra:"));
        panelAnadir.add(campoNuevaPalabra);
        panelAnadir.add(crearEtiqueta("Pista:"));
        panelAnadir.add(campoNuevaPista);
        panelAnadir.add(btnAnadir);

        panel.add(panelAnadir, BorderLayout.NORTH);

        modeloTablaPalabras = new DefaultTableModel(new Object[]{"Palabra", "Pista", "Longitud"}, 0);
        tablaPalabras = crearTablaEstilizada(modeloTablaPalabras);
        JScrollPane scrollPane = new JScrollPane(tablaPalabras);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelEliminar.setBackground(COLOR_FONDO_OSCURO.brighter().brighter());
        JButton btnEliminar = crearBoton("🗑️ Eliminar Palabra Seleccionada", COLOR_TITULO_ADMIN, e -> eliminarPalabra());
        
        panelEliminar.add(btnEliminar);
        panel.add(panelEliminar, BorderLayout.SOUTH);

        cargarDatosDiccionario();
        
        return panel;
    }

    
    //Consulta Diccionario.getPalabrasInmutables() y rellena el modeloTablaPalabras.
    private void cargarDatosDiccionario() {
        modeloTablaPalabras.setRowCount(0);
        
        for (Palabra p : Diccionario.getPalabrasInmutables()) {
            modeloTablaPalabras.addRow(new Object[]{
                p.getPalabra(), 
                p.getPista(), 
                p.getPalabra().length()
            });
        }
    }
    //Lógica para validar la palabra y la pista (incluyendo el regex de letras),
    //y guardar la nueva palabra en el Diccionario.
    private void anadirPalabra() {
        String palabraStr = campoNuevaPalabra.getText().trim().toUpperCase();
        String pista = campoNuevaPista.getText().trim();
        
        if (palabraStr.isEmpty() || pista.isEmpty()) {
            mostrarMensaje("🚨 Faltan datos. Por favor, ingresa la palabra y la pista.", "Error de Entrada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!palabraStr.matches("^[ATENCION]+$") || palabraStr.length() < 3) {
            mostrarMensaje("🚨 La palabra debe contener solo letras (A-Z), sin espacios, y tener al menos 3 caracteres.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Palabra nuevaPalabra = new Palabra(palabraStr, pista);
        
        boolean yaExiste = Diccionario.getPalabrasInmutables().contains(nuevaPalabra);
        
        if (yaExiste) {
             mostrarMensaje("❌ Error al guardar. La palabra '" + palabraStr + "' ya existe en el diccionario.", "Error de Duplicado", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        Diccionario.agregarPalabra(nuevaPalabra);
        
        modeloTablaPalabras.addRow(new Object[]{nuevaPalabra.getPalabra(), nuevaPalabra.getPista(), nuevaPalabra.getPalabra().length()});
        campoNuevaPalabra.setText("");
        campoNuevaPista.setText("");
        mostrarMensaje("✅ Palabra '" + palabraStr + "' guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    //Maneja la eliminación de la palabra seleccionada del diccionario.
    private void eliminarPalabra() {
        int filaSeleccionada = tablaPalabras.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            mostrarMensaje("👆 Por favor, selecciona una palabra de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String palabraAEliminar = modeloTablaPalabras.getValueAt(filaSeleccionada, 0).toString();
        
        aplicarEstiloJOptionPane();
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que deseas eliminar la palabra '" + palabraAEliminar + "' del diccionario?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        restaurarEstiloJOptionPane();
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = Diccionario.eliminarPalabra(palabraAEliminar); 
            
            if (exito) {
                modeloTablaPalabras.removeRow(filaSeleccionada);
                mostrarMensaje("🗑️ Palabra '" + palabraAEliminar + "' eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarMensaje("❌ Error: No se pudo eliminar la palabra. Puede que no esté disponible para edición.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 4. MÉTODOS AUXILIARES Y ESTILO
 
    //Muestra un diálogo de confirmación, cierra la ventana actual y 
    //usa Reflection para abrir la VentanaLoginGUI.
    private void cerrarSesion() {
        aplicarEstiloJOptionPane();
                
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres cerrar la sesión de Administrador?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        restaurarEstiloJOptionPane();

        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
          
            try {
                // Se utiliza reflection para evitar una dependencia circular si no es necesario
                Class<?> loginClass = Class.forName("ar.com.juegoahorcado.gui.dominio.VentanaLoginGUI");
                JFrame loginFrame = (JFrame) loginClass.getDeclaredConstructor().newInstance();
                loginFrame.setVisible(true);
            } catch (Exception e) {
                 // Si falla, VentanaLoginGUI está disponible directamente
                 // new VentanaLoginGUI().setVisible(true); 
                 System.err.println("No se pudo iniciar VentanaLoginGUI de forma segura. Asegúrate de que la clase exista.");
            }
        }
    }
    
    
    //Implementa un WindowAdapter para que el botón de cierre de 
    //la ventana (la "X") llame al método cerrarSesion().
    private void configurarCierreVentana() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarSesion(); 
            }
        });
    }
    
    //Crea un JTextField con el ancho y el estilo predefinido.
    private JTextField crearCampoTexto(int columnas) {
        JTextField textField = new JTextField(columnas); 
        return (JTextField) aplicarEstiloCampo(textField);
    }
    
    //Crea una JLabel con el estilo y color de fuente blanco predefinido.
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_LABEL);
        label.setForeground(Color.WHITE);
        return label;
    }

    //Crea un JButton aplicando todos los estilos y configurando el ActionListener.
    private JButton crearBoton(String texto, Color colorFondo, java.awt.event.ActionListener listener) {
        JButton button = new JButton(texto);
        button.setBackground(colorFondo);
        button.setForeground(colorFondo.getRed() > 150 ? Color.BLACK : Color.WHITE); 
        button.setFont(FUENTE_BOTONES); 
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 40)); 
        button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        button.addActionListener(listener);
        return button;
    }

    //Crea una JTable aplicando el tema oscuro, la selección de color y la
    //capacidad de ordenación (setAutoCreateRowSorter(true)).
    private JTable crearTablaEstilizada(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(COLOR_INPUT_FONDO); 
        table.setForeground(Color.WHITE);      
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(COLOR_BORDE_ENFOQUE.darker().darker()); 
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true); 

        table.getTableHeader().setBackground(COLOR_FONDO_OSCURO);
        table.getTableHeader().setForeground(COLOR_BORDE_ENFOQUE);
        table.getTableHeader().setFont(FUENTE_PESTANA);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
        
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1)); 

        return table;
    }
    
    //Aplica estilos visuales uniformes a JTextField, JPasswordField y JSpinner.
    private JComponent aplicarEstiloCampo(JComponent componente) {
        if (componente instanceof JTextField) {
            JTextField field = (JTextField) componente;
            field.setFont(FUENTE_INPUT);
            field.setForeground(COLOR_INPUT_TEXTO);
            field.setBackground(COLOR_INPUT_FONDO);
            field.setCaretColor(COLOR_BORDE_ENFOQUE);
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        } else if (componente instanceof JSpinner) {
            JSpinner spinner = (JSpinner) componente;
            spinner.setFont(FUENTE_INPUT);
            JComponent editor = spinner.getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
                textField.setForeground(COLOR_INPUT_TEXTO);
                textField.setBackground(COLOR_INPUT_FONDO);
            }
            spinner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else if (componente instanceof JPasswordField) {
             JPasswordField field = (JPasswordField) componente;
            field.setFont(FUENTE_INPUT);
            field.setForeground(COLOR_INPUT_TEXTO);
            field.setBackground(COLOR_INPUT_FONDO);
            field.setCaretColor(COLOR_BORDE_ENFOQUE);
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 75), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
        return componente;
    }
    
    //Configura el tema oscuro para los diálogos emergentes de JOptionPane 
    //a través de UIManager.
    private void aplicarEstiloJOptionPane() {
        UIManager.put("OptionPane.background", COLOR_FONDO_OSCURO);
        UIManager.put("Panel.background", COLOR_FONDO_OSCURO);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("OptionPane.font", FUENTE_PESTANA); 
        UIManager.put("Button.background", COLOR_BORDE_ENFOQUE.darker());
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", FUENTE_PESTANA.deriveFont(Font.PLAIN, 14f));
        UIManager.put("Button.border", BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    //Revierte los cambios en UIManager para evitar que los diálogos futuros
    //se vean afectados
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
    
    //Encapsula el uso seguro de JOptionPane aplicando y restaurando el estilo.
    private void mostrarMensaje(String mensaje, String titulo, int tipoMensaje) {
        aplicarEstiloJOptionPane();
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipoMensaje);
        restaurarEstiloJOptionPane();
    }
    
    //Estiliza el componente JTabbedPane para que se adapte al tema oscuro de la aplicación.
    private void aplicarEstiloPestanas(JTabbedPane tabbedPane) {
        tabbedPane.setBackground(COLOR_FONDO_OSCURO);
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(FUENTE_PESTANA);
        
        UIManager.put("TabbedPane.contentAreaColor", COLOR_FONDO_OSCURO);
        UIManager.put("TabbedPane.selectedBackground", COLOR_BORDE_ENFOQUE.darker());
        UIManager.put("TabbedPane.unselectedBackground", COLOR_FONDO_OSCURO);
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        UIManager.put("TabbedPane.unselectedForeground", Color.LIGHT_GRAY);
        UIManager.put("TabbedPane.focusColor", COLOR_BORDE_ENFOQUE.darker());
    }
    
    
    //PRUEBA VENTANAADMINISTRACIONGUI
    //Punto de entrada del programa. Inicializa el entorno Swing en el EDT,
    //crea un Usuario de prueba con rol ADMIN e inicia la ventana.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
      
            //Agregamos un administrador de prueba
            Usuario adminDePrueba = new Usuario("BAUTISTA", "ADMIN", 19, "admin10@bytenine.com", "BauAdmin", "0311", Rol.ADMIN);
            // Agregamos un jugador de prueba.
            GestorUsuarios.agregarUsuario(new Usuario("GUADALUPE", "ROLDAN", 23, "guada@mail.com", "GuadaR", "0508", Rol.JUGADOR));
            new VentanaAdministracionGUI(adminDePrueba).setVisible(true);
        });
    }
}
