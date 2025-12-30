import javax.swing.SwingUtilities;
import model.Usuario;
import ui.MenuAnalista;


public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Usuario usuarioPrueba = new Usuario();
            usuarioPrueba.setIdUsuario(1);
            usuarioPrueba.setNombre("Analista Test");
            usuarioPrueba.setUsuario("analista");
            usuarioPrueba.setRol("analista");
            usuarioPrueba.setActivo(true);

            new MenuAnalista(usuarioPrueba).setVisible(true);
        });
    }
}