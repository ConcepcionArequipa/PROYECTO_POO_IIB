package ui;

import javax.swing.*;
import java.awt.*;

public class MenuAnalista extends BaseFrame {

    // Cambiados a protected para que MenuAdmin pueda heredarlos
    protected JButton btnRegistrar;
    protected JButton btnGenerar;
    protected JButton btnVerificar;
    protected JButton btnExamenes;
    protected JTable table1;
    protected JButton btnLogin;
    protected JCheckBox EXAMENESCheckBox;
    protected JCheckBox APROBADOCheckBox;
    protected JCheckBox TODOSCheckBox;
    protected JCheckBox PENDIENTECheckBox;
    protected JTextField txtCedula;
    protected JPanel jpFiltros;
    protected JPanel mainPanel;

    public MenuAnalista() {
        // Llama al constructor de BaseFrame para configurar título, tamaño y centrado
        super("Panel de Gestión - Analista");
        setContentPane(mainPanel);

        // Inicializar lógica de la tabla (Cargar datos de la BD)
        configurarTabla();

        // Configurar el ButtonGroup para los CheckBoxes
        configurarFiltros();
    }

    private void configurarTabla() {

    }

    private void configurarFiltros() {
        // Agrupar los checkboxes para que solo uno esté activo a la vez
        ButtonGroup grupoEstados = new ButtonGroup();
        grupoEstados.add(TODOSCheckBox);
        grupoEstados.add(PENDIENTECheckBox);
        grupoEstados.add(EXAMENESCheckBox);
        grupoEstados.add(APROBADOCheckBox);

        // Seleccionar "TODOS" por defecto
        TODOSCheckBox.setSelected(true);
    }
}