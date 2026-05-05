package org.example.gui;

import org.example.logica.ArbolBinario;
import org.example.modelo.NodoBinario;

import javax.swing.*;
import java.awt.*;

// Panel que dibuja el árbol gráficamente usando círculos y líneas
@SuppressWarnings("rawtypes")
public class PanelArbol extends JPanel {

    private ArbolBinario arbolActual;

    private static final int RADIO_NODO = 26;
    private static final int SEPARACION_VERTICAL = 75;
    private static final Color COLOR_RELLENO_NODO = new Color(21, 101, 192);
    private static final Color COLOR_BORDE_NODO = new Color(13, 71, 161);
    private static final Color COLOR_TEXTO_NODO = Color.WHITE;
    private static final Color COLOR_LINEA_CONEXION = new Color(55, 55, 55);

    public PanelArbol() {
        setBackground(new Color(250, 250, 255));
        setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200), 1));
    }

    public void actualizarArbol(ArbolBinario arbol) {
        this.arbolActual = arbol;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Mejora la calidad del dibujo (círculos y texto más suaves)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (arbolActual == null || arbolActual.estaVacio()) {
            dibujarMensajeVacio(g2d);
            return;
        }

        // Empieza a dibujar desde la raíz, centrado en la parte superior
        int centroX = getWidth() / 2;
        int inicioY = 45;
        int desplazamientoHorizontal = getWidth() / 4;

        dibujarNodoConHijos(g2d, arbolActual.obtenerRaiz(), centroX, inicioY, desplazamientoHorizontal);
    }

    // Dibuja un nodo y, de forma recursiva, sus hijos con líneas que los conectan
    @SuppressWarnings("rawtypes")
    private void dibujarNodoConHijos(Graphics2D g, NodoBinario nodo, int x, int y, int desplazamiento) {
        if (nodo == null) return;

        // Primero dibuja las líneas hacia los hijos (quedan detrás de los nodos)
        if (nodo.hijoIzquierdo != null) {
            int hijoX = x - desplazamiento;
            int hijoY = y + SEPARACION_VERTICAL;
            g.setColor(COLOR_LINEA_CONEXION);
            g.setStroke(new BasicStroke(1.5f));
            g.drawLine(x, y, hijoX, hijoY);
            dibujarNodoConHijos(g, nodo.hijoIzquierdo, hijoX, hijoY, Math.max(desplazamiento / 2, 20));
        }

        if (nodo.hijoDerecho != null) {
            int hijoX = x + desplazamiento;
            int hijoY = y + SEPARACION_VERTICAL;
            g.setColor(COLOR_LINEA_CONEXION);
            g.setStroke(new BasicStroke(1.5f));
            g.drawLine(x, y, hijoX, hijoY);
            dibujarNodoConHijos(g, nodo.hijoDerecho, hijoX, hijoY, Math.max(desplazamiento / 2, 20));
        }

        // Dibuja el círculo del nodo
        g.setColor(COLOR_RELLENO_NODO);
        g.fillOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);
        g.setColor(COLOR_BORDE_NODO);
        g.setStroke(new BasicStroke(2f));
        g.drawOval(x - RADIO_NODO, y - RADIO_NODO, RADIO_NODO * 2, RADIO_NODO * 2);

        // Escribe el valor dentro del círculo, centrado
        g.setColor(COLOR_TEXTO_NODO);
        g.setFont(new Font("Segoe UI", Font.BOLD, 12));
        FontMetrics medidas = g.getFontMetrics();
        String textoValor = String.valueOf(nodo.valor);
        int textoX = x - medidas.stringWidth(textoValor) / 2;
        int textoY = y + medidas.getAscent() / 2 - 1;
        g.drawString(textoValor, textoX, textoY);
    }

    private void dibujarMensajeVacio(Graphics2D g) {
        g.setColor(new Color(180, 180, 200));
        g.setFont(new Font("Segoe UI", Font.ITALIC, 15));
        String mensaje = "El árbol está vacío — inserte un valor para comenzar";
        FontMetrics medidas = g.getFontMetrics();
        int x = (getWidth() - medidas.stringWidth(mensaje)) / 2;
        int y = getHeight() / 2;
        g.drawString(mensaje, x, y);
    }
}
