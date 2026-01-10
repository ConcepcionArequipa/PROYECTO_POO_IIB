package ui;

import model.Usuario;

import model.Requisito;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import service.RequisitosService;


public class VerificarRequisito extends BaseDialogo {
    private JButton REGRESARButton;
    private JCheckBox CBcertificado;
    private JCheckBox CBpago;
    private JButton APROBARButton;
    private JButton RECHAZARButton;
    private JCheckBox CBmultas;
    private JTextField txtObservaciones;
    private JPanel panelVerificacion;
    private int tramiteId;

    public VerificarRequisito(Frame parent,Usuario usuario,int tramiteId) {
        super(parent,"Verificacion de requisitos del solicitante",usuario);
        this.tramiteId = tramiteId;
        initUI();
        setVisible(true);

    }

    @Override
    public void initUI() {
        setContentPane(panelVerificacion);
        pack();
        setLocationRelativeTo(getParent());
        APROBARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!CBcertificado.isSelected() || !CBpago.isSelected() || !CBmultas.isSelected()) {
                        mostrarError("Debe marcar todos los requisitos para aprobar");
                        return;
                    }
                    Requisito requisito = new Requisito();
                    requisito.setTramiteId(tramiteId);
                    requisito.setCertificadoMedico(CBcertificado.isSelected());
                    requisito.setPago(CBpago.isSelected());
                    requisito.setMultas(CBmultas.isSelected());
                    requisito.setObservaciones(txtObservaciones.getText());

                    RequisitosService requisitoService = new RequisitosService();
                    requisitoService.verificarRequisitos(requisito);

                    mostrarMensaje("Requisitos aprobados exitosamente");
                    CBcertificado.setSelected(false);
                    CBpago.setSelected(false);
                    CBmultas.setSelected(false);
                    txtObservaciones.setText("");

                   dispose(); //Cerrar

                }catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

        RECHAZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    //Crear el objeto requisito
                    Requisito requisito = new Requisito();
                    requisito.setTramiteId(tramiteId);
                    requisito.setCertificadoMedico(CBcertificado.isSelected());
                    requisito.setPago(CBpago.isSelected());
                    requisito.setMultas(CBmultas.isSelected());
                    requisito.setObservaciones(txtObservaciones.getText());

                    //LLamar a service

                    RequisitosService requisitoService = new RequisitosService();
                    requisitoService.rechazarRequisitos(requisito);
                    mostrarMensaje("EL tramite fue rechazado correctamente");
                    CBcertificado.setSelected(false);
                    CBpago.setSelected(false);
                    CBmultas.setSelected(false);
                    txtObservaciones.setText("");
                    dispose();

                }
                catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }



            }
        });

        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean hayCambios =
                        CBcertificado.isSelected() ||
                                CBpago.isSelected() ||
                                CBmultas.isSelected() ||
                                !txtObservaciones.getText().trim().isEmpty();

                if (hayCambios) {
                    int opcion = JOptionPane.showConfirmDialog(
                            VerificarRequisito.this,
                            " Esta seguro de salir?",
                            "Confirmar",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (opcion != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                dispose();
            }
        });

    }
}
