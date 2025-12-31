package ui;

import dao.UsuarioDao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import model.Usuario;


public class GestionUsuario extends JFrame {
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtUsername;
    private JButton btnRegresar;
    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JTable table1;
    private JCheckBox ADMINCheckBox;
    private JCheckBox ACTIVOCheckBox;
    private JCheckBox DESACTIVADOCheckBox;
    private JCheckBox ANALISTACheckBox;
    private JTextField txtCedulaFilltro;
    private JPasswordField jpPassword;
    private JPanel JPgestion;
    private JButton btnRefrescar;
    private Usuario usuario;

    public GestionUsuario(Usuario usuario){
        this.usuario = usuario;

        setTitle("Gestión de Usuarios");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setContentPane(JPgestion);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configurarTabla();
        configurarGrupos();

        btnCrear.addActionListener(e -> crearUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegresar.addActionListener(e -> regresarMenuAdmin());

        // FILTROS
        txtCedulaFilltro.addActionListener(e -> filtrarPorCedula());
        //REFRESCAR
        btnRefrescar.addActionListener(e -> {
            cargarTabla();
            JOptionPane.showMessageDialog(this,
                    "Tabla actualizada correctamente",
                    "Refrescar",
                    JOptionPane.INFORMATION_MESSAGE);
        });

    }

    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Cedula", "UsserName", "rol", "Estado"}, 0
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
                manejarSeleccionUsuario();
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {

        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.setRowCount(0);

        UsuarioDao dao = new UsuarioDao();
        List<Object[]> lista = dao.listarTodosUsuarios();

        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }
    }



    private void manejarSeleccionUsuario() {
        int fila = table1.getSelectedRow();
        if (fila == -1) return;

        // la tabla tiene: ID, Nombre, Cédula, Username, Rol, Estado
        txtNombre.setText(table1.getValueAt(fila, 1).toString());
        txtCedula.setText(table1.getValueAt(fila, 2).toString());
        txtUsername.setText(table1.getValueAt(fila, 3).toString());

        // Para los CheckBox de Rol
        String rol = table1.getValueAt(fila, 4).toString();
        ADMINCheckBox.setSelected(rol.equals("admin"));
        ANALISTACheckBox.setSelected(rol.equals("analista"));

        // Para los CheckBox de Estado
        boolean activo = (boolean) table1.getValueAt(fila, 5);
        ACTIVOCheckBox.setSelected(activo);
        DESACTIVADOCheckBox.setSelected(!activo);
    }

    private void configurarGrupos() {
        ButtonGroup grupoRol = new ButtonGroup();
        grupoRol.add(ADMINCheckBox);
        grupoRol.add(ANALISTACheckBox);

        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(ACTIVOCheckBox);
        grupoEstado.add(DESACTIVADOCheckBox);
    }

    private void filtrarPorCedula() {

        String cedula = txtCedulaFilltro.getText().trim();

        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        modelo.setRowCount(0); // limpiar tabla

        if (cedula.isEmpty()) {
            UsuarioDao dao = new UsuarioDao();
            List<Object[]> lista = dao.listarTodosUsuarios();
            return;
        }

        UsuarioDao dao = new UsuarioDao();
        Usuario u = dao.buscarPorCedula(cedula);

        if (u != null) {
            Object[] fila = {
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getCedula(),
                    u.getUsuario(),
                    u.getRol(),
                    u.isActivo()
            };
            modelo.addRow(fila);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró usuario con esa cédula",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void crearUsuario() {

        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(jpPassword.getPassword());

        if (nombre.isEmpty() || cedula.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setCedula(cedula);
        user.setUsuario(username);
        user.setRol(ADMINCheckBox.isSelected() ? "admin" : "analista");
        user.setActivo(ACTIVOCheckBox.isSelected());

        UsuarioDao dao = new UsuarioDao();

        if (dao.insertar(user, password)) {
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
            cargarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario");
        }
    }

    private void actualizarUsuario() {

        int fila = table1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }

        int id = (int) table1.getValueAt(fila, 0);

        Usuario user = new Usuario();
        user.setIdUsuario(id);
        user.setNombre(txtNombre.getText().trim());
        user.setCedula(txtCedula.getText().trim());
        user.setUsuario(txtUsername.getText().trim());
        user.setRol(ADMINCheckBox.isSelected() ? "admin" : "analista");
        user.setActivo(ACTIVOCheckBox.isSelected());

        UsuarioDao dao = new UsuarioDao();

        if (dao.actualizarUsuario(user)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado");
            cargarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar");
        }
    }

    private void limpiarFormulario() {

        txtNombre.setText("");
        txtCedula.setText("");
        txtUsername.setText("");
        jpPassword.setText("");

        ADMINCheckBox.setSelected(false);
        ANALISTACheckBox.setSelected(false);
        ACTIVOCheckBox.setSelected(true);
        DESACTIVADOCheckBox.setSelected(false);

        table1.clearSelection();
    }

    private void regresarMenuAdmin() {
        this.dispose();
        new MenuAdmin(usuario).setVisible(true);
    }

}
