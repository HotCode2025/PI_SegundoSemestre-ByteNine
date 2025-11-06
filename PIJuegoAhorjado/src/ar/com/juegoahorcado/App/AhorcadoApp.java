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
}
