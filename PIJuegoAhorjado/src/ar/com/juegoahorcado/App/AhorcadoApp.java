package ar.com.juegoahorcado.App;
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
}
