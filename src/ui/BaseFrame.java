package ui;

import javax.swing.*;
import java.awt.*;
import model.Usuario; // ENCAPSULAMIENTO

public abstract class BaseFrame extends JFrame {
    //Para que las clases hijas sepan el rol del usuario
    protected Usuario usuarioLogueado; // Encapsulamiento para control de roles

    public BaseFrame(String titulo, Usuario usuario) {
        this.usuarioLogueado = usuario;
        //Diseño estandar
        setTitle(titulo);
        setSize(900, 600);
        setResizable(false); //No cambia el tamaño
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }




    //Metodo abstracto para obligar a las hijas a organizar su UI

    public abstract void initUI();

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Sistema", JOptionPane.ERROR_MESSAGE);
    }


    // Metodo para regresar al Login (Cerrar sesión)

    public void cerrarSesion() {
        int confirmar= JOptionPane.showConfirmDialog(this,"Esta seguro de cerrar sesion?");
        if(confirmar==JOptionPane.YES_OPTION) {
            this.dispose();
            //Aqui se instanciara el loginFrame
        }
    }
}