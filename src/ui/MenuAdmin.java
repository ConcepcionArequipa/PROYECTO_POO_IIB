package ui;

import javax.swing.*;
import java.awt.*;


 // Clase Administrador: Hereda Analista y añade gestión de usuarios y reportes

public class MenuAdmin extends MenuAnalista {

    public MenuAdmin() {
        // Llama al constructor del padre (MenuAnalista)
        // Esto carga el .form, la tabla y los filtros automáticamente
        super();

        // Cambiamos el título para diferenciar la sesión
        setTitle("SISTEMA DE LICENCIAS - PANEL DE ADMINISTRADOR");

        // Añadir las funciones exclusivas del Admin
        agregarBotonesAdmin();
    }

    private void agregarBotonesAdmin() {
        // Creamos los botones nuevos que el analista no tiene
        JButton btnGestionUsuarios = new JButton("GESTIÓN DE USUARIOS");
        JButton btnReportes = new JButton("REPORTES GENERALES");

        // Los añadimos al panel 'jpFiltros' que heredamos
        this.jpFiltros.add(new JSeparator()); // Una línea divisoria visual
        this.jpFiltros.add(btnGestionUsuarios);
        this.jpFiltros.add(btnReportes);

        // Programar las acciones de estos botones
        btnGestionUsuarios.addActionListener(e -> {
            // new GestionUsuario().setVisible(true); // Abre la ventana de gestión
        });

        btnReportes.addActionListener(e -> {
            // new ReporteAdmin().setVisible(true); // Abre la ventana de reportes
        });

        // Refrescar el panel para que los nuevos botones se dibujen
        this.jpFiltros.revalidate();
        this.jpFiltros.repaint();
    }
}