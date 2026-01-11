package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
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

    // Labels del diseño
    private JLabel lblPendiente;
    private JLabel lblExamen;
    private JLabel lblEmitidas;
    private JLabel lblAprobado;
    private JLabel lblReprobado;

    private Usuario usuario;
    private DefaultTableModel modelo;
    private ReporteDao reporteDao = new ReporteDao();

    public ReporteAdmin(Usuario usuario) {
        this.usuario = usuario;
        setTitle("REPORTE - ADMIN");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setContentPane(ReportePanel);
        ReportePanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 1. Configurar la estructura de la tabla
        configurarTabla();

        // 2. Configurar máscaras de fecha
        configurarMascarasFecha();

        // 3. Cargar los datos en la tabla al iniciar
        cargarTablaInicial();

        // 4. CARGA AUTOMÁTICA DE CONTEOS
        // Es importante llamar a este metodo DESPUES de que los labels hayan sido inicializados por el Form
        actualizarEstadisticas();

        // 5. Asignar funciones a los botones
        FILTRARButton.addActionListener(e -> EnviarReporte());
        regresarButton.addActionListener(e -> regresarMenuAdmin());
        EXPORTARButton.addActionListener(e -> generarReporteCSV());

    }

    private void configurarMascarasFecha(){
        try {
            MaskFormatter mask = new MaskFormatter("####-##-##");
            mask.setPlaceholderCharacter('_');
            DefaultFormatterFactory factory = new DefaultFormatterFactory(mask);
            desdeFecha.setFormatterFactory(factory);
            hastaFecha.setFormatterFactory(factory);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private void cargarTablaInicial() {
        modelo.setRowCount(0);
        List<Object[]> datos = reporteDao.llenarTabla();
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
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
            // Si no se ingresan fechas, podrías optar por cargar todo o validar
            if (desdeFecha.getText().contains("_") || hastaFecha.getText().contains("_")) {
                JOptionPane.showMessageDialog(this, "Complete correctamente las fechas (AAAA-MM-DD).");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d1 = sdf.parse(desdeFecha.getText());
            java.util.Date d2 = sdf.parse(hastaFecha.getText());

            java.sql.Date sqlInicio = new java.sql.Date(d1.getTime());
            java.sql.Date sqlFin = new java.sql.Date(d2.getTime());

            modelo.setRowCount(0);

            List<Object[]> datos = reporteDao.filtrarFlexible(
                    Estado.getSelectedItem().toString(),
                    TIPO.getSelectedItem().toString(),
                    textField1.getText(),
                    sqlInicio,
                    sqlFin
            );

            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }

            // Opcional: Actualizar estadísticas también al filtrar
            actualizarEstadisticas();

            if (datos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron registros.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void regresarMenuAdmin() {
        this.dispose();
        new MenuAdmin(usuario).setVisible(true);
    }

    // Metodo para refrescar los Labels usando el DAO
    private void actualizarEstadisticas() {
        reporteDao.cargarConteosAutomaticos(
                lblPendiente,
                lblExamen,
                lblAprobado,
                lblReprobado,
                lblEmitidas
        );
    }


    private void generarReporteCSV() {

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para exportar.");
            return;
        }

        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("reporte_filtrado.csv"));

            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

            java.io.FileWriter writer = new java.io.FileWriter(chooser.getSelectedFile());

            // =========================
            // 1. RESUMEN DE ESTADOS
            // =========================
            writer.append("RESUMEN DE ESTADOS\n");
            writer.append("Estado,Cantidad\n");

            writer.append("Pendiente,").append(lblPendiente.getText()).append("\n");
            writer.append("Examen,").append(lblExamen.getText()).append("\n");
            writer.append("Aprobado,").append(lblAprobado.getText()).append("\n");
            writer.append("Reprobado,").append(lblReprobado.getText()).append("\n");
            writer.append("Emitido,").append(lblEmitidas.getText()).append("\n");

            writer.append("\n");

            // =========================
            // 2. DETALLE DE LA TABLA
            // =========================
            writer.append("DETALLE DEL REPORTE\n");

            // Encabezados
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                writer.append(modelo.getColumnName(i)).append(",");
            }
            writer.append("\n");

            // Filas
            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    writer.append(modelo.getValueAt(i, j).toString()).append(",");
                }
                writer.append("\n");
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "Reporte CSV generado correctamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar CSV: " + e.getMessage());
        }
    }

}