package ar.com.juegoahorcado.app;

// =========================================================================================
// BLOQUE DE IMPORTACIONES: CONEXIÓN DE LA CAPA 'APP' CON LAS OTRAS CAPAS
// =========================================================================================
import javax.swing.JOptionPane; 

// ENTIDADES DE DOMINIO: Necesarias para manipular los datos del juego y el usuario.
import ar.com.juegoahorcado.domain.Usuario; 
import ar.com.juegoahorcado.domain.Rol;     
import ar.com.juegoahorcado.domain.Palabra; 
import ar.com.juegoahorcado.gui.dominio.AhorcadoAppGUI;



// REPOSITORIOS: Clases Singleton que manejan el acceso a datos.
import ar.com.juegoahorcado.repository.GestorUsuarios; 
import ar.com.juegoahorcado.repository.Diccionario;    

// LÓGICA DE NEGOCIO: Motor del juego (usa el Patrón Template Method).
import ar.com.juegoahorcado.logic.JuegoAhorcado; 

import java.util.ArrayList; 
import java.util.List;      

/**
 * CLASE PRINCIPAL: AhorcadoApp
 * --------------------------------------------------------------------------------
 * PROPÓSITO: Esta clase funciona como la Capa de Presentación (UI).
 * Se encarga de la ORQUESTACIÓN: muestra menús, llama a la lógica y muestra resultados.
 * --------------------------------------------------------------------------------
 */
public class AhorcadoApp {

    // ------------------------- CONSTANTES DE LA APLICACIÓN -------------------------
    private static final String TITULO_APP = "Ahorcado POO - ByteNine"; 
    private static final int PUNTOS_GANAR = 10;    // Puntos fijos por victoria
    private static final int PUNTOS_PERDER = -5;   // Puntos fijos por derrota
    private static final int LIMITE_PUNTOS = 1000; // Objetivo de la racha.

    // ------------------------- ESTADO DE SESIÓN Y ACUMULACIÓN -------------------------
    private static Usuario usuarioLogueado = null; 
    private static List<RegistroPartida> historialPartidas = new ArrayList<>();

    /**
     * CLASE INTERNA (Anidada): RegistroPartida
     */
    private static class RegistroPartida {
        String palabraSecreta;
        String resultado; 
        int puntosObtenidos;

        public RegistroPartida(String palabraSecreta, String resultado, int puntosObtenidos) {
            this.palabraSecreta = palabraSecreta;
            this.resultado = resultado;
            this.puntosObtenidos = puntosObtenidos;
        }

        @Override
        public String toString() {
            return String.format("%-10s | %-15s | %s", 
                                  resultado, 
                                  palabraSecreta, 
                                  (puntosObtenidos > 0 ? "+" : "") + puntosObtenidos + " pts");
        }
    }

    /**
     * MÉTODO PRINCIPAL: main
     */
    public static void main(String[] args) {
        mostrarMensaje(
            "Bienvenido al Ahorcado POO\n" +
            "¡Por favor, inicia sesión o regístrate para comenzar!", 
            "INICIO"
        );
        
        
   // Mostrar un menú para elegir entre diseño simple o GUI
    String opcion = JOptionPane.showInputDialog(
        null, 
        "Elige el modo de juego:\n1. Consola\n2. Interfaz Gráfica", 
        TITULO_APP, 
        JOptionPane.QUESTION_MESSAGE
    );

    if ("2".equals(opcion)) {
        // Si elige "2" (GUI), ejecutar la clase AhorcadoAppGUI 
        AhorcadoAppGUI.main(args); // Esto ejecuta la GUI
    } else {
        // Si elige "1" (Consola), continuar con el flujo actual
        mostrarMenuPrincipal(); // Esto sigue con la versión en consola
    }

    // Llamada para guardar los datos
    GestorUsuarios.guardarDatos(); 
    
    // Mensaje final de salida
    mostrarMensaje("Sesión finalizada. ¡Vuelve pronto!", "ADIÓS");
}

    // =========================================================================================
    // I. MENÚS PRINCIPALES
    // =========================================================================================

    private static void mostrarMenuPrincipal() {
        int opcion;
        
        do {
            String menu = 
                "--- Menú de Acceso ---\n" +
                "1. Iniciar Sesión\n" +
                "2. Registrarse\n" +
                "3. Salir\n" +
                "----------------------\n" +
                "Seleccione una opción:";

            String input = JOptionPane.showInputDialog(null, menu, TITULO_APP, JOptionPane.QUESTION_MESSAGE);
            
            if (input == null) {
                opcion = 3;
            } else {
                try {
                    opcion = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    mostrarMensaje("Opción inválida. Inténtalo de nuevo.", "Error de Entrada");
                    opcion = 0; 
                }
            }

            switch (opcion) {
                case 1:
                    iniciarSesion(); 
                    break;
                case 2:
                    registrarUsuario(); 
                    break;
                case 3:
                    break; 
                default:
                    if (opcion != 0) {
                        mostrarMensaje("Opción no reconocida.", "Error");
                    }
                    break;
            }

        } while (usuarioLogueado == null && opcion != 3);

        if (usuarioLogueado != null) {
            if (usuarioLogueado.getRol() == Rol.ADMIN) {
                mostrarMenuAdmin();
            } else {
                mostrarMenuJugador();
            }
        }
    }

    private static void mostrarMenuJugador() {
        int opcion;
        
        do {
            String menu = String.format(
                    "--- Menú de Jugador (%s) ---\n"
                    + "Puntuación Total Acumulada: %d\n"
                    + "Objetivo Global: %d puntos\n"
                    + "1. Jugar Partida (Nueva Racha)\n" 
                    + "2. Ver Ranking Global\n"
                    + "3. Cerrar Sesión\n"
                    + "----------------------------\n"
                    + "Seleccione una opción:",
                    usuarioLogueado.getNombreUsuario(),
                    usuarioLogueado.getPuntuacionTotal(),
                    LIMITE_PUNTOS);

            String input = JOptionPane.showInputDialog(null, menu, TITULO_APP, JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                opcion = 3; 
            } else {
                try {
                    opcion = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    mostrarMensaje("Opción inválida.", "Error de Entrada");
                    opcion = 0;
                }
            }

            switch (opcion) {
                case 1:
                    jugarContinuamente(); 
                    break;
                case 2:
                    mostrarRanking(); 
                    break;
                case 3:
                    usuarioLogueado = null; 
                    mostrarMenuPrincipal(); 
                    break;
                default:
                    if (opcion != 0) {
                        mostrarMensaje("Opción no reconocida.", "Error");
                    }
                    break;
            }
        } while (usuarioLogueado != null);
    }
    
    private static void mostrarMenuAdmin() {
        int opcion;
        
        do {
            String menu = String.format(
                    "--- Menú de Administrador (%s) ---\n"
                    + "Palabras en Diccionario: %d\n"
                    + "1. Listar Palabras\n"
                    + "2. Añadir Palabra\n"
                    + "3. Eliminar Palabra\n"
                    + "4. Cerrar Sesión\n"
                    + "-----------------------------------\n"
                    + "Seleccione una opción:",
                    usuarioLogueado.getNombreUsuario(),
                    Diccionario.obtenerTotalPalabras()); 

            String input = JOptionPane.showInputDialog(null, menu, TITULO_APP, JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                opcion = 4; 
            } else {
                try {
                    opcion = Integer.parseInt(input.trim());
                } catch (NumberFormatException e) {
                    mostrarMensaje("Opción inválida.", "Error de Entrada");
                    opcion = 0;
                }
            }

            switch (opcion) {
                case 1:
                    listarPalabras(); 
                    break;
                case 2:
                    administrarAgregarPalabra(); 
                    break;
                case 3:
                    administrarEliminarPalabra(); 
                    break;
                case 4:
                    usuarioLogueado = null; 
                    mostrarMenuPrincipal(); 
                    break;
                default:
                    if (opcion != 0) {
                        mostrarMensaje("Opción no reconocida.", "Error");
                    }
                    break;
            }
        } while (usuarioLogueado != null);
    }

    // =========================================================================================
    // II. AUTENTICACIÓN Y REGISTRO
    // =========================================================================================

    private static void iniciarSesion() {
        String nombreUsuario = JOptionPane.showInputDialog(null, "Introduce tu Nombre de Usuario o Email:", 
                            TITULO_APP + " - Login", JOptionPane.PLAIN_MESSAGE);
        
        if (nombreUsuario == null) return; 

        String contrasena = JOptionPane.showInputDialog(null, "Introduce tu Contraseña:", 
                            TITULO_APP + " - Login", JOptionPane.PLAIN_MESSAGE);
        
        if (contrasena == null) return; 
        
        Usuario user = GestorUsuarios.login(nombreUsuario, contrasena);

        if (user != null) {
            usuarioLogueado = user; 
            mostrarMensaje("¡Bienvenido, " + usuarioLogueado.getNombreUsuario() + "! Has iniciado sesión como " + usuarioLogueado.getRol() + ".", "Éxito");
        } else {
            mostrarMensaje("Credenciales incorrectas. Inténtalo de nuevo.", "Fallo de Login");
        }
    }
    
    private static void registrarUsuario() {
        mostrarMensaje("¡Bienvenido! Ingresa los datos para registrar un nuevo JUGADOR:", "Registro");

        String nombre = JOptionPane.showInputDialog("Nombre:");
        if (nombre == null) return;

        String apellido = JOptionPane.showInputDialog("Apellido:");
        if (apellido == null) return;
        
        String edadStr = JOptionPane.showInputDialog("Edad:");
        if (edadStr == null) return;
        int edad;
        try {
            edad = Integer.parseInt(edadStr.trim());
            if (edad < 18) {
                mostrarMensaje("Lo sentimos, debes ser mayor de 18 años.", "Error de Edad");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Edad no válida. Debe ser un número.", "Error de Entrada");
            return;
        }

        String email = JOptionPane.showInputDialog("Email:");
        if (email == null) return;
        if (!email.contains("@")) {
            mostrarMensaje("Formato de Email inválido.", "Error");
            return;
        }

        String nombreUsuario = JOptionPane.showInputDialog("Nombre de Usuario (Nick):");
        if (nombreUsuario == null) return;

        if (GestorUsuarios.existeUsuario(email, nombreUsuario)) {
            mostrarMensaje("El Email o Nombre de Usuario ya está en uso.", "Error de Duplicado");
            return;
        }

        String contrasena = JOptionPane.showInputDialog("Contraseña:");
        if (contrasena == null || contrasena.length() < 4) {
             mostrarMensaje("La contraseña debe tener al menos 4 caracteres.", "Error de Contraseña");
             return;
        }

        // El constructor de Usuario garantiza que puntuacionTotal comienza en 0.
        Usuario nuevoUsuario = new Usuario(nombre, apellido, edad, email, nombreUsuario, contrasena, Rol.JUGADOR);
        
        GestorUsuarios.agregarUsuario(nuevoUsuario);
        mostrarMensaje("¡Registro exitoso! Ya puedes iniciar sesión.", "Éxito");
    }

    // =========================================================================================
    // III. LÓGICA DE JUEGO (Orquestación del Motor)
    // =========================================================================================
    
    /**
     * FUNCIÓN: Ejecuta una única partida y calcula el resultado y la puntuación.
     * * @return RegistroPartida que contiene el resultado y los puntos obtenidos.
     */
    private static RegistroPartida ejecutarPartidaUnica() throws Exception {
        
        Palabra palabra = Diccionario.obtenerPalabraAleatoria();

        if (palabra == null) {
            throw new Exception("Diccionario vacío");
        }

        JuegoAhorcado juego = new JuegoAhorcado(usuarioLogueado, palabra);
        
        mostrarMensaje(crearMensajeJuegoTextoPlano(juego, "¡Adivina la palabra!"), "INICIO DE PARTIDA");

        while (!juego.juegoTerminado()) {
            
            // Se muestra el estado del juego y la solicitud de entrada en el mismo diálogo.
            String mensajePrompt = crearMensajeJuegoTextoPlano(juego, "Turno. Introduce una letra o la palabra completa:");
            
            String entrada = JOptionPane.showInputDialog(null, 
                            mensajePrompt, 
                            TITULO_APP + " - Ingresar Intento", 
                            JOptionPane.QUESTION_MESSAGE);

            if (entrada == null) {
                int confirmar = JOptionPane.showConfirmDialog(null, 
                            "¿Estás seguro de que quieres ABANDONAR la partida? Esto contará como DERROTA.", 
                            "Confirmar Abandono", 
                            JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    juego.setIntentosRestantes(0); 
                    break; 
                }
                continue; 
            }
            
            String entradaNormalizada = entrada.trim().toUpperCase();

            if (entradaNormalizada.isEmpty()) {
                mostrarMensaje("ADVERTENCIA: Entrada vacía.", "Advertencia");
                continue;
            }

            String resultadoTurno;
            
            if (entradaNormalizada.length() == 1) {
                resultadoTurno = juego.procesarLetra(entradaNormalizada.charAt(0));
            } else {
                resultadoTurno = juego.procesarPalabra(entradaNormalizada);
            }
            
            if(!juego.juegoTerminado()) {
                mostrarMensaje(crearMensajeJuegoTextoPlano(juego, resultadoTurno), "Resultado del Turno");
            }
        } 

        juego.finalizarJuego(); 
        
        boolean victoria = juego.getPalabraOculta().equals(juego.getPalabraSecreta().getPalabra());
        
        String resultado;
        int puntos;
        
        if (victoria) {
            resultado = "GANADA";
            puntos = PUNTOS_GANAR;
            mostrarMensaje("¡FELICIDADES! ¡Has ganado esta ronda! (+"+puntos+" pts)", "VICTORIA");
        } else {
            resultado = "PERDIDA";
            puntos = PUNTOS_PERDER;
            mostrarMensaje("Oh no, has perdido. La palabra era: " + palabra.getPalabra() + " ("+puntos+" pts)", "DERROTA");
        }
        
        // Lógica clave de ACUMULACIÓN de la racha:
        // Se suma/resta los nuevos puntos a la puntuación temporal de la racha (que comenzó en 0).
        usuarioLogueado.setPuntuacionTotal(usuarioLogueado.getPuntuacionTotal() + puntos);
        
        return new RegistroPartida(palabra.getPalabra(), resultado, puntos);
    }
    
    /**
     * FUNCIÓN: Bucle principal de juego continuo para el Jugador (la "Racha").
     */
    private static void jugarContinuamente() {
        // 1. Guardar la puntuación REAL del usuario.
        int puntuacionRealOriginal = usuarioLogueado.getPuntuacionTotal();
        
        // 2. TEMPORALMENTE resetear la puntuación del usuario a CERO para el inicio de la racha.
        usuarioLogueado.setPuntuacionTotal(0);

        historialPartidas.clear(); 
        boolean seguirJugando = true;
        int puntuacionRachaActual; // Para usar en los mensajes de continuidad
        
        while (seguirJugando) {
            try {
                RegistroPartida registro = ejecutarPartidaUnica();
                historialPartidas.add(registro); 

                puntuacionRachaActual = usuarioLogueado.getPuntuacionTotal();

                // 3. Verificar si se alcanzó el límite de puntos de la racha.
                if (puntuacionRachaActual >= LIMITE_PUNTOS) {
                    mostrarMensaje("¡Felicidades, " + usuarioLogueado.getNombreUsuario() + "!\n"
                            + "Has alcanzado o superado el límite de " + LIMITE_PUNTOS + " puntos en esta racha.\n"
                            + "Tu puntuación total obtenida: " + puntuacionRachaActual, "OBJETIVO CUMPLIDO");
                    seguirJugando = false; 
                } else {
                    // 4. Preguntar si quiere seguir jugando.
                    String mensajeContinuar = String.format(
                            "PUNTUACIÓN ACTUAL (Racha): %d / %d\n"
                            + "¿Quieres jugar otra partida?", 
                            puntuacionRachaActual, // Muestra la puntuación ACUMULADA de la racha.
                            LIMITE_PUNTOS);
                    
                    int confirmar = JOptionPane.showConfirmDialog(null, 
                            mensajeContinuar, 
                            TITULO_APP + " - Continuar", 
                            JOptionPane.YES_NO_OPTION);
                    
                    if (confirmar == JOptionPane.NO_OPTION || confirmar == JOptionPane.CLOSED_OPTION) {
                        seguirJugando = false; 
                    }
                }

            } catch (Exception e) {
                mostrarMensaje("Error al iniciar la partida: " + e.getMessage(), "Error Crítico");
                seguirJugando = false;
                e.printStackTrace(); 
            }
        }
        
        // 5. Obtener los puntos finales ganados en esta racha.
        int puntosGanadosEnRacha = usuarioLogueado.getPuntuacionTotal();
        
        // 6. RESTAURAR la puntuación global original del usuario.
        usuarioLogueado.setPuntuacionTotal(puntuacionRealOriginal);
        
        // 7. AÑADIR los puntos de la racha a la puntuación real global y actualizar.
        usuarioLogueado.setPuntuacionTotal(puntuacionRealOriginal + puntosGanadosEnRacha);
        
        mostrarMensaje("Puntuación Global Actualizada: " + usuarioLogueado.getPuntuacionTotal() + "\n"
                    + "Puntos ganados en esta racha: " + puntosGanadosEnRacha, "Racha Terminada");
        
        // LLAMADA CLAVE: Se muestra el historial de la racha en la CONSOLA
        if (!historialPartidas.isEmpty()) {
            mostrarHistorialPartidas();
        } else {
            mostrarMensaje("No se jugó ninguna partida en esta sesión.", "Partida Terminada");
        }
    }
    
    // -----------------------------------------------------------------------------------------
    // IV. ADMINISTRACIÓN Y REPORTES
    // -----------------------------------------------------------------------------------------
    
    private static void mostrarRanking() {
        String ranking = GestorUsuarios.generarRanking(); 
        mostrarMensaje("RANKING GLOBAL DE JUGADORES\n" + ranking, "Ranking");
    }

    /**
     * MÉTODO CORREGIDO: Muestra el historial en la CONSOLA, no en JOptionPane.
     */
    private static void mostrarHistorialPartidas() {
        if (historialPartidas.isEmpty()) {
            System.out.println("No hay historial de partidas para mostrar en esta sesión.");
            return;
        }
        
        StringBuilder sb = new StringBuilder(); 
        sb.append("---------------------------------------------------\n");
        sb.append("--- Historial de Partidas de Racha (Consola) ---\n");
        sb.append("---------------------------------------------------\n");
        sb.append(String.format("%-10s | %-15s | %s\n", "RESULTADO", "PALABRA", "PUNTOS"));
        sb.append("---------------------------------------------------\n");
        
        for (RegistroPartida registro : historialPartidas) {
            sb.append(registro.toString()).append("\n");
        }
        
        sb.append("---------------------------------------------------\n");
        // Muestra la puntuación global del usuario después de la racha
        sb.append("Puntuación Total Global Actual: ").append(usuarioLogueado.getPuntuacionTotal()).append(" pts\n");
        sb.append("---------------------------------------------------\n");
        
        // Imprimir en la consola (System.out.println)
        System.out.println(sb.toString());
        
        // Se puede dejar un mensaje adicional en la UI para notificar que se imprimió en consola
        mostrarMensaje("El historial detallado de la racha ha sido impreso en la CONSOLA.", "Historial en Consola");
    }

    private static void listarPalabras() {
        String lista = Diccionario.listarPalabras(); 
        mostrarMensaje("Diccionario de Palabras\n" + lista, "Diccionario");
    }

    private static void administrarAgregarPalabra() {
        String palabraStr = JOptionPane.showInputDialog("Introduce la PALABRA (solo letras, sin espacios ni tildes):").toUpperCase();
        if (palabraStr == null || palabraStr.isEmpty()) return;
        
        String pista = JOptionPane.showInputDialog("Introduce la PISTA para la palabra '" + palabraStr + "':");
        if (pista == null || pista.isEmpty()) return;
        
        Palabra nuevaPalabra = new Palabra(palabraStr, pista);
        
        Diccionario.agregarPalabra(nuevaPalabra);
        
        mostrarMensaje("Palabra procesada. Ver consola para resultado.", "Administración");
    }

    private static void administrarEliminarPalabra() {
        String palabraStr = JOptionPane.showInputDialog("Introduce la PALABRA A ELIMINAR (exacta):").toUpperCase();
        if (palabraStr == null || palabraStr.isEmpty()) return;
        
        boolean eliminado = Diccionario.eliminarPalabra(palabraStr);

        if (eliminado) {
            mostrarMensaje("La palabra '" + palabraStr + "' fue eliminada.", "Eliminación Exitosa");
        } else {
            mostrarMensaje("No se encontró la palabra '" + palabraStr + "' en el diccionario.", "Eliminación Fallida");
        }
    }

    // =========================================================================================
    // V. UTILIDADES DE UI
    // =========================================================================================

    private static void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static String crearMensajeJuegoTextoPlano(JuegoAhorcado juego, String encabezado) {
        
        String dibujo = juego.dibujarAhorcado(JuegoAhorcado.MAX_INTENTOS - juego.getIntentosRestantes());
        
        return String.format(
            "%s\n"
            + "---------------------------------\n"
            + "Pista: %s\n\n"
            + "%s\n\n" 
            + "Palabra Oculta: %s\n"
            + "Intentos Restantes: %d / %d\n"
            + "Letras probadas: %s\n"
            + "---------------------------------",
            encabezado,
            juego.getPalabraSecreta().getPista(), 
            dibujo,
            juego.getPalabraOculta().replace("", " ").trim(), 
            juego.getIntentosRestantes(),
            JuegoAhorcado.MAX_INTENTOS,
            juego.getLetrasIntentadas().toString().replace("[", "").replace("]", "")
        );
    }
}
