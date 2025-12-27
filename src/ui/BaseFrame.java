package ui;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

    public BaseFrame(String titulo) {
        setTitle(titulo);
        setSize(900, 600);
        setResizable(false); //No cambia el tamaño
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cierra toda la aplicacion
    }


     // Metodo para mostrar mensajes de alerta de forma rápida

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


    // Metodo para regresar al Login (Cerrar sesión)

    public void regresarLogin() {
        new LoginFrame().setVisible(true);
        this.dispose();
    }
}