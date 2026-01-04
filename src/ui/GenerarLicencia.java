package ui;

import javax.swing.*;
import dao.Conexion;
import dao.LicenciaDao;
import java.sql.Connection;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import java.io.File;

public class GenerarLicencia extends JFrame {
    private JButton GUARDARButton;
    private JButton GENERARButton;
    private JButton REGRESARButton;
    private JTextPane textPane1;
    private JPanel generarPanel;

    // Guardamos los datos de la fila como variable de instancia
    private Object[] datosFila;

    public GenerarLicencia(Object[] datosFila) {
        this.datosFila = datosFila; // Guardamos para usar en otros métodos
        int tramiteId = (int) datosFila[0]; // ID del trámite

        setContentPane(generarPanel);
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("GENERAR - LICENCIA");

        // Llenar el JTextPane con HTML usando los datos de la fila
        textPane1.setContentType("text/html");
        String contenido = "<html><body style='font-family: sans-serif; padding: 20px;'>" +
                "<h1 style='text-align: center; color: #2c3e50;'>REPÚBLICA DE ECUADOR</h1>" +
                "<h3 style='text-align: center;'>LICENCIA DE CONDUCIR</h3>" +
                "<hr>" +
                "<p><b>CÉDULA:</b> " + datosFila[1] + "</p>" +
                "<p><b>NOMBRE:</b> " + datosFila[2] + "</p>" +
                "<p><b>TIPO:</b> " + datosFila[3] + "</p>" +
                "<p><b>ESTADO:</b> <span style='color: green;'>" + datosFila[5] + "</span></p>" +
                "<br><p style='font-size: 8px;'>Documento preliminar para validación de datos.</p>" +
                "</body></html>";
        textPane1.setText(contenido);
        textPane1.setEditable(false);

        // Configurar botones
        configurarBotones();

        setVisible(true);
    }

    private void configurarBotones() {
        int tramiteId = (int) datosFila[0];

        // Botón GUARDAR -> cambia estado en base de datos
        GUARDARButton.addActionListener(e -> {
            try (Connection con = new Conexion().getConexion()) {
                LicenciaDao dao = new LicenciaDao();
                boolean ok = dao.cambiarEstado(tramiteId, con);

                if (ok) {
                    JOptionPane.showMessageDialog(this, "¡Licencia emitida con éxito!");
                    GUARDARButton.setEnabled(false);
                    GENERARButton.setEnabled(true); // Habilitar PDF
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el estado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Botón GENERAR -> exportar PDF
        GENERARButton.addActionListener(e -> exportarLicencia());

        // Botón REGRESAR -> cerrar ventana
        REGRESARButton.addActionListener(e -> this.dispose());
    }

    private void exportarLicencia() {
        if (datosFila == null || datosFila.length < 4) {
            JOptionPane.showMessageDialog(this, "No hay datos válidos para generar PDF.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar  -  Licencia");
        fileChooser.setSelectedFile(new File("Licencia_" + datosFila[1] + ".pdf"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = fileChooser.getSelectedFile();
                if (!archivo.getAbsolutePath().endsWith(".pdf")) {
                    archivo = new File(archivo.getAbsolutePath() + ".pdf");
                }

                PdfWriter writer = new PdfWriter(archivo.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document documento = new Document(pdf);

                documento.add(new Paragraph("REPÚBLICA DE ECUADOR").setBold().setFontSize(16));
                documento.add(new Paragraph("COMPROBANTE DE EMISIÓN DE LICENCIA").setFontSize(12));
                documento.add(new LineSeparator(new SolidLine()));

                documento.add(new Paragraph("CÉDULA: " + datosFila[1]));
                documento.add(new Paragraph("NOMBRE COMPLETO: " + datosFila[2]));
                documento.add(new Paragraph("TIPO DE LICENCIA: " + datosFila[3]));
                documento.add(new Paragraph("ESTADO: EMITIDA"));
                documento.add(new Paragraph("FECHA DE EMISIÓN: " + java.time.LocalDate.now()));

                documento.add(new LineSeparator(new SolidLine()));
                documento.add(new Paragraph("\nEste documento es un comprobante oficial del sistema."));

                documento.close();

                JOptionPane.showMessageDialog(this, "PDF creado exitosamente ");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
