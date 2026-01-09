package ui;

import javax.swing.*;
import java.awt.*;
import model.Usuario;

//Clase base para los dialogos emergentes
public abstract class BaseDialogo extends JDialog {
    protected Usuario usuarioLogueado; //Para cumplir con encapsulamiento
    public BaseDialogo( Frame parent, String titulo, Usuario usuario) {
        super(parent, titulo,true); // 'true' activa la modalidad
        this.usuarioLogueado = usuario;
        setSize(500, 400); //Tamaño estandar
        setResizable(false);
        setLocationRelativeTo(parent);  // Se centra sobre la ventana padre
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    public abstract void initUI();

    // Metodo para mostrar mensajes de alerta de forma rápida

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,"Informacion",JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
