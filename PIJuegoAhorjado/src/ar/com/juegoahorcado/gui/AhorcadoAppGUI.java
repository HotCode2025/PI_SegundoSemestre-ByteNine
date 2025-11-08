package ar.com.juegoahorcado.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Clase: AhorcadoAppGUI
 * FUNCIÓN: Punto de entrada para la opción gráfica del juego Ahorcado.
 * Permite elegir entre iniciar sesión o registrarse, simulando la selección de opción 2 GUI.
 */
public class AhorcadoAppGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaInicioGUI().setVisible(true));
    }
}
