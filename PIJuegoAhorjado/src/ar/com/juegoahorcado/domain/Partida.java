
package ar.com.juegoahorcado.domain;

public class Partida {
    
    // =====================================================================
    // 1. ATRIBUTOS DE INSTANCIA (Propiedades del OBJETO)
    // =====================================================================

    private String palabraAdivinada; // La palabra que el usuario debía adivinar
    private boolean resultado;        // true = ganó, false = perdió
    private int puntos;               // Puntos obtenidos en esta partida

    // =====================================================================
    // 2. CONSTRUCTOR
    // =====================================================================

    /**
     * @param palabraAdivinada La palabra que se intentó adivinar
     * @param resultado true si ganó, false si perdió
     * @param puntos Puntos obtenidos en esta partida
     */
    public Partida(String palabraAdivinada, boolean resultado, int puntos) {
        this.palabraAdivinada = palabraAdivinada.toUpperCase(); // Normalizamos a mayúsculas
        this.resultado = resultado;
        this.puntos = puntos;
    }

    // =====================================================================
    // 3. MÉTODOS GETTERS (LECTURA DE ATRIBUTOS)
    // =====================================================================

    public String getPalabraAdivinada() {
        return this.palabraAdivinada; // Retorna la palabra de la partida
    }

    public boolean getResultado() {
        return this.resultado; // Retorna el resultado (true = ganó, false = perdió)
    }

    public int getPuntos() {
        return this.puntos; // Retorna los puntos obtenidos
    }

    // =====================================================================
    // 4. MÉTODOS SETTERS (MODIFICACIÓN DE ATRIBUTOS)
    // =====================================================================

    public void setPalabraAdivinada(String palabraAdivinada) {
        this.palabraAdivinada = palabraAdivinada.toUpperCase(); // Cambia la palabra y normaliza
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado; // Cambia el resultado de la partida
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos; // Cambia los puntos obtenidos
    }

    // =====================================================================
    // 5. MÉTODO toString()
    // =====================================================================

    @Override
    public String toString() {
        String estado = this.resultado ? "GANADA" : "PERDIDA"; // Convertimos boolean a texto
        return "Partida [Palabra=" + this.palabraAdivinada + ", Resultado=" + estado
                + ", Puntos=" + this.puntos + "]";
    }
}
