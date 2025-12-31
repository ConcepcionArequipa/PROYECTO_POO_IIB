import javax.swing.SwingUtilities;
import model.Usuario;
import ui.MenuAnalista;
import ui.MenuAdmin;



public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Usuario usuarioPrueba = new Usuario();
            usuarioPrueba.setIdUsuario(1);
            usuarioPrueba.setNombre("Administrador Test");
            usuarioPrueba.setUsuario("admin");
            usuarioPrueba.setRol("admin");
            usuarioPrueba.setActivo(true);

            if (usuarioPrueba.getRol().equalsIgnoreCase("admin")) {
                new MenuAdmin(usuarioPrueba).setVisible(true);
            } else if (usuarioPrueba.getRol().equalsIgnoreCase("analista")) {
                new MenuAnalista(usuarioPrueba).setVisible(true);
            }
        });

    }
}