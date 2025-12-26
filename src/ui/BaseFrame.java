package ui;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

    public BaseFrame(String titulo) {
        setTitle(titulo);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


     // Metodo para mostrar mensajes de alerta de forma rápida

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


    // Metodo para regresar al Login (Cerrar sesión)

    public void regresarLogin() {
        // new LoginFrame().setVisible(true);
        // this.dispose();
    }
}