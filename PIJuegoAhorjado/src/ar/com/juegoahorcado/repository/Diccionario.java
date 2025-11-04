package ar.com.juegoahorcado.repository;

// Importaciones necesarias para trabajar
import ar.com.juegoahorcado.domain.Palabra; // Importamos la clase Palabra del package domain
import java.util.ArrayList; // Para almacenar palabras
import java.util.Collections; // // Para hacer la lista inmutable.
import java.util.List; // Importamos la Interfaz List.
import java.util.Random; // Para seleccionar al azar

/**
 * DEFINICIÓN DE CLASE: Diccionario
 *
 * PALABRA CLAVE: public class FUNCIÓN: Crea el molde para una CLASE DE UTILIDAD
 * ESTÁTICA. Esta clase NO debe ser instanciada (no se usa 'new Diccionario()').
 * Su propósito es ser un REPOSITORIO (almacén) central para todas las palabras
 * del juego.
 *
 * CONCEPTOS POO Y DISEÑO APLICADOS: 1. Static (Estático): Todos sus miembros
 * (atributos y métodos) pertenecen a la clase y no a objetos. 2. Singleton de
 * Datos: Mantiene una única colección global de palabras. 3. Encapsulación de
 * Datos: La lista interna de palabras es 'private'.
 */
public class Diccionario {

    // ===================================================================================================
    //                          1. ATRIBUTOS ESTÁTICOS Y FINALES
    // ===================================================================================================
    // MODIFICADOR DE ACCESO: private (Encapsulación).
    // MODIFICADOR DE CLASE: static final. PERTENECE a la Clase y su referencia NO puede ser modificada.
    // TIPO DE DATO: List<Palabra> (La Interfaz List).
    // FUNCIÓN: La ÚNICA y GLOBAL lista que almacena todos los objetos 'Palabra'.
    private static final List<Palabra> palabrasDisponibles;
    // FUNCIÓN: Almacena la misma lista de arriba, pero protegida para que no se pueda modificar.
    private static final List<Palabra> palabrasInmutables;
    // MODIFICADOR DE CLASE: static final.
    // TIPO DE DATO: Random.
    // FUNCIÓN: Objeto que genera números pseudoaleatorios. Es esencial para elegir una palabra al azar.
    private static final Random rand = new Random();

    // ===================================================================================================
    //                   2. BLOQUE ESTÁTICO (Carga de Datos Inicial - Base de Datos Simulada)
    // ===================================================================================================
    /**
     * BLOQUE ESTÁTICO: PALABRA CLAVE: static { ... } FUNCIÓN: Este bloque de
     * código se ejecuta UNA SOLA VEZ, la primera vez que la JVM (Máquina
     * Virtual de Java) carga la clase 'Diccionario' en memoria. Es el lugar
     * perfecto para inicializar variables 'static final' y cargar datos fijos.
     */
    static {
        // ------------------ INICIALIZACIÓN DE LA LISTA MUTABLE ------------------
        // Creamos el objeto ArrayList concreto y se lo asignamos a una variable local para el llenado.
        List<Palabra> listaMutable = new ArrayList<>();

        // ------------------ LLENADO DE DATOS (Simulación de DB) ------------------
        // USO DE new Palabra(...): Llamamos al constructor de la clase Palabra para crear nuevos objetos.
        // El método .add() de la lista agrega estos nuevos objetos a la colección.
        // Categoría: Fundamentos POO y Clases
        listaMutable.add(new Palabra("CLASE", "La plantilla o el molde para crear objetos."));
        listaMutable.add(new Palabra("OBJETO", "Una instancia de una clase con su propio estado y comportamiento."));
        listaMutable.add(new Palabra("HERENCIA", "Mecanismo por el cual una clase hija adquiere propiedades de una clase padre."));
        listaMutable.add(new Palabra("POLIMORFISMO", "Capacidad de que una referencia de un tipo pueda tomar muchas formas."));
        listaMutable.add(new Palabra("ENCAPSULAMIENTO", "Principio de POO que protege los datos y restringe el acceso directo a atributos."));
        listaMutable.add(new Palabra("ABSTRACCION", "El proceso de mostrar solo la información esencial y relevante al usuario."));
        listaMutable.add(new Palabra("METODO", "Función definida dentro de una clase."));
        listaMutable.add(new Palabra("CONSTRUCTOR", "Método especial usado para inicializar un objeto al crearse."));
        listaMutable.add(new Palabra("INTERFAZ", "Define un contrato de métodos que las clases deben implementar."));
        listaMutable.add(new Palabra("CLASEPADRE", "Término usado para la clase de la que se hereda (Superclase)."));
        listaMutable.add(new Palabra("CLASEHIJA", "Término usado para la clase que hereda de otra (Subclase)."));
        listaMutable.add(new Palabra("SOBRECARGA", "Múltiples métodos con el mismo nombre, pero diferente lista de parámetros."));
        listaMutable.add(new Palabra("SOBRESCRITURA", "Redefinir un método heredado de una superclase en la subclase."));
        listaMutable.add(new Palabra("FINAL", "Palabra clave para hacer que una variable sea constante o una clase no sea heredable."));
        listaMutable.add(new Palabra("STATIC", "Modificador para miembros que pertenecen a la clase y no a la instancia."));
        listaMutable.add(new Palabra("PUBLIC", "Modificador de acceso más permisivo."));
        listaMutable.add(new Palabra("PRIVATE", "Modificador de acceso que restringe el uso a la propia clase."));
        listaMutable.add(new Palabra("PROTECTED", "Modificador de acceso visible dentro del paquete y por subclases."));
        listaMutable.add(new Palabra("SUPER", "Palabra clave que llama al constructor o métodos de la clase padre."));
        listaMutable.add(new Palabra("THIS", "Palabra clave que hace referencia a la instancia actual del objeto dentro de un método."));
        listaMutable.add(new Palabra("CLASEBASE", "Sinónimo de Clase Padre o Superclase."));
        listaMutable.add(new Palabra("IMPLEMENTAR", "Acción de una clase que consiste en proporcionar el cuerpo de los métodos de una interfaz."));
        listaMutable.add(new Palabra("ENUM", "Tipo de dato especial para definir un conjunto fijo de constantes."));
        listaMutable.add(new Palabra("EXTENDS", "Palabra clave que usa una clase para heredar de otra."));
        listaMutable.add(new Palabra("ABSTRACT", "Modificador para clases o métodos que no pueden ser instanciados o deben ser implementados."));
        listaMutable.add(new Palabra("OVERLOAD", "Tener múltiples constructores con diferentes argumentos."));
        listaMutable.add(new Palabra("OVERRIDING", "Nombre en inglés de la sobrescritura de métodos."));
        listaMutable.add(new Palabra("HEAP", "Área de memoria donde se almacenan los objetos en tiempo de ejecución."));
        listaMutable.add(new Palabra("STACK", "Área de memoria donde se almacenan las llamadas a métodos y variables locales."));
        listaMutable.add(new Palabra("NULL", "Valor que indica que una variable de referencia no apunta a ningún objeto."));
        listaMutable.add(new Palabra("IMMUTABLE", "Un objeto que no puede ser modificado después de su creación (ejemplo: String)."));

        // Categoría: Manejos de Estructuras y Datos
        listaMutable.add(new Palabra("ARRAY", "Estructura de datos de tamaño fijo para almacenar elementos del mismo tipo."));
        listaMutable.add(new Palabra("ARRAYLIST", "Implementación de List cuyo tamaño puede cambiar dinámicamente."));
        listaMutable.add(new Palabra("COLECCION", "Estructura para almacenar y manipular grupos de objetos, como List o Set."));
        listaMutable.add(new Palabra("LISTA", "Interfaz que mantiene el orden de inserción de los elementos."));
        listaMutable.add(new Palabra("ITERADOR", "Objeto utilizado para recorrer una colección."));
        listaMutable.add(new Palabra("CADENATEXTO", "El tipo de dato fundamental para almacenar texto, manejado por la clase String."));
        listaMutable.add(new Palabra("STRING", "Clase inmutable (objeto de referencia) utilizada para manejar secuencias de caracteres."));
        listaMutable.add(new Palabra("STRINGBUILDER", "Clase eficiente y mutable para manipular y construir cadenas de texto en Java."));
        listaMutable.add(new Palabra("APPEND", "Método de StringBuilder para añadir texto o cualquier tipo de dato al final de la cadena."));
        listaMutable.add(new Palabra("BUCLE", "Estructura de control para repetir un bloque de código."));
        listaMutable.add(new Palabra("CONDICIONAL", "Estructura que ejecuta código si se cumple una condición."));
        listaMutable.add(new Palabra("IF", "Estructura de control que evalúa una expresión booleana."));
        listaMutable.add(new Palabra("ELSE", "Bloque que se ejecuta cuando la condición de un if es falsa."));
        listaMutable.add(new Palabra("SWITCH", "Estructura de control para seleccionar entre muchas opciones basadas en un valor."));
        listaMutable.add(new Palabra("WHILE", "Ciclo que evalúa una condición antes de ejecutar el bloque de código."));
        listaMutable.add(new Palabra("FOR", "Ciclo usado frecuentemente para iterar un número fijo de veces."));
        listaMutable.add(new Palabra("DO", "Ciclo que garantiza que el bloque de código se ejecute al menos una vez."));
        listaMutable.add(new Palabra("FOREACH", "Sintaxis simplificada de ciclo para recorrer colecciones o arrays."));
        listaMutable.add(new Palabra("CONTINUE", "Instrucción que salta la iteración actual de un bucle y pasa a la siguiente."));
        listaMutable.add(new Palabra("BREAK", "Instrucción para salir inmediatamente de un bucle o bloque 'switch'."));
        listaMutable.add(new Palabra("PRIMITIVO", "Tipo de dato básico como int, boolean o char."));
        listaMutable.add(new Palabra("BOOLEAN", "Tipo de dato primitivo que solo admite los valores lógicos true o false."));
        listaMutable.add(new Palabra("CHAR", "Tipo de dato primitivo para almacenar un solo carácter Unicode."));
        listaMutable.add(new Palabra("INT", "Tipo de dato primitivo para números enteros de 32 bits."));
        listaMutable.add(new Palabra("DOUBLE", "Tipo de dato primitivo para números decimales de 64 bits."));
        listaMutable.add(new Palabra("FLOAT", "Tipo de dato primitivo para números decimales de 32 bits."));
        listaMutable.add(new Palabra("MODULO", "Operador aritmético que devuelve el resto de una división (%)."));
        listaMutable.add(new Palabra("IGUALDAD", "Operador relacional (==) que compara si dos valores son idénticos."));
        listaMutable.add(new Palabra("ASIGNACION", "Operador (=) que da un valor a una variable."));
        listaMutable.add(new Palabra("LOGICO", "Tipo de operadores como AND (&&), OR (||) o NOT (!)."));
        listaMutable.add(new Palabra("VARIABLE", "Un espacio de almacenamiento con nombre que contiene un valor."));
        listaMutable.add(new Palabra("CONSTANTE", "Término que se aplica a una variable static final cuyo valor no cambia jamás."));
        listaMutable.add(new Palabra("INICIALIZAR", "Dar un valor inicial a una variable."));
        listaMutable.add(new Palabra("DECLARAR", "Indicar el tipo y el nombre de una variable."));
        listaMutable.add(new Palabra("ORDENAR", "Acción de organizar los elementos de una colección siguiendo un criterio específico."));

        // Categoría: Herramientas Esenciales y Excepciones
        listaMutable.add(new Palabra("JAVA", "El lenguaje de programación desarrollado por Sun Microsystems."));
        listaMutable.add(new Palabra("JRE", "Acrónimo del Entorno de Ejecución de Java."));
        listaMutable.add(new Palabra("JVM", "Acrónimo de la Máquina Virtual de Java."));
        listaMutable.add(new Palabra("BYTECODE", "El código intermedio que la JVM puede ejecutar."));
        listaMutable.add(new Palabra("COMPILADOR", "Herramienta que traduce código fuente a bytecode."));
        listaMutable.add(new Palabra("PAQUETE", "Utilizado para organizar clases relacionadas y controlar los niveles de acceso."));
        listaMutable.add(new Palabra("IMPORT", "Se usa al inicio de un archivo para traer clases de otros paquetes."));
        listaMutable.add(new Palabra("MAIN", "El método inicial por donde empieza la ejecución de un programa Java."));
        listaMutable.add(new Palabra("VOID", "Indica que un método no devuelve ningún valor."));
        listaMutable.add(new Palabra("RETURN", "Palabra clave para finalizar la ejecución de un método y devolver un valor."));
        listaMutable.add(new Palabra("EXCEPCION", "Evento que interrumpe el flujo normal del programa, se maneja con try-catch."));
        listaMutable.add(new Palabra("TRY", "Bloque donde se coloca el código susceptible a errores."));
        listaMutable.add(new Palabra("CATCH", "Bloque que maneja o captura una excepción lanzada."));
        listaMutable.add(new Palabra("FINALLY", "Bloque que siempre se ejecuta, haya o no excepción."));
        listaMutable.add(new Palabra("RUNTIME", "Tipo de excepción que no es obligatoria capturar."));
        listaMutable.add(new Palabra("TOSTRING", "Método de la clase Object para obtener la representación en texto de un objeto."));
        listaMutable.add(new Palabra("GETTER", "Método público que se usa para obtener (leer) el valor de un atributo privado."));
        listaMutable.add(new Palabra("SETTER", "Método público que se usa para establecer (modificar) el valor de un atributo privado."));
        listaMutable.add(new Palabra("SCANNER", "Clase en java.util usada para obtener entrada de datos desde la consola."));
        listaMutable.add(new Palabra("JOPTIONPANE", "Clase de Swing para crear cuadros de diálogo y ventanas emergentes sencillas."));
        listaMutable.add(new Palabra("OPERADOR", "Símbolo que realiza una acción sobre uno o más valores o variables."));
        listaMutable.add(new Palabra("CASE", "Etiqueta dentro de un bloque 'switch'."));
        listaMutable.add(new Palabra("DEFAULT", "Opción que se ejecuta si ninguna otra etiqueta 'case' coincide."));
        listaMutable.add(new Palabra("ASSERT", "Se usa para verificar suposiciones internas del código."));
        listaMutable.add(new Palabra("DICCIONARIO", "Clase de colección que almacena palabras clave y pistas en tu juego."));
        listaMutable.add(new Palabra("WIDENING", "Término para la conversión implícita de tipos de menor a mayor capacidad."));
        listaMutable.add(new Palabra("SHORT", "Tipo de dato entero que usa 16 bits."));
        listaMutable.add(new Palabra("LONG", "Tipo de dato entero que usa 64 bits."));
        listaMutable.add(new Palabra("RANDOM", "Clase en java.util usada para generar números pseudoaleatorios de manera eficiente y controlada."));

        // Categoría: Conceptos de ingeniería de software
        listaMutable.add(new Palabra("DOMINIO", "El área central o modelo de negocio de una aplicación (las clases principales)."));
        listaMutable.add(new Palabra("TEST", "Archivo o clase que verifica si una pieza de código funciona correctamente."));
        listaMutable.add(new Palabra("TESTUNITARIO", "Prueba de la parte más pequeña del código (un método o una clase)."));
        listaMutable.add(new Palabra("MAVEN", "Herramienta popular para la gestión de proyectos y dependencias de Java."));
        listaMutable.add(new Palabra("IDE", "Acrónimo de Entorno de Desarrollo Integrado (Integrated Development Environment)."));
        listaMutable.add(new Palabra("DEPENDENCIA", "Una clase o biblioteca que otra clase necesita para funcionar."));
        listaMutable.add(new Palabra("REFACTORIZAR", "Reestructurar el código existente sin cambiar su comportamiento externo."));
        listaMutable.add(new Palabra("MODULAR", "Diseño que divide el programa en partes pequeñas e independientes."));
        listaMutable.add(new Palabra("DEBUG", "Proceso de encontrar y reducir errores en un programa."));

        // Categoría: Control de Versiones
        listaMutable.add(new Palabra("COMMIT", "Acción de guardar cambios en un sistema de control de versiones."));
        listaMutable.add(new Palabra("BRANCH", "Una rama de desarrollo separada en el control de versiones (Git)."));
        listaMutable.add(new Palabra("VERSIONAR", "Mantener un registro de los cambios en el código a lo largo del tiempo."));
        listaMutable.add(new Palabra("LEGIBLE", "Cualidad del código que es fácil de entender por otros desarrolladores."));
        listaMutable.add(new Palabra("ESCALABLE", "Capacidad de un sistema para manejar una mayor carga de trabajo."));
        listaMutable.add(new Palabra("ACRONIMO", "Palabra formada por las letras iniciales de otras palabras."));
        listaMutable.add(new Palabra("BACKEND", "La parte del sistema que se ejecuta en el servidor y maneja la lógica de negocio."));

        // ------------------ ASIGNACIÓN FINAL DE LA LISTA ------------------
        // Asignamos la lista LOCAL y LLENA a la variable ESTÁTICA y FINAL del diccionario.
        palabrasDisponibles = listaMutable;

        // Collections.unmodifiableList(): Crea una "vista" de la lista principal que NO permite agregar, remover o modificar elementos.
        // FUNCIÓN: Proteger la lista de datos para que no se modifique por error desde fuera.
        palabrasInmutables = Collections.unmodifiableList(palabrasDisponibles);
    }

    // ===================================================================================================
    //                      3. CONSTRUCTOR PRIVADO (Evita la Creación de Objetos)
    // ===================================================================================================
    /**
     * CONSTRUCTOR PRIVADO: PALABRA CLAVE: private Diccionario() FUNCIÓN: Evita
     * que alguien pueda crear una instancia del Diccionario usando 'new
     * Diccionario()'. Esto refuerza que la clase es solo un conjunto de
     * servicios y datos ESTÁTICOS.
     */
    private Diccionario() {
        // Constructor intencionalmente vacío.
    }

    // ===================================================================================================
    //                      4. MÉTODOS ESTÁTICOS (Funciones de Servicio)
    // ===================================================================================================
    /**
     * MÉTODO: obtenerPalabraAleatoria MODIFICADOR DE CLASE: static. Se llama
     * directamente desde la clase: Diccionario.obtenerPalabraAleatoria().
     * FUNCIÓN: Selecciona una palabra al azar para iniciar una partida.
     *
     * @return Un objeto Palabra seleccionado aleatoriamente.
     */
    public static Palabra obtenerPalabraAleatoria() {
        // Condición de guardia: Chequea si la lista está vacía.
        if (palabrasDisponibles.isEmpty()) {
            System.err.println("❌ ERROR: El diccionario está vacío. No se puede iniciar la partida.");
            return null; // Devuelve 'null' para indicar que no hay datos.
        }

        // rand.nextInt(palabrasDisponibles.size()): Llama al método 'nextInt' del objeto Random estático.
        // FUNCIÓN: Genera un número entero que va desde 0 hasta (tamaño de la lista - 1). Este es un índice válido.
        int indice = rand.nextInt(palabrasDisponibles.size());

        // .get(indice): Método de la List que retorna el elemento en la posición 'indice'.
        return palabrasDisponibles.get(indice);
    }

    /**
     * MÉTODO: agregarPalabra (Función de Administración/CRUD) FUNCIÓN: Agrega
     * una palabra nueva a la lista interna, validando que no exista un
     * duplicado.
     *
     * @param palabra El objeto Palabra a añadir.
     */
    public static void agregarPalabra(Palabra palabra) {
        // Se obtiene el texto de la palabra nueva.
        final String nuevaPalabraTexto = palabra.getPalabra();

        // .contains(palabra): Método de la List. Utiliza el método 'equals()' de la clase Palabra
        // para comparar si el objeto 'palabra' ya existe en la lista (basándose solo en el texto).
        // El '!' es el operador LÓGICO NOT: Si NO contiene la palabra, entonces...
        if (!palabrasDisponibles.contains(palabra)) {
            // .add(palabra): Agrega el objeto a la lista.
            palabrasDisponibles.add(palabra);
            System.out.println("✅ Éxito: Palabra '" + nuevaPalabraTexto + "' añadida al diccionario.");
        } else {
            // Si el .contains() devolvió 'true', significa que es un duplicado.
            System.out.println("❌ ADVERTENCIA: La palabra '" + nuevaPalabraTexto + "' ya existe en el diccionario. No se añadió.");
        }
    }

    /**
     * MÉTODO: listarPalabras FUNCIÓN: Crea y retorna un String con el listado
     * completo del contenido.
     *
     * @return String con el detalle.
     */
    public static String listarPalabras() {
        if (palabrasDisponibles.isEmpty()) {
            return "El diccionario está vacío. ¡El Administrador debe añadir palabras!";
        }

        // USO DE StringBuilder (sb): Para construir la cadena de texto de manera eficiente.
        StringBuilder sb = new StringBuilder();
        sb.append("--- LISTA DE PALABRAS Y PISTAS (Total: ").append(palabrasDisponibles.size()).append(") ---\n");

        int contador = 1;
        // BUCLE for-each: Sintaxis simplificada para recorrer colecciones (lee 'Por cada Palabra 'p' en la colección 'palabrasDisponibles'').
        for (Palabra p : palabrasDisponibles) {
            // String.format(): Permite crear una cadena de texto con formato (más limpio que concatenar con '+').
            sb.append(String.format("%d. %s (Pista: %s)\n",
                    contador++, // contador++: Primero usa el valor, LUEGO lo incrementa.
                    p.getPalabra(),
                    p.getPista()
            ));
        }

        // .toString(): Convierte el StringBuilder a un String final para retornarlo.
        return sb.toString();
    }

    /**
     * MÉTODO: eliminarPalabra (Función de Administración/CRUD) FUNCIÓN: Busca y
     * elimina una palabra de la lista basándose en el texto que se le pasa.
     *
     * @param textoPalabra El texto de la palabra a eliminar.
     * @return boolean: true si se eliminó, false si no se encontró.
     */
    public static boolean eliminarPalabra(String textoPalabra) {
        if (textoPalabra == null || textoPalabra.trim().isEmpty()) {
            return false;
        }

        final String palabraBuscada = textoPalabra.toUpperCase();

        // CREACIÓN DE UN OBJETO TEMPORAL: Se crea un objeto 'Palabra' temporal con el texto buscado.
        // FUNCIÓN: Nos permite usar el método remove(Object o) de la lista, que a su vez llama al
        // 'equals()' de la clase Palabra (el cual compara solo el texto).
        Palabra palabraAEliminar = new Palabra(palabraBuscada, "");

        // .remove(Object o): Método de la List. Busca y elimina la PRIMERA ocurrencia del objeto.
        boolean eliminado = palabrasDisponibles.remove(palabraAEliminar);

        if (eliminado) {
            System.out.println("✅ Éxito: Palabra '" + palabraBuscada + "' eliminada del diccionario.");
        } else {
            System.out.println("❌ Advertencia: Palabra '" + palabraBuscada + "' no encontrada.");
        }

        return eliminado;
    }

    /**
     * MÉTODO: obtenerTotalPalabras FUNCIÓN: Devuelve la cantidad de elementos
     * en la lista.
     *
     * @return El tamaño actual de la colección.
     */
    public static int obtenerTotalPalabras() {
        // .size(): Método de la List que retorna el número de elementos.
        return palabrasDisponibles.size();
    }

    /**
     * MÉTODO: getPalabrasInmutables FUNCIÓN: Permite obtener la lista de
     * palabras desde el exterior, pero de forma segura (sin que se puedan
     * modificar).
     *
     * @return Una List<Palabra> de solo lectura.
     */
    public static List<Palabra> getPalabrasInmutables() {
        return palabrasInmutables;
    }
}
