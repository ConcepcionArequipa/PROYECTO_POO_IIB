package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import model.Usuario;
import service.LicenciaService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// crear imagen
// crear pdf
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import model.Licencia;
import java.io.File;
import java.time.LocalDate;

public class GenerarLicencia extends JFrame {
    private JButton GUARDARButton;
    private JButton GENERARButton;
    private JButton REGRESARButton;
    private JTextPane textPane1;
    private JPanel generarPanel;
    private JButton AGREGARFOTOButton;
    private Usuario usuario;

    private File archivoImagenSeleccionada = null; // Variable para guardar la foto elegida

    // Guardamos los datos de la fila como variable de instancia
    private Object[] datosFila;

    public GenerarLicencia(Object[] datosFila, Usuario usuario) {
        this.usuario=usuario;
        this.datosFila = datosFila; // Guardamos para usar en otros métodos
        int tramiteId = (int) datosFila[0]; // ID del trámite

        setContentPane(generarPanel);
        generarPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );
        setSize(700, 600);
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
        GENERARButton.setEnabled(false);
        AGREGARFOTOButton.setEnabled(false);


        // Configurar botones
        configurarBotones();

        setVisible(true);

    }

    private void configurarBotones() {
        int tramiteId = (int) datosFila[0];

        // Botón GUARDAR -> cambia estado en base de datos
        GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Licencia licencia=new Licencia();
                    licencia.setTramiteId((int) datosFila[0]);
                    licencia.setNumeroLicencia("LIC-"+datosFila[1]);
                    licencia.setFechaVencimiento(java.time.LocalDate.now().plusYears(5));

                    new LicenciaService().emitirLicencia(licencia,usuario);

                    JOptionPane.showMessageDialog(null,"Licencia emitida correctamente");
                    GUARDARButton.setEnabled(false);
                    AGREGARFOTOButton.setEnabled(true);
                    GENERARButton.setEnabled(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
        });
        AGREGARFOTOButton.addActionListener(e -> seleccionarFoto());
        // Botón GENERAR -> exportar PDF
        GENERARButton.addActionListener(e -> exportarLicencia());

        // Botón REGRESAR -> cerrar ventana
        REGRESARButton.addActionListener(e -> this.dispose());
    }
    private void seleccionarFoto(){
        JFileChooser buscador = new JFileChooser();
        buscador.setDialogTitle("Seleccione la foto del conductor");

        // Filtro para que solo se vean imágenes
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png");
        buscador.setFileFilter(filtro);

        int resultado = buscador.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            archivoImagenSeleccionada = buscador.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Foto cargada con éxito.");
        }
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
                // --- LÓGICA DE LA IMAGEN ---
                if (archivoImagenSeleccionada != null) {
                    try {
                        com.itextpdf.io.image.ImageData data = com.itextpdf.io.image.ImageDataFactory.create(archivoImagenSeleccionada.getAbsolutePath());
                        com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(data);

                        img.scaleToFit(150f, 150f);
                        img.setFixedPosition(400f, 595f); // Asegúra que no se salgan de la página
                        documento.add(img);
                    } catch (Exception imgError) {
                        // Si la imagen falla, que al menos imprima el resto del PDF
                        documento.add(new Paragraph("[Error al cargar la foto seleccionada]"));
                        System.err.println("Error con la imagen: " + imgError.getMessage());
                    }
                }
                documento.add(new Paragraph("CÉDULA: " + datosFila[1]));
                documento.add(new Paragraph("NOMBRE COMPLETO: " + datosFila[2]));
                documento.add(new Paragraph("TIPO DE LICENCIA: " + datosFila[3]));
                documento.add(new Paragraph("ESTADO: EMITIDA"));
                documento.add(new Paragraph("FECHA DE EMISIÓN: " + LocalDate.now()));
                documento.add(new Paragraph("FECHA DE CADUCIDAD: " + LocalDate.now().plusYears(5)));

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
