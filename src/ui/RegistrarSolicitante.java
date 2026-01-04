package ui;

import model.Solicitante;
import model.Usuario;
import service.SolicitanteService;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
    private JFormattedTextField Fe_Nacimiento;


    public RegistrarSolicitante(Usuario usuario) {
        super("Registro de Solicitante",usuario);
        initUI();

    }
    @Override
    public void initUI() {
        setContentPane(panelRegistro);
        try {
            MaskFormatter mask = new MaskFormatter("####-##-##");
            mask.setPlaceholderCharacter('_');
            Fe_Nacimiento.setFormatterFactory(new DefaultFormatterFactory(mask));
        } catch (Exception e) {
            mostrarError("Error al configurar la fecha");
        }


        txtFecha.setEditable(false);
        txtFecha.setText(LocalDate.now().toString());

        //Listeners
        jcbLicencia.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) {} });


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cedula = txtCedula.getText().trim();
                    String nombre = txtNombre.getText().trim();

                    if (cedula.isEmpty()) {
                        mostrarError("Campo Cedula vacio o Menor a 10 Digitos.");
                        return;
                    }

                    if (nombre.isEmpty()) {
                        mostrarError("Campo Nombre vacio.");
                        return;
                    }


                    if (Fe_Nacimiento.getText().contains("_")) {
                        mostrarError("Complete correctamente la fecha de nacimiento.");
                        return;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d1 = sdf.parse(Fe_Nacimiento.getText());


                    Solicitante solicitante = new Solicitante();
                    solicitante.setCedula(txtCedula.getText());
                    solicitante.setNombre(txtNombre.getText());
                    solicitante.setFechaNacimiento(d1);
                    solicitante.setTipoLicencia(jcbLicencia.getSelectedItem().toString());
                    SolicitanteService service = new SolicitanteService();
                    service.registrarSolicitante(solicitante);

                    mostrarMensaje("Solicitante registrado exitosamente");
                    txtCedula.setText("");
                    txtNombre.setText("");
                    Fe_Nacimiento.setValue(null);
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

