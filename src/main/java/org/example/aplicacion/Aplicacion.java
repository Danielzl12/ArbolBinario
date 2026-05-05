package org.example.aplicacion;

import org.example.gui.VentanaInicio;

import javax.swing.*;

// Inicia la aplicación mostrando la ventana de selección de tipo de árbol
public class Aplicacion {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventanaInicio = new VentanaInicio();
            ventanaInicio.setVisible(true);
        });
    }
}
