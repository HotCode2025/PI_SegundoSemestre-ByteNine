
package ar.com.juegoahorcado.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class PanelDibujoAhorcado extends JPanel {
    
    private int erroresCometidos = 0;
    private final int maxErrores;
    
    // Colores para el estilo ByteNine
    // Color de la horca: Naranja Java (#FF8000) para reforzar la "J" de Java
    private static final Color COLOR_HORCA = new Color(255, 128, 0); 
    private static final Color COLOR_CUERPO = new Color(255, 60, 60); 
    private static final Color COLOR_CUERDA = new Color(100, 100, 100); 
    
    // Bandera para el estado de "perdido"
    private boolean juegoPerdido = false; 

    public PanelDibujoAhorcado(int maxErrores) {
        this.maxErrores = maxErrores;
        // Fondo Oscuro del panel central (coherente con VentanaJuegoGUI)
        setBackground(new Color(30, 30, 45)); 
    }

    /**
     * Establece la cantidad de errores y solicita un repintado.
     * @param errores La cantidad de partes del cuerpo dibujadas (1 a maxErrores).
     */
    public void setErroresCometidos(int errores) {
        this.erroresCometidos = errores;
        this.juegoPerdido = errores >= maxErrores; // Determina si se ha perdido
        repaint(); // Esto llama automáticamente a paintComponent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Mejora el aspecto de las líneas
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dimensiones del panel
        int w = getWidth();
        int h = getHeight();

        // Márgenes para el dibujo
        int margenX = w / 8;
        int margenY = h / 10;
        
        // --- Cálculo de Coordenadas de la Horca ---
        int baseY = h - margenY;
        int posteX = margenX;
        int altoHorca = h - 2 * margenY;
        int vigaX = posteX + altoHorca / 2; // Extremo derecho de la viga
        int vigaY = margenY;
        
        // --- Dibujo de la Horca (Estructura Fija, temática Java/Naranja) ---
        g2d.setColor(COLOR_HORCA); // Naranja de Java
        g2d.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // Línas más gruesas

        // 1. Base (horizontal)
        g2d.draw(new Line2D.Float(posteX / 2, baseY, w - margenX, baseY)); 

        // 2. Poste Vertical 
        g2d.draw(new Line2D.Float(posteX, baseY, posteX, vigaY));

        // 3. Viga Superior 
        g2d.draw(new Line2D.Float(posteX, vigaY, vigaX, vigaY));

        // 4. Soporte diagonal 
        g2d.draw(new Line2D.Float(posteX, vigaY + altoHorca / 8, posteX + altoHorca / 8, vigaY));
        
        // 5. Cuerda / Punto de Suspensión
        int cuerdaX = vigaX - altoHorca / 6;
        int longCuerdaInicial = altoHorca / 8;
        
        // **CORRECCIÓN: Soga se alarga si el juego está perdido**
        // Aumenta la longitud de la soga para el efecto de "colgado"
        int longCuerdaAjustada = juegoPerdido ? longCuerdaInicial + altoHorca / 10 : longCuerdaInicial;
        
        g2d.setColor(COLOR_CUERDA);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(new Line2D.Float(cuerdaX, vigaY, cuerdaX, vigaY + longCuerdaAjustada)); 

        // --- Cálculo de Coordenadas de la Figura ---
        int centroFiguraX = cuerdaX;
        int centroFiguraY = vigaY + longCuerdaAjustada; // Punto de inicio de la cabeza
        int radio = altoHorca / 16;
        int longCuerpo = altoHorca / 5;
        int longExtremidad = altoHorca / 7;

        // --- Dibujo del Ahorcado (Progresivo, basado en errores) ---
        g2d.setColor(COLOR_CUERPO);
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // 1. Cabeza (Error 1)
        if (erroresCometidos >= 1) {
            g2d.drawOval(centroFiguraX - radio, centroFiguraY, 2 * radio, 2 * radio);
        }

        // 2. Tronco (Error 2)
        int troncoY = centroFiguraY + 2 * radio;
        if (erroresCometidos >= 2) {
            g2d.drawLine(centroFiguraX, troncoY, centroFiguraX, troncoY + longCuerpo);
        }

        // 3. Brazo Izquierdo (Error 3)
        if (erroresCometidos >= 3) {
            g2d.drawLine(centroFiguraX, troncoY + longCuerpo / 4, centroFiguraX - longExtremidad, troncoY + longExtremidad);
        }

        // 4. Brazo Derecho (Error 4)
        if (erroresCometidos >= 4) {
            g2d.drawLine(centroFiguraX, troncoY + longCuerpo / 4, centroFiguraX + longExtremidad, troncoY + longExtremidad);
        }

        // 5. Pierna Izquierda (Error 5)
        int piernaY = troncoY + longCuerpo;
        if (erroresCometidos >= 5) {
            g2d.drawLine(centroFiguraX, piernaY, centroFiguraX - longExtremidad, piernaY + longExtremidad);
        }

        // 6. Pierna Derecha (Error 6 - Último error)
        if (erroresCometidos >= 6) {
            g2d.drawLine(centroFiguraX, piernaY, centroFiguraX + longExtremidad, piernaY + longExtremidad);
        }
    }
    
}
