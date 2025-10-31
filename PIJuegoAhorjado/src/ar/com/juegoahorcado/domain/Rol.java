package ar.com.juegoahorcado.domain;

// ENUM, es básicamente una lista fija de opciones.
// La usamos acá para definir los únicos "tipos" de usuarios que
// va a tener el juego. O sos uno, o sos el otro.

/**
 * DEFINE LOS ROLES DEL JUEGO
 * Esto nos dice qué puede hacer cada usuario.
 * No es lo mismo un jugador común que un administrador
 * que tiene todos los permisos.
 */
public enum Rol {

    // -----------------------------------------------------------------
    // ACÁ DEFINIMOS LOS ÚNICOS ROLES QUE EXISTEN
    // -----------------------------------------------------------------

    /**
     * ROL JUGADOR
     * Es el usuario común y corriente.
     * Entra, juega, mira su puntaje, el ranking general y listo.
     * La mayoría de la persona va a tener este rol.
     */
    JUGADOR("Rol de Jugador"),

    /**
     * ROL ADMINISTRADOR
     * Este es el que tiene "los superpoderes".
     * Puede meter mano en las palabras del juego, manejar
     * la lista de usuarios y, en general, controlar todo el sistema.
     */
    ADMIN("Rol de Administrador");

    // =====================================================================
    // ATRIBUTO PARA DESCRIPCIÓN DEL ROL
    // =====================================================================

    // MODIFICADOR: private final (atributo inmutable y privado)
    // FUNCIÓN: Almacena una descripción legible y explicativa para cada rol.
    private final String descripcionRol;

    // =====================================================================
    // CONSTRUCTOR DEL ENUM
    // =====================================================================

    /**
     * El constructor es 'private' (privado) a propósito.
     * ¿Por qué? Para que nadie pueda "inventar" roles nuevos
     * desde otra parte del código.
     * Los únicos que valen son JUGADOR y ADMIN.
     *
     * @param descripcion Es el texto que le pasamos arriba (ej: "Rol de Administrador").
     */
    private Rol(String descripcion) {
        this.descripcionRol = descripcion; // Asigna la descripción al atributo.
    }

    // =====================================================================
    // MÉTODO GETTER PARA ACCEDER A LA DESCRIPCIÓN DEL ROL
    // =====================================================================

    /**
     * Es un 'getter' simple. Sirve para que desde otra clase
     * podamos preguntar: "Che, ¿cuál es la descripción de este rol?"
     *
     * @return Devuelve el texto lindo de la descripción (ej: "Rol de Jugador").
     */
    public String getDescripcionRol() {
        return descripcionRol;
    }
}