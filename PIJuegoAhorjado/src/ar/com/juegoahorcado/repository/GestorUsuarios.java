package ar.com.juegoahorcado.repository;

// Importaciones necesarias para trabajar con colecciones y Streams de Java.
import ar.com.juegoahorcado.domain.Usuario; //Importarmos la clase Usuario del package domain
import ar.com.juegoahorcado.domain.Rol; //Importarmos la clase Rol del package domain
import java.util.ArrayList;
import java.util.Comparator; // Necesario para ordenar colecciones.
import java.util.List; // Interfaz que define el comportamiento de una lista (colección ordenada).
import java.util.stream.Collectors; // Necesario para transformar el Stream de vuelta a una List.

/**
 * DEFINICIÓN DE CLASE: GestorUsuarios
 *
 * FUNCIÓN: Clase de Utilidad Estática (Repositorio). Actúa como la "Base de Datos en memoria"
 * para todos los objetos Usuario (Jugadores y Administradores). Centraliza la lógica de
 * autenticación, registro, y la generación de reportes (como el Ranking).
 *
 * Concepto Clave: Singleton de Datos en memoria, accesible solo por métodos estáticos.
 */
public class GestorUsuarios {

	// ===================================================================================================
	//                          1. ATRIBUTO ESTATICO PRINCIPAL (Colección Maestra)
	// ===================================================================================================

	// MODIFICADOR DE ACCESO: private (Encapsulación).
	// MODIFICADOR DE CLASE: static. Pertenece a la CLASE. Es la única lista compartida.
	// TIPO DE DATO: List<Usuario>. Una lista dinámica que almacena solo objetos 'Usuario'.
	// FUNCIÓN: Almacén central de todos los usuarios del sistema.
	private static List<Usuario> listaUsuarios = new ArrayList<>();

	// ===================================================================================================
	//                   2. BLOQUE ESTÁTICO (Carga de Datos Inicial)
	// ===================================================================================================

	/**
	 * BLOQUE ESTÁTICO:
	 * FUNCIÓN: Este código se ejecuta UNA SOLA VEZ al iniciar el programa, garantizando
	 * que la lista 'listaUsuarios' siempre se inicialice con datos de prueba.
	 */
	static {
		// Llamamos al método que contiene la lógica de carga de datos de prueba.
		cargarDatosIniciales();
	}

	// ===================================================================================================
	//                      3. CONSTRUCTOR PRIVADO (Evita la Creación de Objetos)
	// ===================================================================================================

	/**
	 * CONSTRUCTOR PRIVADO:
	 * FUNCIÓN: Al ser 'private', impide el uso de 'new GestorUsuarios()'.
	 * Esto fuerza a que la clase se use exclusivamente como una Clase de Utilidad Estática.
	 */
	private GestorUsuarios() {}

	// ===================================================================================================
	//              4. METODOS DE INICIALIZACION Y PERSISTENCIA (Simulada)
	// ===================================================================================================

	/**
	 * MÉTODO ESTATICO: cargarDatosIniciales
	 * FUNCIÓN: Rellena la lista con objetos 'Usuario' predefinidos (Administradores y Jugadores).
	 */
	public static void cargarDatosIniciales() {
		// Condición de guardia: Solo carga datos si la lista está VACÍA, evitando duplicados en re-ejecuciones accidentales.
		if (listaUsuarios.isEmpty()) {
			
			// Creación de objetos Usuario (Administradores)
			// USO DE .add(new Usuario(...)): Llama al constructor de la clase Usuario y añade el objeto a la lista estática.
			// Se pasa el Rol.ADMIN (Constante del Enum Rol, que debe existir).
			listaUsuarios.add(new Usuario("Gabriel", "Santarena", 20, "admin1@bytenine.com", "GabiAdmin", "1234", Rol.ADMIN));
			listaUsuarios.add(new Usuario("Maximiliano", "Morales", 20, "admin2@bytenine.com", "MaxiAdmin", "2345", Rol.ADMIN));
			// ... otros administradores ...
			listaUsuarios.add(new Usuario("Ariel", "User", 35, "admin8@ahorcado.com", "ElProfeAdmin", "0101", Rol.ADMIN));

			// Creación de objetos Usuario (Jugadores)
			// Se crean variables temporales (pedro, juan) para luego agregarles partidas simuladas.
			Usuario pedro = new Usuario("Pedro", "Perez", 25, "pedro@juego.com", "Peter", "1236", Rol.JUGADOR);
			Usuario juan = new Usuario("Juan", "Lopez", 25, "juan@juego.com", "Juany", "1237", Rol.JUGADOR);

			// Finalmente, agregamos los jugadores a la lista maestra.
			listaUsuarios.add(pedro);
			listaUsuarios.add(juan);
		}
		System.out.println("✅ ¡Listo! Usuarios (ADMINS y Jugadores) y ranking inicial cargados. ¡A jugar! 🎉");
	}

	/**
	 * MÉTODO ESTATICO: guardarDatos
	 * FUNCIÓN: Simula la persistencia de datos (guardado).
	 */
	public static void guardarDatos() {
		System.out.println("💾 Datos guardados en la nube (simulado). ¡Tu progreso está seguro! 🔒");
		// NOTA: En este contexto, solo es un mensaje de consola.
	}

	// ===================================================================================================
	//              5. METODOS DE AUTENTICACIÓN Y REGISTRO (Lógica de Acceso)
	// ===================================================================================================
	
	/**
	 * MÉTODO ESTATICO: buscarUsuario
	 * FUNCIÓN: Busca un usuario usando un bucle 'for-each'. Puede buscar por String (Nick) o Integer (ID).
	 *
	 * @param identificador El nombre de usuario (String) o el ID (int) a buscar.
	 * @return El objeto Usuario encontrado o 'null' si no existe.
	 */
	public static Usuario buscarUsuario(Object identificador) {
		// Bucle for-each: Recorre la lista estática.
		for (Usuario usuario : listaUsuarios) {
			// instanceof: Operador que verifica si el objeto 'identificador' es del tipo 'String'.
			if (identificador instanceof String && usuario.getNombreUsuario().equalsIgnoreCase((String) identificador)) {
				// .equalsIgnoreCase(): Compara cadenas ignorando mayúsculas/minúsculas.
				return usuario; // Retorna el objeto Usuario encontrado.
			}
			// instanceof: Verifica si el objeto 'identificador' es del tipo 'Integer'.
			if (identificador instanceof Integer && usuario.getId() == (int) identificador) {
				// (int) identificador: Se hace un 'cast' (conversión explícita) al tipo int.
				return usuario;
			}
		}
		return null; // Si el bucle termina sin encontrar nada.
	}
	
	/**
	 * MÉTODO ESTATICO: existeUsuario (Validación de registro)
	 * FUNCIÓN: Evita duplicados de Email o Nombre de Usuario.
	 */
	public static boolean existeUsuario(String email, String nombreUsuario) {
		for (Usuario usuarioEnLista : listaUsuarios) {
			// Lógica OR (||): Si el Email coincide O el Nick coincide, retorna TRUE (existe).
			if (usuarioEnLista.getEmail().equalsIgnoreCase(email) || usuarioEnLista.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
				return true;
			}
		}
		return false; // Si el bucle termina, no se encontró ningún duplicado.
	}

	/**
	 * MÉTODO ESTATICO: login (Autenticación)
	 * FUNCIÓN: Valida credenciales.
	 * @return El objeto Usuario si la autenticación es exitosa, o 'null' si falla.
	 */
	public static Usuario login(String nombreUsuario, String contrasena) {
		for (Usuario usuarioEnLista : listaUsuarios) {
			// Lógica de Coincidencia de Nick O Email.
			boolean coincideCredencial = usuarioEnLista.getNombreUsuario().equalsIgnoreCase(nombreUsuario) || usuarioEnLista.getEmail().equalsIgnoreCase(nombreUsuario);
			
			// Lógica AND (&&): Debe coincidir la credencial Y la contraseña.
			// .equals(): Compara Strings (la contraseña debe ser idéntica, sensible a mayúsculas).
			if (coincideCredencial && usuarioEnLista.getContrasena().equals(contrasena)) {
				return usuarioEnLista; // Autenticación exitosa.
			}
		}
		return null; // Fallo de autenticación.
	}

	/**
	 * MÉTODO ESTATICO: agregarUsuario
	 * FUNCIÓN: Añade un nuevo objeto Usuario y llama al método de guardado simulado.
	 */
	public static void agregarUsuario(Usuario nuevoUsuario) {
		listaUsuarios.add(nuevoUsuario);
		guardarDatos();
	}
	
	// ===================================================================================================
	//              6. METODOS DE CONSULTA Y RANKING
	// ===================================================================================================

	/**
	 * MÉTODO ESTATICO: generarRanking (Uso de Streams de Java)
	 * FUNCIÓN: Crea una lista ordenada de Jugadores (solo Rol.JUGADOR) por su puntuación.
	 *
	 * @return Cadena de texto formateada con el ranking.
	 */
	public static String generarRanking() {
		
		// PASO 1: Filtrado (Uso de Stream API)
		List<Usuario> rankingJugadores = listaUsuarios.stream() // Inicia un 'stream' (flujo) de datos desde la lista.
				// .filter(condición): Solo permite pasar al flujo a los objetos que cumplen la condición.
				// Condición: usuario.getRol() == Rol.JUGADOR (Solo usuarios que tienen el rol de Jugador).
				.filter(usuario -> usuario.getRol() == Rol.JUGADOR)
				// .collect(Collectors.toList()): Finaliza el flujo y convierte los elementos filtrados en una nueva lista.
				.collect(Collectors.toList());

		if (rankingJugadores.isEmpty()) {
			return "No hay suficientes jugadores registrados para mostrar un ranking.";
		}
                
		rankingJugadores.sort(Comparator.comparingInt(Usuario::getPuntuacionTotal).reversed());

		// PASO 3: Generación del Reporte (Mismo patrón que en Diccionario/Usuario)
		StringBuilder sb = new StringBuilder();
		sb.append("🏆 RANKING GLOBAL DE JUGADORES 🏆\n");
		sb.append("=========================================\n");

		// Bucle 'for' tradicional para incluir el número de posición.
		for (int posicionActual = 0; posicionActual < rankingJugadores.size(); posicionActual++) {
			Usuario jugadorActual = rankingJugadores.get(posicionActual);
                        
			sb.append(String.format("#%d. %s | Puntos: %d\n",
				(posicionActual + 1), // La posición se muestra desde 1.
				jugadorActual.getNombreUsuario(),
				jugadorActual.getPuntuacionTotal()
			));
		}

		return sb.toString();
	}
	
	// Métodos adicionales para Administración (CRUD) - Comentados aquí para simplicidad
	
	public static List<Usuario> getTodosLosUsuarios() {
		// Crea una nueva lista con los contenidos de la lista estática.
		// FUNCIÓN: Retorna una copia para evitar que una clase externa modifique la lista original.
		return new ArrayList<>(listaUsuarios);
	}

	public static boolean eliminarUsuario(Usuario usuarioAEliminar) {
		boolean eliminado = listaUsuarios.remove(usuarioAEliminar);
		if (eliminado) {
			guardarDatos();
		}
		return eliminado;
	}

	public static void agregarAdministrador(Usuario nuevoAdmin) {
		if (nuevoAdmin.getRol() != Rol.ADMIN) {
			System.err.println("❌ Error: Solo se pueden agregar usuarios con Rol.ADMIN.");
			return;
		}
		agregarUsuario(nuevoAdmin);
	}
}