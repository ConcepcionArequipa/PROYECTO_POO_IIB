package ui;

import dao.TramiteDao;
import model.Usuario;
import ui.LoginFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuAnalista extends BaseFrame {

    // Cambiados a protected para que MenuAdmin pueda heredarlos
    protected JButton btnRegistrar;
    protected JButton btnGenerar;
    protected JButton btnVerificar;
    protected JButton btnExamenes;
    protected JTable table1;
    protected JButton btnLogin;
    // ESTADOS
    protected JCheckBox EXAMENESCheckBox;
    protected JCheckBox APROBADOCheckBox;
    protected JCheckBox TODOSCheckBox;
    protected JCheckBox PENDIENTECheckBox;
    private JCheckBox REPROBADOCheckBox;
    private JCheckBox EMITIDACheckBox;
    protected JTextField txtCedula;
    protected JPanel mainPanel;

    // Solo admin (se ocultan aquí)
    protected JButton btnGestionarUsuario;
    protected JButton btnReporteAdmin;


    public MenuAnalista(Usuario usuario) {

        // Llama al constructor de BaseFrame para configurar título, tamaño y centrado
        super("Panel de Gestión - Analista",usuario);
        initUI();

        // ACCIONES NO SE PUEDEN REALIZAR SI NO SE SELECCIONA EN LA TABLA
        btnVerificar.setEnabled(false);
        btnExamenes.setEnabled(false);
        btnGenerar.setEnabled(false);

        // Analista NO puede ver funciones de admin
        btnGestionarUsuario.setVisible(false);
        btnReporteAdmin.setVisible(false);

        // Inicializar lógica de la tabla (Cargar datos de la BD)
        configurarTabla();

        // Configurar el ButtonGroup para los CheckBoxes
        configurarFiltros();

        accionesBotones();

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    @Override
    public void initUI() {
        setContentPane(mainPanel);
    }

    // MANEJA SELECCIONES
    private void manejarSeleccionFila(){
        int fila = table1.getSelectedRow();

        if(fila == -1){
            deshabilitarAcciones();
            return;
        }
        String estado = table1.getValueAt(fila,5).toString();

        deshabilitarAcciones();

        switch (estado){
            case "PENDIENTE" -> btnVerificar.setEnabled(true);
            case "EXAMENES" -> btnExamenes.setEnabled(true);
            case "APROBADO" ->btnGenerar.setEnabled(true);
        }

    }

    private int obtenerIdTramiteSeleccionado(){
        int fila = table1.getSelectedRow();
        if (fila == -1) return -1;
        return (int) table1.getValueAt(fila, 0);
    }

    private void deshabilitarAcciones(){
        btnVerificar.setEnabled(false);
        btnExamenes.setEnabled(false);
        btnGenerar.setEnabled(false);
    }



    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Cédula", "Nombre", "Tipo", "Fecha", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1.setModel(modelo);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                manejarSeleccionFila();
            }
        });

        cargarTablaPorEstado("TODOS");
    }


    // ====== CARGA DE DATOS ======
    private void cargarTablaPorEstado(String estado) {

        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.setRowCount(0);

        TramiteDao dao = new TramiteDao();
        List<Object[]> lista;

        if (estado.equals("TODOS")) {
            lista = dao.listarTodos();
        } else {
            lista = dao.listarPorEstado(estado);
        }

        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }

        deshabilitarAcciones();
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

        TODOSCheckBox.addActionListener(e -> cargarTablaPorEstado("TODOS"));
        PENDIENTECheckBox.addActionListener(e -> cargarTablaPorEstado("PENDIENTE"));
        EXAMENESCheckBox.addActionListener(e -> cargarTablaPorEstado("EXAMENES"));
        APROBADOCheckBox.addActionListener(e -> cargarTablaPorEstado("APROBADO"));
    }

    // ====== EJEMPLO DE ACCIONES ======
    private void accionesBotones() {

        btnVerificar.addActionListener(e -> {
            int idTramite = obtenerIdTramiteSeleccionado();
            if (idTramite == -1) return;

            // new VentanaRequisitos(idTramite).setVisible(true);
            cargarTablaPorEstado("TODOS");
        });

        btnExamenes.addActionListener(e -> {
            int idTramite = obtenerIdTramiteSeleccionado();
            if (idTramite == -1) return;

            // new VentanaExamenes(idTramite).setVisible(true);
            cargarTablaPorEstado("TODOS");
        });

        btnGenerar.addActionListener(e -> {
            int idTramite = obtenerIdTramiteSeleccionado();
            if (idTramite == -1) return;

            // new VentanaLicencia(idTramite).setVisible(true);
            cargarTablaPorEstado("TODOS");
        });

        btnLogin.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }



}