package ui;

import model.Usuario;

import javax.swing.*;
import java.awt.*;


 // Clase Administrador: Hereda Analista y añade gestión de usuarios y reportes

public class MenuAdmin extends MenuAnalista {

    public MenuAdmin(Usuario usuario) {
        // Llama al constructor del padre (MenuAnalista)
        // Esto carga el .form, la tabla y los filtros automáticamente
        super(usuario);

        // Admin sí tiene permisos PUEDE VER LOS BOTONES
        btnGestionarUsuario.setVisible(true);
        btnReporteAdmin.setVisible(true);

        // Cambiamos el título para diferenciar la sesión
        setTitle("SISTEMA DE LICENCIAS - PANEL DE ADMINISTRADOR");

    }
}