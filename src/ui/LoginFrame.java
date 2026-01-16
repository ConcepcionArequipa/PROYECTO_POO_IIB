package ui;

import dao.UsuarioDao;
import model.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CLASE ANALISTA
 * GESTIONA LAS OPERACIONES PRINCIPALES PARA LAS LICENCIAS
 * <p>
 * MATERIA : POO
 * DOCENTE: YADIRA FRANCO
 *
 * @author Concepcion Arequipa, Patiño Josue
 * @version 1.0.2
 * @see <a href="https://youtu.be/Fc4uFeMXBS8?si=m8ACk4XqPKF90DEp" target="_blank">Link del video</a>
 * @see <a href="https://github.com/ConcepcionArequipa/PROYECTO_POO_IIB.git" target="_blank">Repositorio en GitHub</a>
 */

public class LoginFrame extends BaseFrame {
    private JPanel jpLogin;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JButton salirButton;
    private JButton ingresarButton;
    private int intentos= 0; //Para el control de 3 intentos

    /**
     * Constructor de la ventana de login.
     * <p>
     * Inicializa la ventana con título, tamaño y posición centrada.
     * No requiere usuario porque aún no se ha iniciado sesión.
     */
    public LoginFrame() {
        super("Iniciar Sesion",null); //null porque aun no se inicia sesion
        setSize(600,600);
        setLocationRelativeTo(null);
        initUI();
    }
    /**
     * Inicializa los componentes de la interfaz de usuario.
     * <p>
     * Configura el panel principal, bordes y los listeners
     * para los botones de ingreso y salida.
     */
    @Override
    public void initUI(){
        setContentPane(jpLogin);
        jpLogin.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Logica de los botones
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Cierra el sistema
            }
        });


    }

    /**
     * Valida las credenciales del login.
     * <p>
     * Verifica que los campos no estén vacíos, consulta la base de datos
     * y redirige al menú correspondiente según el rol del usuario.
     * Maneja los intentos fallidos de autenticación.
     */
    private void login(){
        String usuarioIngresado = txtUsuario.getText();
        String claveIngresada = new String(txtPassword.getPassword());
        //Validacion si los campos estan vacios
        if(usuarioIngresado.isEmpty() || claveIngresada.isEmpty()){
            mostrarMensaje("Todos los campos deben estar completos");
            return;
        }

        UsuarioDao dao = new UsuarioDao();
        Usuario usuario = dao.login(usuarioIngresado, claveIngresada);
        // Si el usuario ingreso correctamente
        if(usuario != null){
            mostrarMensaje("Bienvenido "+usuario.getNombre());
            //Logica de roles, para abrir el menu correspondiente
            if (usuario.getRol().equalsIgnoreCase("admin")) {
                new MenuAdmin(usuario).setVisible(true);
            }
            else {
                new MenuAnalista(usuario).setVisible(true);
            }
            this.dispose(); //Cierra la ventana del login
        }

        else {

            manejarFallo();
        }

    }

    //Metodo para validar las credenciales incorrectas
    private void manejarFallo(){
        intentos++; //Va sumando los intentos
        txtUsuario.setText("");
        txtPassword.setText("");
        if(intentos >= 3){
            mostrarError("Acceso bloqueado por 3 intentos fallidos");
            txtUsuario.setEnabled(false); //Bloquea los txt
            txtPassword.setEnabled(false);
            ingresarButton.setEnabled(false);

        }
        else {
            mostrarError("Usuario o contraseña incorrecta.\nIntentos restantes:" + (3 - intentos));
        }

    }

}
