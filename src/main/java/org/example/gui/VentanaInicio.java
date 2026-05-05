package org.example.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Primera ventana que ve el usuario: elige el tipo de árbol
public class VentanaInicio extends JFrame {

    private static final Color AZUL_HEADER     = new Color(26, 35, 126);
    private static final Color VERDE_NORMAL    = new Color(56, 142, 60);
    private static final Color VERDE_HOVER     = new Color(27, 94, 32);
    private static final Color AZUL_NORMAL     = new Color(25, 118, 210);
    private static final Color AZUL_HOVER      = new Color(13, 71, 161);

    public VentanaInicio() {
        setTitle("Árbol Binario — Inicio");
        setSize(500, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}

        add(construirHeader(), BorderLayout.NORTH);
        add(construirCentro(), BorderLayout.CENTER);
    }

    private JPanel construirHeader() {
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(AZUL_HEADER);
        header.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel titulo = new JLabel("Árbol Binario");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitulo = new JLabel("Estructuras de Datos — Guía 7");
        subtitulo.setForeground(new Color(179, 198, 255));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(titulo);
        header.add(subtitulo);
        return header;
    }

    private JPanel construirCentro() {
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(new Color(245, 245, 252));
        centro.setBorder(new EmptyBorder(32, 50, 32, 50));

        JLabel pregunta = new JLabel("¿Qué tipo de árbol desea crear?");
        pregunta.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pregunta.setForeground(new Color(30, 30, 70));
        pregunta.setAlignmentX(Component.CENTER_ALIGNMENT);

        centro.add(pregunta);
        centro.add(Box.createRigidArea(new Dimension(0, 28)));

        // Fila con los dos botones grandes de selección
        JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 0));
        filaBotones.setOpaque(false);
        filaBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnNumeros = crearBotonGrande(
                "<html><center><b style='font-size:15px'>123</b><br>Números<br>enteros</center></html>",
                VERDE_NORMAL, VERDE_HOVER);
        btnNumeros.addActionListener(e -> abrirArbol("numeros"));

        JButton btnLetras = crearBotonGrande(
                "<html><center><b style='font-size:15px'>ABC</b><br>Letras<br>(caracteres)</center></html>",
                AZUL_NORMAL, AZUL_HOVER);
        btnLetras.addActionListener(e -> abrirArbol("letras"));

        filaBotones.add(btnNumeros);
        filaBotones.add(btnLetras);
        centro.add(filaBotones);

        return centro;
    }

    private JButton crearBotonGrande(String htmlTexto, Color colorNormal, Color colorHover) {
        JButton boton = new JButton(htmlTexto);
        boton.setBackground(colorNormal);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(155, 80));

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { boton.setBackground(colorHover); }
            public void mouseExited(MouseEvent e)  { boton.setBackground(colorNormal); }
        });
        return boton;
    }

    private void abrirArbol(String tipoArbol) {
        dispose();
        VentanaPrincipal ventana = new VentanaPrincipal(tipoArbol);
        ventana.setVisible(true);
    }
}
