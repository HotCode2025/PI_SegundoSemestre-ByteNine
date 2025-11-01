package ar.com.juegoahorcado.domain;

// ===================================================================================================
// IMPORTACIONES
// ===================================================================================================
// La palabra "import" en Java se utiliza para traer clases o bibliotecas externas que necesitamos utilizar en nuestro código.
// En este caso, importamos "java.util.Objects", que es parte de las bibliotecas estándar de Java.
// La clase "Objects" facilita la creación de un código hash único para objetos, lo que es importante 
// cuando queremos comparar o almacenar objetos de manera eficiente en estructuras como HashSet o HashMap.
import java.util.Objects; 

/**
 * CLASE: Palabra
 * ===================================================================================================
 * Esta clase representa la palabra secreta dentro del juego del ahorcado, junto con la pista 
 * asociada a esa palabra. 
 *  
 * Los conceptos clave de la programación orientada a objetos (POO) aplicados en esta clase son:
 * 1. Encapsulación: Todos los atributos son 'private', lo que significa que solo pueden ser accedidos 
 *    dentro de esta clase. Usamos getters para acceder a ellos de manera controlada.
 * 2. Inmutabilidad: Los atributos 'palabra' y 'pista' son declarados como 'final', lo que significa 
 *    que una vez asignados no se pueden modificar, asegurando que el objeto no cambie después de su creación.
 * 3. Identidad definida: Se sobrescriben los métodos 'equals()' y 'hashCode()' para definir cuándo dos objetos 
 *    de tipo 'Palabra' son considerados iguales. Esto se hace comparando solo el texto de la palabra secreta 
 *    (sin importar la pista).
 */
public class Palabra {

    // ===================================================================================================
    // 1. ATRIBUTOS (O CAMPOS DE INSTANCIA)
    // ===================================================================================================
    // Los atributos (también llamados campos) son las variables que contienen el estado del objeto.
    // En esta clase, los atributos representan la palabra secreta y la pista asociada.
    
    // 'private' significa que solo esta clase podrá acceder a estos atributos directamente, 
    // garantizando que no se modifiquen de forma externa (encapsulación).
    
    // 'final' significa que una vez asignados, estos valores no pueden ser cambiados (inmutabilidad).

    // 'String' es un tipo de dato en Java que representa una secuencia de caracteres (texto).
    private final String palabra; // Atributo que almacena la palabra secreta que el jugador debe adivinar.
    
    // 'pista' es una cadena de texto que ayudará al jugador a adivinar la palabra secreta.
    private final String pista; // Atributo que almacena la pista asociada a la palabra secreta.

    // ===================================================================================================
    // 2. CONSTRUCTOR
    // ===================================================================================================
    // El constructor es un método especial que se ejecuta cuando creamos un nuevo objeto de la clase.
    // Este constructor recibe dos parámetros: 'palabra' y 'pista', y les asigna los valores a los atributos internos.
    // Los constructores nos permiten inicializar el objeto con valores específicos cuando lo creamos.

    /**
     * Constructor de la clase Palabra.
     * Este constructor recibe dos parámetros: 'palabra' y 'pista', y los asigna a los atributos internos de la clase.
     * La palabra es convertida a mayúsculas para evitar diferencias al comparar letras en el juego.
     * 
     * @param palabra La palabra secreta que debe adivinar el jugador.
     * @param pista La pista que ayuda al jugador a adivinar la palabra secreta.
     */
    public Palabra(String palabra, String pista) {
        
        // "this.palabra" hace referencia al atributo 'palabra' del objeto actual. 
        // Se utiliza 'this' para diferenciarlos, ya que el parámetro del constructor y el atributo tienen el mismo nombre.
        // 'palabra' (sin 'this.') es el parámetro que se pasa al constructor.
        // 'toUpperCase()' convierte el texto a mayúsculas, lo que garantiza que el juego no sea sensible a mayúsculas/minúsculas.
        // Ejemplo: "Perro" y "perro" se consideran la misma palabra.
        this.palabra = palabra.toUpperCase(); 

        // La pista no necesita ser convertida a mayúsculas porque es solo texto informativo para el jugador.
        // Asignamos directamente el valor de 'pista' al atributo correspondiente.
        this.pista = pista; 
    }

    // ===================================================================================================
    // 3. MÉTODOS GETTERS (LECTURA DE ATRIBUTOS)
    // ===================================================================================================
    // Los métodos 'getter' son utilizados para obtener (leer) los valores de los atributos privados de un objeto.
    // Estos métodos permiten acceder a los valores de los atributos de forma controlada, manteniendo la encapsulación.

    /**
     * Método getter para el atributo 'palabra'.
     * Permite acceder a la palabra secreta que el jugador debe adivinar.
     * 
     * @return La palabra secreta en formato mayúsculas.
     */
    public String getPalabra() {
        // 'this.palabra' devuelve el valor del atributo 'palabra' del objeto actual.
        return this.palabra; // Devuelve la palabra secreta.
    }

    /**
     * Método getter para el atributo 'pista'.
     * Permite acceder a la pista asociada a la palabra secreta.
     * 
     * @return La pista que ayuda al jugador a adivinar la palabra.
     */
    public String getPista() {
        return this.pista; // Devuelve la pista asociada a la palabra.
    }

    // ===================================================================================================
    // 4. MÉTODOS equals() Y hashCode()
    // ===================================================================================================
    // Los métodos equals() y hashCode() son fundamentales para comparar objetos en Java.
    // Se utilizan para definir cómo dos objetos de tipo 'Palabra' se comparan entre sí (si son iguales o no).
    // Estos métodos son especialmente importantes cuando trabajamos con colecciones como HashSet y HashMap.

    /**
     * Método equals() sobrescrito de la clase Object.
     * Compara si dos objetos de tipo 'Palabra' son iguales.
     * Dos objetos 'Palabra' son iguales si su atributo 'palabra' tiene el mismo valor.
     * 
     * @param o El objeto con el que se comparará el objeto actual.
     * @return true si ambos objetos son iguales, false si son diferentes.
     */
    @Override
    public boolean equals(Object o) {

        // Paso 1: Comprobación de referencia.
        // Si 'this' y 'o' apuntan al mismo objeto en memoria, entonces son iguales.
        if (this == o) return true;

        // Paso 2: Comprobación de tipo.
        // Si el objeto recibido 'o' es nulo o no pertenece a la misma clase, los objetos no son iguales.
        if (o == null || getClass() != o.getClass()) return false;

        // Paso 3: Conversión de tipo.
        // Convertimos el objeto recibido 'o' a tipo 'Palabra' para acceder a su atributo 'palabra'.
        Palabra otraPalabra = (Palabra) o;

        // Paso 4: Comparación de contenido.
        // Usamos el método 'equals()' de la clase String para comparar las dos palabras secretas.
        return this.palabra.equals(otraPalabra.palabra);
    }

    /**
     * Método hashCode() sobrescrito de la clase Object.
     * Genera un valor hash único basado en el atributo 'palabra'.
     * Este valor es usado en colecciones como HashSet y HashMap para organizar los objetos de manera eficiente.
     * 
     * @return Un valor hash basado en la palabra secreta.
     */
    @Override
    public int hashCode() {
        // 'Objects.hash()' es una forma moderna y segura de generar un valor hash para el objeto.
        // En este caso, solo usamos el atributo 'palabra' para definir la identidad del objeto.
        return Objects.hash(this.palabra); // Devuelve el valor hash basado en 'palabra'.
    }

    // ===================================================================================================
    // 5. MÉTODO toString()
    // ===================================================================================================
    // El método 'toString()' convierte el objeto en una representación en texto.
    // Es útil para depuración (debugging) o cuando necesitamos mostrar el objeto en pantalla.

    /**
     * Método toString() sobrescrito de la clase Object.
     * Genera una representación legible del objeto 'Palabra', mostrando la palabra secreta y la pista.
     * 
     * @return Una cadena de texto que muestra la palabra y su pista.
     */
    @Override
    public String toString() {
        // Usamos concatenación de cadenas para crear una representación legible del objeto.
        return this.palabra + " (Pista: " + this.pista + ")";
    }
}

