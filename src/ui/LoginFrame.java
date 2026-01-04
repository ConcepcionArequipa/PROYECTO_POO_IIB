package ui;

import dao.UsuarioDao;
import model.Usuario;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends BaseFrame {
    private JPanel jpLogin;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JButton salirButton;
    private JButton ingresarButton;
    private int intentos= 0; //Para el control de 3 intentos


    public LoginFrame() {
        super("Iniciar Sesion",null); //null porque aun no se inicia sesion
        setSize(500,300);
        setLocationRelativeTo(null);
        initUI();
    }

    @Override
    public void initUI(){
        setContentPane(jpLogin);
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

    //Metodo para las acciones del boton del ingreso
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
