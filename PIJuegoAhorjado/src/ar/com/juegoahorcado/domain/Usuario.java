package ar.com.juegoahorcado.domain;
import java.util.ArrayList;

// ============================================================================
// CLASE: Usuario
// ============================================================================
// Esta clase VA A representaR a un usuario dentro del juego . 
// su rol , su puntuación y el historial de partidas jugadas.
// ============================================================================

public class Usuario {

    // =========================================================================
    //  ATRIBUTOS ESTATICOS (Propiedades compartidas por TODOS los usuarios)
    // =========================================================================

    // Este contador se usa para generar automaticamente un ID unico
    // para cada nuevo usuario que se crea .
    private static int siguienteIdentificadorUsuario = 1000;


    // =========================================================================
    // ATRIBUTOS DE INSTANCIA (las propiedades unicas  de cada usuario)
    // =========================================================================

    // ------------------------
    // Datos personales
    // ------------------------
    private final int id;          // ID único que identifica al usuario (no cambia nunca)
    private String nombre;         // Nombre del usuario
    private String apellido;       // Apellido del usuario
    private int edad;              // Edad del usuario
    private String email;          // Correo electrónico del usuario (normalizado en minúsculas)
    private Rol rol;               // Rol del usuario (por ejemplo: ADMIN o JUGADOR)

    // ------------------------
    // Credenciales de acceso
    // ------------------------
    private String nombreUsuario;  // Nombre de usuario o apodo usado para ingresar
    private String contrasena;     // Contraseña del usuario

    // ------------------------
    // Datos relacionados con el juego
    // ------------------------
    private int puntuacionTotal;               // Suma total de puntos acumulados
    private ArrayList<Partida> registroPartidas; // Lista con todas las partidas jugadas por el usuario


    // =========================================================================
    //  CONSTRUCTOR  (Se ejecuta al crear un nuevo usuario)
    // =========================================================================

    public Usuario(String nombre, String apellido, int edad, String email, 
                   String nombreUsuario, String contrasena, Rol rol) {

        // Generamos automáticamente el ID, incrementando el contador estático
        this.id = ++siguienteIdentificadorUsuario;

        // Normalizamos los datos personales para mantener consistencia
        this.nombre = nombre.toUpperCase();      // Convertimos el nombre a mayúsculas
        this.apellido = apellido.toUpperCase();  // Convertimos el apellido a mayúsculas
        this.edad = edad;                        // Asignamos la edad tal cual
        this.email = email.toLowerCase();        // Convertimos el email a minúsculas
        this.rol = rol;                          // Asignamos el rol (ADMIN / JUGADOR)

        // Credenciales del usuario
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;

        // Inicialización de los datos de juego
        this.puntuacionTotal = 0;                     // Empieza con 0 puntos
        this.registroPartidas = new ArrayList<>();    // Empieza con el historial vacío
    }


    // =========================================================================
    // MÉTODOS GETTERS  (Para leer los valores de los atributos)
    // =========================================================================

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public int getEdad() {
        return this.edad;
    }

    public String getEmail() {
        return this.email;
    }

    public Rol getRol() {
        return this.rol;
    }

    public String getNombreUsuario() {
        return this.nombreUsuario;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public int getPuntuacionTotal() {
        return this.puntuacionTotal;
    }

    public ArrayList<Partida> getRegistroPartidas() {
        return this.registroPartidas;
    }


    // =========================================================================
    //  MÉTODOS SETTERS  (para modificar los valores de los atributos)
    // =========================================================================

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();  // Se almacena siempre en mayúsculas
    }

    public void setApellido(String apellido) {
        this.apellido = apellido.toUpperCase(); // También se normaliza en mayúsculas
    }

    public void setEdad(int edad) {
        this.edad = edad; // Se actualiza la edad
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase(); // Se guarda en minúsculas
    }

    public void setRol(Rol rol) {
        this.rol = rol; // Se cambia el rol del usuario
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario; // Se actualiza el nombre de usuario
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena; // Se actualiza la contraseña
    }

    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal; // Se actualiza la puntuación acumulada
    }


    // =========================================================================
    //  METODOS DE LOGICA ( acciones del usuario)
    // =========================================================================

    // -------------------------------------------------------------------------
    //  Registrar una nueva partida en el historial
    // -------------------------------------------------------------------------
    // Este metose se utiliza para guardar una nueva partida que el usuario haya jugado.
    // Cada partida se agrega al registro para poder consultar el historial más adelante.
    public void registrarPartida(Partida partida) {
        this.registroPartidas.add(partida);
    }

    // -------------------------------------------------------------------------
    // Generar un historial en formato texto
    // -------------------------------------------------------------------------
    // Este método crea un resumen con todas las partidas jugadas por el usuario.
    // Si el usuario no tiene partidas registradas, devuelvolvera un mensaje.
    public String generarHistorial() {
        // Si no hay partidas, informamos que el usuario no ha jugado aún
        if (this.registroPartidas.isEmpty()) {
            return "El usuario " + this.nombreUsuario + " no ha jugado ninguna partida aún.";
        }

        // Creamos un texto que contendrá el historial completo
        StringBuilder reporte = new StringBuilder();

        // Encabezado
        reporte.append("--- HISTORIAL DE PARTIDAS DE ")
               .append(this.nombreUsuario)
               .append(" ---\n");

        // Puntuación total
        reporte.append("Puntuación Total Acumulada: ")
               .append(this.puntuacionTotal)
               .append("\n");

        // Detalle de cada partida
        for (int i = 0; i < this.registroPartidas.size(); i++) {
            Partida partidaActual = this.registroPartidas.get(i);

            reporte.append("Partida #").append(i + 1)
                    .append(" | Palabra: ").append(partidaActual.getPalabraAdivinada())
                    .append(" | Resultado: ").append(partidaActual.getResultado())
                    .append(" | Puntos: (Aplicados por App)\n");
        }

        // Devolvemos el historial completo en formato texto
        return reporte.toString();
    }
}