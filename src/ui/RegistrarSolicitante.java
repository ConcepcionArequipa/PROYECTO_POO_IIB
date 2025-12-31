package ui;

import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrarSolicitante extends BaseFrame{
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JTextField textField1;
    private JTextField textField2;
    private JButton btnRegresar;
    private JPanel panelRegistro;
    private JComboBox jcbLicencia;

    public RegistrarSolicitante(Usuario usuario) {
        super("Registro de Solicitante",usuario);
        jcbLicencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    @Override
    public void initUI() {
        setContentPane(panelRegistro);
    }

}

