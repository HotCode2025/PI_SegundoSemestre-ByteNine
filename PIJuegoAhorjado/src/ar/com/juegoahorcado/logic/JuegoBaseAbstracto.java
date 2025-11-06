package ar.com.juegoahorcado.logic;

// ===================================================================================================
// IMPORTACIONES
// ===================================================================================================
// Importamos las clases necesarias que se encuentran en otros paquetes. 
// En este caso, 'Usuario' y 'Palabra' que están en el paquete 'ar.com.juegoahorcado.domain'.

import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.domain.Palabra;

/**
 * CLASE: JuegoBaseAbstracto
 * ===================================================================================================
 * Esta clase es la base de todos los juegos de adivinanza, proporcionando la estructura común para el flujo de juego.
 * Al ser abstracta, no podemos crear instancias directamente de ella. Las clases hijas implementarán los detalles concretos.
 * 
 * PATRÓN DE DISEÑO APLICADO: Template Method
 * ===================================================================================================
 * El patrón Template Method se utiliza para definir el esqueleto de un algoritmo en un método (ej. ejecutarJuego()) 
 * mientras que deja que las clases hijas definan los pasos concretos (métodos como 'iniciarJuego', 'jugarTurno', etc.).
 * 
 * Esta clase define los métodos abstractos que deben ser implementados por las clases hijas.
 */
public abstract class JuegoBaseAbstracto { 

    // ===================================================================================================
    // 1. ATRIBUTOS PROTEGIDOS (Visibilidad Controlada)
    // ===================================================================================================
    // Los atributos 'protected' permiten que las subclases (y clases del mismo paquete) accedan a estos valores.

    // Atributo que hace referencia al jugador actual (Usuario).
    // 'protected' hace que este atributo sea accesible tanto dentro de esta clase como en sus subclases (como JuegoAhorcado).
    protected Usuario usuarioActual;

    // Atributo que guarda la palabra secreta a adivinar (Palabra).
    protected Palabra palabraSecreta;

    // ===================================================================================================
    // 2. CONSTRUCTOR (Inicialización de la Base)
    // ===================================================================================================
    /**
     * CONSTRUCTOR PÚBLICO:
     * Este constructor es utilizado por las clases hijas para inicializar los atributos comunes del juego:
     * el jugador y la palabra secreta.
     * 
     * @param usuarioActual El usuario que juega esta partida.
     * @param palabraSecreta La palabra que se intentará adivinar.
     */
    public JuegoBaseAbstracto(Usuario usuarioActual, Palabra palabraSecreta) {
        // Asignamos los parámetros recibidos a los atributos de la clase.
        this.usuarioActual = usuarioActual; 
        this.palabraSecreta = palabraSecreta; 
    }

    // ===================================================================================================
    // 3. MÉTODOS ABSTRACTOS (A IMPLEMENTAR POR LAS CLASES HIJAS)
    // ===================================================================================================
    // Los métodos abstractos no tienen implementación aquí. Son "promesas" de que las clases hijas proporcionarán
    // su implementación específica para estos pasos del juego.

    /**
     * MÉTODO ABSTRACTO: iniciarJuego()
     * Este método debe ser implementado por las clases hijas. Define la preparación inicial del juego.
     * Ejemplo: Inicializar los contadores, la palabra oculta, etc.
     */
    public abstract void iniciarJuego(); 

    /**
     * MÉTODO ABSTRACTO: jugarTurno()
     * Define el ciclo de juego, donde el jugador realiza un intento (letra o palabra).
     * Cada turno se procesa aquí: recibir la entrada, procesarla, actualizar el estado, etc.
     * @return boolean: Devuelve 'true' para continuar, 'false' para detener el juego si ocurre un error.
     */
    public abstract boolean jugarTurno(); 

    /**
     * MÉTODO ABSTRACTO: juegoTerminado()
     * Este método debe ser implementado por las clases hijas. Se utiliza para definir las condiciones
     * que hacen que el juego termine (por ejemplo, cuando el jugador gana o pierde).
     * @return boolean: Devuelve 'true' si el juego ha terminado (ganado o perdido).
     */
    public abstract boolean juegoTerminado(); 

    /**
     * MÉTODO ABSTRACTO: finalizarJuego()
     * Este método define lo que ocurre una vez que el juego termina. Puede incluir cálculos de puntuación
     * y guardar los resultados de la partida.
     */
    public abstract void finalizarJuego();

    // ===================================================================================================
    // 4. MÉTODO FINAL CONCRETO (EL TEMPLATE METHOD)
    // ===================================================================================================
    /**
     * MÉTODO FINAL: ejecutarJuego()
     * El método 'final' define la secuencia de pasos del juego que no puede ser modificada por las clases hijas.
     * Este método es el 'esqueleto' del flujo del juego, mientras que las clases hijas implementan los pasos concretos.
     * 
     * PASOS:
     * 1. Inicializa el juego.
     * 2. Ejecuta los turnos del juego mientras el juego no haya terminado.
     * 3. Finaliza el juego al terminarlo.
     */
    public final void ejecutarJuego() {
        System.out.println("🏁 INICIANDO PARTIDA: ¡Adivina la palabra!");
        
        // PASO 1: Inicialización del juego (llama al método abstracto 'iniciarJuego' que debe ser implementado en la clase hija).
        iniciarJuego();

        // PASO 2: Bucle principal (El ciclo de juego)
        // Mientras el juego no haya terminado, seguimos ejecutando turnos.
        while (!juegoTerminado()) {
            jugarTurno();  // Llama a la implementación específica del turno en la clase hija.
        }

        // PASO 3: Finalización del juego.
        // Una vez que el juego termina, se ejecuta la lógica final.
        finalizarJuego();
        
        System.out.println("✅ PARTIDA FINALIZADA.");
    }
}
