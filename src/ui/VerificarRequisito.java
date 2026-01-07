package ui;

import model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import service.TramiteService;

import dao.TramiteDao;

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
    }

    @Override
    public void initUI() {
        setContentPane(panelVerificacion);
        APROBARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    boolean certificadoMedico=CBcertificado.isSelected();
                    boolean pago=CBpago.isSelected();
                    boolean multas=CBmultas.isSelected();

                   TramiteService tramiteService = new TramiteService();

                   tramiteService.validarRequisitos(tramiteId,certificadoMedico,pago,multas);

                   mostrarMensaje("Requisitos aprobados. Trámite enviado a exámenes.");

                   dispose(); //Cerrar

                }catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

    }
}
