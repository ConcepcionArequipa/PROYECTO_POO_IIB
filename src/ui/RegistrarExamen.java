package ui;

import model.Usuario;
import model.Examen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import service.ExamenService;

public class RegistrarExamen extends BaseDialogo{
    private JTextField txtNotaTeorica;
    private JTextField txtNotaPractica;
    private JButton btnGuardar;
    private JButton btnRegresar;
    private JPanel panelExamenes;
    private int tramiteId;


    public RegistrarExamen(Frame parent, Usuario usuario, int tramiteId) {

        super(parent,"Registro de examenes",usuario);
        this.tramiteId=tramiteId;
        initUI();
        setVisible(true);

    }

    @Override
    public void initUI() {
        setContentPane(panelExamenes);
        pack();
        setLocationRelativeTo(getParent());
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // Validaciones básicas
                    if (txtNotaTeorica.getText().isEmpty() || txtNotaPractica.getText().isEmpty()) {
                        mostrarError("Debe completar todos los campos");
                        return;
                    }

                    double notaTeorica = Double.parseDouble(txtNotaTeorica.getText());
                    double notaPractica = Double.parseDouble(txtNotaPractica.getText());

                    Examen examen = new Examen();
                    examen.setTramiteId(tramiteId);
                    examen.setNotaTeorica(notaTeorica);
                    examen.setNotaPractica(notaPractica);

                    ExamenService service = new ExamenService();

                    service.registrarExamen(examen);

                    mostrarMensaje("Notas del examen registradas exitosamente");

                    //Limpiar campos

                    txtNotaTeorica.setText("");
                    txtNotaPractica.setText("");
                    dispose();
                } catch (NumberFormatException ex) {
                    mostrarError("Las notas deben de ser numeros validos");
                }
                catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}

