package ui;

import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class RegistrarSolicitante extends BaseFrame{
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JButton btnRegresar;
    private JPanel panelRegistro;
    private JComboBox jcbLicencia;
    private JTextField txtFecha;

    public RegistrarSolicitante(Usuario usuario) {
        super("Registro de Solicitante",usuario);
        jcbLicencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    @Override
    public void initUI() {
        setContentPane(panelRegistro);
        txtFecha.setEditable(false);
        txtFecha.setText(LocalDate.now().toString());
    }

}

