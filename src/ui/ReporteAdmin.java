package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import dao.ReporteDao;
import model.Usuario;

public class ReporteAdmin extends JFrame {
    private JButton regresarButton;
    private JComboBox Estado;
    private JTextField textField1;
    private JComboBox TIPO;
    private JButton EXPORTARButton;
    private JButton FILTRARButton;
    private JTable table1;
    private JFormattedTextField desdeFecha;
    private JFormattedTextField hastaFecha;
    private JPanel ReportePanel;

    private Usuario usuario;
    private DefaultTableModel modelo; // Variable de clase para que todos los métodos la vean
    private ReporteDao reporteDao = new ReporteDao(); // Instancia del DAO

    public ReporteAdmin(Usuario usuario) {
        this.usuario = usuario;
        setTitle("REPORTE - ADMIN");
        setSize(900, 600); // Aumenté el tamaño para que la tabla se vea bien
        setLocationRelativeTo(null);
        setContentPane(ReportePanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configurarTabla();

        FILTRARButton.addActionListener(e -> EnviarReporte());
        regresarButton.addActionListener(e -> regresarMenuAdmin());
    }

    private void configurarTabla() {

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Cédula", "Nombre", "Tipo Licencia", "Fecha Creación", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1.setModel(modelo);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void EnviarReporte() {
        try {
            // Validación: Si el campo tiene el texto por defecto, no procesar
            if (desdeFecha.getText().equals("DD/MM/AAAA") || hastaFecha.getText().equals("DD/MM/AAAA")) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese fechas válidas.");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1 = sdf.parse(desdeFecha.getText());
            java.util.Date d2 = sdf.parse(hastaFecha.getText());

            java.sql.Date sqlInicio = new java.sql.Date(d1.getTime());
            java.sql.Date sqlFin = new java.sql.Date(d2.getTime());

            // Limpiar tabla antes de cargar nuevos datos
            modelo.setRowCount(0);

            // Obtener datos del DAO
            List<Object[]> datos = reporteDao.filtrarReporteCompleto(
                    Estado.getSelectedItem().toString(),
                    TIPO.getSelectedItem().toString(),
                    textField1.getText(),
                    sqlInicio,
                    sqlFin
            );

            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }

            if (datos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron registros.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en formato de fecha o conexión: " + e.getMessage());
        }
    }

    private void regresarMenuAdmin() {
        this.dispose();
        // Pasamos el objeto usuario de vuelta para mantener la sesión
        new MenuAdmin(usuario).setVisible(true);
    }
}