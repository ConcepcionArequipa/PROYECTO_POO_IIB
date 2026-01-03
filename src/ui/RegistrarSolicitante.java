package ui;

import model.Solicitante;
import model.Usuario;
import service.SolicitanteService;
import service.TramiteService;
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

    }
    @Override
    public void initUI() {
        setContentPane(panelRegistro);
        txtFecha.setEditable(false);
        txtFecha.setText(LocalDate.now().toString());

        //Listeners
        jcbLicencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Solicitante solicitante = new Solicitante();
                    solicitante.setCedula(txtCedula.getText());
                    solicitante.setNombre(txtNombre.getText());
                    solicitante.setTipoLicencia(jcbLicencia.getSelectedItem().toString());
                    SolicitanteService service = new SolicitanteService();
                    service.registrarSolicitante(solicitante);
                    mostrarMensaje("Solicitante registrado exitosamente");
                    txtCedula.setText("");
                    txtNombre.setText("");
                    jcbLicencia.setSelectedIndex(0); //Primer elemento
                }
                catch (Exception ex){
                    mostrarError(ex.getMessage());
                }
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCedula.setText("");
                txtNombre.setText("");
                jcbLicencia.setSelectedIndex(0); //Primer elemento
            }
        });
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //Cierra la ventana

            }
        });
    }

}

