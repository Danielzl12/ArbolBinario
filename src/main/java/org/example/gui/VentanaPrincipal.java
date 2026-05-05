package org.example.gui;

import org.example.logica.ArbolBinario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Ventana principal de la aplicación
@SuppressWarnings({"unchecked", "rawtypes"})
public class VentanaPrincipal extends JFrame {

    private final ArbolBinario arbol;
    private final String tipoArbol; // "numeros" o "letras"

    private PanelArbol panelArbol;
    private JTextArea areaResultado;
    private JTextField campoValorInsertar;
    private JTextField campoNodoBuscarNivel;

    // Paleta de colores
    private static final Color AZUL_HEADER   = new Color(26, 35, 126);
    private static final Color VERDE_NORMAL  = new Color(56, 142, 60);
    private static final Color VERDE_HOVER   = new Color(27, 94, 32);
    private static final Color AZUL_NORMAL   = new Color(25, 118, 210);
    private static final Color AZUL_HOVER    = new Color(13, 71, 161);
    private static final Color NARANJA_NORMAL = new Color(245, 124, 0);
    private static final Color NARANJA_HOVER  = new Color(191, 54, 12);
    private static final Color FONDO_RESULTADO = new Color(22, 22, 38);

    public VentanaPrincipal(String tipoArbol) {
        this.tipoArbol = tipoArbol;
        this.arbol = new ArbolBinario();
        construirVentana();
    }

    private void construirVentana() {
        setTitle("Árbol Binario");
        setSize(980, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}

        add(construirHeader(), BorderLayout.NORTH);

        panelArbol = new PanelArbol();
        add(panelArbol, BorderLayout.CENTER);

        add(construirPanelControles(), BorderLayout.EAST);
        add(construirPanelResultado(), BorderLayout.SOUTH);
    }

    private JPanel construirHeader() {
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(AZUL_HEADER);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel titulo = new JLabel("Árbol Binario");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        String textoSubtitulo = tipoArbol.equals("numeros")
                ? "Modo activo: Números enteros"
                : "Modo activo: Letras (caracteres)";
        JLabel subtitulo = new JLabel(textoSubtitulo);
        subtitulo.setForeground(new Color(179, 198, 255));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(titulo);
        header.add(subtitulo);
        return header;
    }

    private JPanel construirPanelControles() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(243, 243, 248));
        panel.setBorder(new EmptyBorder(15, 12, 15, 12));
        panel.setPreferredSize(new Dimension(210, 0));

        // --- Insertar ---
        panel.add(etiquetaSeccion("Insertar valor"));
        panel.add(espaciado(4));

        String labelCampo = tipoArbol.equals("numeros") ? "Número entero:" : "Letras (1 a 3 caracteres):";
        panel.add(etiquetaCampo(labelCampo));
        panel.add(espaciado(3));

        campoValorInsertar = nuevoCampoTexto();
        panel.add(campoValorInsertar);
        panel.add(espaciado(8));

        JButton btnInsertar = crearBoton("Insertar", VERDE_NORMAL, VERDE_HOVER);
        btnInsertar.addActionListener(e -> accionInsertar());
        panel.add(btnInsertar);

        panel.add(espaciado(15));
        panel.add(new JSeparator());
        panel.add(espaciado(10));

        // --- Recorridos ---
        panel.add(etiquetaSeccion("Recorridos"));
        panel.add(espaciado(6));

        JButton btnInOrden = crearBoton("In-orden", AZUL_NORMAL, AZUL_HOVER);
        btnInOrden.addActionListener(e -> mostrarRecorrido("in-orden"));
        panel.add(btnInOrden);
        panel.add(espaciado(5));

        JButton btnPreOrden = crearBoton("Pre-orden", AZUL_NORMAL, AZUL_HOVER);
        btnPreOrden.addActionListener(e -> mostrarRecorrido("pre-orden"));
        panel.add(btnPreOrden);
        panel.add(espaciado(5));

        JButton btnPostOrden = crearBoton("Post-orden", AZUL_NORMAL, AZUL_HOVER);
        btnPostOrden.addActionListener(e -> mostrarRecorrido("post-orden"));
        panel.add(btnPostOrden);

        panel.add(espaciado(15));
        panel.add(new JSeparator());
        panel.add(espaciado(10));

        // --- Propiedades ---
        panel.add(etiquetaSeccion("Propiedades del árbol"));
        panel.add(espaciado(6));

        JButton btnGrado = crearBoton("Grado del árbol", AZUL_NORMAL, AZUL_HOVER);
        btnGrado.addActionListener(e -> accionCalcularGrado());
        panel.add(btnGrado);
        panel.add(espaciado(5));

        JButton btnAltura = crearBoton("Altura del árbol", AZUL_NORMAL, AZUL_HOVER);
        btnAltura.addActionListener(e -> accionCalcularAltura());
        panel.add(btnAltura);
        panel.add(espaciado(10));

        panel.add(etiquetaCampo("Nodo para calcular nivel:"));
        panel.add(espaciado(3));
        campoNodoBuscarNivel = nuevoCampoTexto();
        panel.add(campoNodoBuscarNivel);
        panel.add(espaciado(5));

        JButton btnNivel = crearBoton("Calcular nivel", AZUL_NORMAL, AZUL_HOVER);
        btnNivel.addActionListener(e -> accionCalcularNivel());
        panel.add(btnNivel);

        panel.add(espaciado(15));
        panel.add(new JSeparator());
        panel.add(espaciado(10));

        // --- BFS ---
        JButton btnBFS = crearBoton("BFS — Búsqueda Amplitud", NARANJA_NORMAL, NARANJA_HOVER);
        btnBFS.addActionListener(e -> accionBFS());
        panel.add(btnBFS);

        return panel;
    }

    // Panel de resultado grande en la parte inferior
    private JPanel construirPanelResultado() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(FONDO_RESULTADO);
        panel.setBorder(new EmptyBorder(12, 18, 12, 18));
        panel.setPreferredSize(new Dimension(0, 100));

        JLabel etiquetaTitulo = new JLabel("Resultado");
        etiquetaTitulo.setForeground(new Color(120, 150, 255));
        etiquetaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));

        areaResultado = new JTextArea("Listo — inserte valores en el árbol y use los botones del panel.");
        areaResultado.setEditable(false);
        areaResultado.setBackground(FONDO_RESULTADO);
        areaResultado.setForeground(new Color(195, 240, 195));
        areaResultado.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setBorder(null);
        areaResultado.setFocusable(false);

        panel.add(etiquetaTitulo, BorderLayout.NORTH);
        panel.add(areaResultado, BorderLayout.CENTER);
        return panel;
    }

    // --- Acciones ---

    private void accionInsertar() {
        String texto = campoValorInsertar.getText().trim();
        if (texto.isEmpty()) {
            mostrarResultado("Ingrese un valor antes de insertar.");
            return;
        }

        if (tipoArbol.equals("numeros")) {
            try {
                int numero = Integer.parseInt(texto);
                arbol.insertar(numero);
                mostrarResultado("Número " + numero + " insertado en el árbol.");
            } catch (NumberFormatException ex) {
                mostrarResultado("Valor inválido. El árbol acepta solo números enteros.");
                return;
            }
        } else {
            if (texto.isEmpty() || texto.length() > 3 || !texto.chars().allMatch(Character::isLetter)) {
                mostrarResultado("Valor inválido. Ingrese entre 1 y 3 letras.");
                return;
            }
            arbol.insertar(texto);
            mostrarResultado("'" + texto + "' insertado en el árbol.");
        }

        campoValorInsertar.setText("");
        panelArbol.actualizarArbol(arbol);
    }

    private void mostrarRecorrido(String tipo) {
        if (arbol.estaVacio()) { mostrarResultado("El árbol está vacío."); return; }
        String resultado;
        switch (tipo) {
            case "in-orden":   resultado = arbol.recorridoInOrden();   break;
            case "pre-orden":  resultado = arbol.recorridoPreOrden();  break;
            case "post-orden": resultado = arbol.recorridoPostOrden(); break;
            default: return;
        }
        mostrarResultado(tipo + ":   " + resultado);
    }

    private void accionCalcularGrado() {
        if (arbol.estaVacio()) { mostrarResultado("El árbol está vacío."); return; }
        mostrarResultado("Grado del árbol: " + arbol.calcularGrado());
    }

    private void accionCalcularAltura() {
        if (arbol.estaVacio()) { mostrarResultado("El árbol está vacío."); return; }
        mostrarResultado("Altura del árbol: " + arbol.calcularAltura());
    }

    private void accionCalcularNivel() {
        String texto = campoNodoBuscarNivel.getText().trim();
        if (texto.isEmpty()) { mostrarResultado("Ingrese el valor del nodo para buscar su nivel."); return; }
        if (arbol.estaVacio()) { mostrarResultado("El árbol está vacío."); return; }

        if (tipoArbol.equals("numeros")) {
            try {
                int numero = Integer.parseInt(texto);
                int nivel = arbol.calcularNivel(numero);
                if (nivel == -1) mostrarResultado("El nodo " + numero + " no existe en el árbol.");
                else mostrarResultado("Nivel del nodo " + numero + ": " + nivel);
            } catch (NumberFormatException ex) {
                mostrarResultado("Ingrese un número entero válido para buscar.");
            }
        } else {
            if (texto.isEmpty() || texto.length() > 3) { mostrarResultado("Ingrese entre 1 y 3 letras."); return; }
            int nivel = arbol.calcularNivel(texto);
            if (nivel == -1) mostrarResultado("El nodo '" + texto + "' no existe en el árbol.");
            else mostrarResultado("Nivel del nodo '" + texto + "': " + nivel);
        }
    }

    private void accionBFS() {
        if (arbol.estaVacio()) { mostrarResultado("El árbol está vacío."); return; }
        mostrarResultado("BFS (Búsqueda en Amplitud):   " + arbol.busquedaAmplitud());
    }

    private void mostrarResultado(String mensaje) {
        areaResultado.setText(mensaje);
    }

    // --- Helpers UI ---

    private JButton crearBoton(String texto, Color colorNormal, Color colorHover) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorNormal);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(186, 36));
        boton.setPreferredSize(new Dimension(186, 36));

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { boton.setBackground(colorHover); }
            public void mouseExited(MouseEvent e)  { boton.setBackground(colorNormal); }
        });
        return boton;
    }

    private JTextField nuevoCampoTexto() {
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(186, 30));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return campo;
    }

    private JLabel etiquetaSeccion(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(40, 40, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel etiquetaCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(Color.DARK_GRAY);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private Component espaciado(int altura) {
        return Box.createRigidArea(new Dimension(0, altura));
    }
}
