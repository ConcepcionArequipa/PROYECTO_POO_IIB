package ui;

import javax.swing.*;
import java.awt.*;


 // Clase Administrador: Hereda Analista y añade gestión de usuarios y reportes

public class MenuAdmin extends MenuAnalista {

    public MenuAdmin() {
        // Llama al constructor del padre (MenuAnalista)
        // Esto carga el .form, la tabla y los filtros automáticamente
        super();

        // Admin sí tiene permisos PUEDE VER LOS BOTONES
        btnGestionarUsuario.setVisible(true);
        btnReporteAdmin.setVisible(true);

        // Cambiamos el título para diferenciar la sesión
        setTitle("SISTEMA DE LICENCIAS - PANEL DE ADMINISTRADOR");

    }
}