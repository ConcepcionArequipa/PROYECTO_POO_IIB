package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends BaseFrame {
    private JPanel jpLogin;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JButton salirButton;
    private JButton ingresarButton;

    public LoginFrame() {
        super("Iniciar Sesion",null);
        initUI(); //Conecta el diseño visual con la ventana

        // Acciones para el boton de Ingresar


    }

    @Override
    public void initUI(){
        setContentPane(jpLogin);
        //Logica de los botones
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioIngresado = txtUsuario.getText();
                String claveIngresada = new String(txtPassword.getPassword());

                //Validacion si los campos estan vacios
                if(usuarioIngresado.isEmpty() || claveIngresada.isEmpty()){

                }


            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });


    }

}
