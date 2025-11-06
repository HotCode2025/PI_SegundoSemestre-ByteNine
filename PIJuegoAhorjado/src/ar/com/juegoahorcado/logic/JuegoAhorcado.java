package ar.com.juegoahorcado.logic;

import ar.com.juegoahorcado.domain.Usuario;
import ar.com.juegoahorcado.domain.Palabra;
import ar.com.juegoahorcado.domain.Partida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase JuegoAhorcado Implementación concreta del juego de Ahorcado. Gestiona
 * turnos, letras, palabras, intentos y estado de la partida.
 */
public class JuegoAhorcado extends JuegoBaseAbstracto {

    public static final int MAX_INTENTOS = 6;

    private char[] palabraOculta;
    private int intentosRestantes;
    private ArrayList<Character> letrasIntentadas;
    private boolean juegoGanado;
    private final Scanner scanner;

    public JuegoAhorcado(Usuario usuario, Palabra palabra) {
        super(usuario, palabra);
        this.intentosRestantes = MAX_INTENTOS;
        this.letrasIntentadas = new ArrayList<>();
        this.juegoGanado = false;
        this.scanner = new Scanner(System.in);
        this.palabraOculta = new char[this.palabraSecreta.getPalabra().length()];
        Arrays.fill(this.palabraOculta, '_');
    }

    @Override
    public void iniciarJuego() {
        System.out.println("¡BIENVENIDO AL AHORCADO, " + this.usuarioActual.getNombreUsuario() + "!");
        System.out.println("=================================================");
        System.out.println(this.dibujarAhorcado(MAX_INTENTOS - this.intentosRestantes));
        System.out.println("Pista: " + this.palabraSecreta.getPista());
        System.out.println("Comienzas con " + MAX_INTENTOS + " intentos. ¡Mucha suerte!");
        System.out.println(this.obtenerEstadoJuego());
    }

    @Override
    public boolean jugarTurno() {
        if (this.scanner == null) {
            System.err.println("Error interno: El escáner no está inicializado.");
            return false;
        }

        System.out.print("\nIntroduce una letra o adivina la palabra: ");
        String entrada = scanner.nextLine().trim().toUpperCase();

        if (entrada.isEmpty()) {
            System.out.println("ADVERTENCIA: Entrada vacía. Inténtalo de nuevo.");
            return true;
        }

        String mensajeResultado;
        if (entrada.length() == 1) {
            mensajeResultado = this.procesarLetra(entrada.charAt(0));
        } else {
            mensajeResultado = this.procesarPalabra(entrada);
        }

        System.out.println("-------------------------------------------------");
        System.out.println(mensajeResultado);
        System.out.println(this.obtenerEstadoJuego());
        return true;
    }

    @Override
    public boolean juegoTerminado() {
        if (new String(this.palabraOculta).equals(this.palabraSecreta.getPalabra())) {
            this.juegoGanado = true;
            return true;
        }
        if (this.intentosRestantes <= 0) {
            this.juegoGanado = false;
            return true;
        }
        return false;
    }

    @Override
    public void finalizarJuego() {
        long aciertos = new String(this.palabraOculta).chars().filter(c -> c != '_').count();
        int errores = MAX_INTENTOS - this.intentosRestantes;

        System.out.println(this.obtenerEstadoJuego());

        String mensajeFinal;
        if (this.juegoGanado) {
            mensajeFinal = "¡VICTORIA! 🎉 Has ADIVINADO la palabra:\n" + this.palabraSecreta.getPalabra();
        } else {
            mensajeFinal = "¡DERROTA! 💀 Te quedaste colgado.\n"
                    + this.dibujarAhorcado(MAX_INTENTOS)
                    + "\nLa palabra secreta era: " + this.palabraSecreta.getPalabra();
        }

        System.out.println("\n=================================================");
        System.out.println("✨ PARTIDA FINALIZADA ✨");
        System.out.println(mensajeFinal);
        System.out.println("=================================================");
    }

    public String procesarLetra(char letra) {
        if (!Character.isLetter(letra)) {
            return "ADVERTENCIA: Por favor, introduce solo letras.";
        }
        if (this.letrasIntentadas.contains(letra)) {
            return "ADVERTENCIA: Ya has intentado la letra '" + letra + "'.";
        }

        this.letrasIntentadas.add(letra);
        String palabra = this.palabraSecreta.getPalabra();
        boolean acierto = false;

        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                this.palabraOculta[i] = letra;
                acierto = true;
            }
        }

        if (!acierto) {
            this.intentosRestantes--;
            return "¡Fallaste! La letra '" + letra + "' no está. Intentos restantes: " + this.intentosRestantes;
        } else {
            return "¡Acertaste! La letra '" + letra + "' está en la palabra.";
        }
    }

    public String procesarPalabra(String intento) {
        String intentoUpper = intento.toUpperCase();
        if (intentoUpper.equals(this.palabraSecreta.getPalabra())) {
            this.palabraOculta = this.palabraSecreta.getPalabra().toCharArray();
            return "¡Increíble! Has adivinado la palabra completa.";
        } else {
            this.intentosRestantes = Math.max(0, this.intentosRestantes - 2);
            return "Intento de palabra fallido. Penalización: -2 intentos. Restantes: " + this.intentosRestantes;
        }
    }

    public String obtenerEstadoJuego() {
        int fallos = MAX_INTENTOS - this.intentosRestantes;
        StringBuilder sb = new StringBuilder();
        sb.append("\n=================================================");
        sb.append("\n").append(this.dibujarAhorcado(fallos))
                .append("\nPalabra: ").append(new String(this.palabraOculta))
                .append("\nIntentos restantes: ").append(this.intentosRestantes)
                .append("\nLetras intentadas: ").append(this.letrasIntentadas.toString().replace("[", "").replace("]", ""))
                .append("\n=================================================");
        return sb.toString();
    }

    public String dibujarAhorcado(int fallos) {
        String[] dibujos = new String[MAX_INTENTOS + 1];
        dibujos[0] = " +---------+\n |         |\n |         |\n |\n |\n=========";
        dibujos[1] = " +---------+\n O         |\n |         |\n |\n |\n=========";
        dibujos[2] = " +---------+\n O         |\n /|        |\n |\n |\n=========";
        dibujos[3] = " +---------+\n O         |\n /|\\       |\n |\n |\n=========";
        dibujos[4] = " +---------+\n O         |\n /|\\       |\n /         |\n |\n=========";
        dibujos[5] = " +---------+\n O         |\n /|\\       |\n / \\       |\n |\n=========";
        dibujos[6] = " +---------+\n O         |\n /|\\       |\n / \\       |\n / \\       |\n=========";

        int indice = Math.max(0, Math.min(fallos, MAX_INTENTOS));
        return dibujos[indice];
    }

    // ===== Getters y Setters =====
    public void setIntentosRestantes(int intentos) {
        this.intentosRestantes = intentos;
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public String getPalabraOculta() {
        return new String(this.palabraOculta);
    }

    public ArrayList<Character> getLetrasIntentadas() {
        return this.letrasIntentadas;
    }

    public Palabra getPalabraSecreta() {
        return this.palabraSecreta;
    }

    public boolean isJuegoGanado() {
        return juegoGanado;
    }

    public Partida getResultadoPartida() {
        int aciertos = (int) new String(this.palabraOculta).chars().filter(c -> c != '_').count();
        return new Partida(
                this.palabraSecreta.getPalabra(),
                this.juegoGanado,
                aciertos
        );
    }

    public void seleccionarNuevaPalabra() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
