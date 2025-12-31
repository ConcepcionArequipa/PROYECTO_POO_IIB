package dao;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDao {

    public Usuario login(String username, String password) {

        Usuario usuario = null;

        String sql = "select * from usuario where username = ? and activo = true";

        try {
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String hashBD = rs.getString("password_hash");

                // validacion real de contraseña
                if (BCrypt.checkpw(password, hashBD)) {

                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCedula(rs.getString("cedula"));
                    usuario.setUsuario(rs.getString("username"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setActivo(rs.getBoolean("activo"));
                }
            }

        } catch (Exception e) {
            System.out.printf("Error en "+e.getMessage());;
        }

        return usuario;
    }
    public boolean actualizarUsuario(Usuario user) {

        String sql = """
        UPDATE usuario
        SET nombre = ?, cedula = ?, username = ?, rol = ?, activo = ?
        WHERE id = ?
    """;

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCedula());
            ps.setString(3, user.getUsuario());
            ps.setString(4, user.getRol());
            ps.setBoolean(5, user.isActivo());
            ps.setInt(6, user.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizar usuario: " + e.getMessage());
            return false;
        }
    }


    public Usuario buscarPorCedula(String cedula) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE cedula = ?";
        try {
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setUsuario(rs.getString("username"));
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return usuario;
    }

    public boolean insertar(Usuario user, String passwordPlana) {
        String sql = "INSERT INTO usuario (nombre, cedula, username, password_hash, rol, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection con = new Conexion().getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getNombre());
            ps.setString(2, user.getCedula());
            ps.setString(3, user.getUsuario());
            // Hasheamos la clave antes de guardar
            ps.setString(4, BCrypt.hashpw(passwordPlana, BCrypt.gensalt()));
            ps.setString(5, user.getRol());
            ps.setBoolean(6, user.isActivo());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }


    public List<Object[]> listarTodosUsuarios() {
        List<Object[]> lista = new ArrayList<>();

        String sql = "SELECT id, nombre, cedula, username, rol, activo FROM usuario";

        try (Connection con = new Conexion().getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("username"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                };
                lista.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error listar usuarios: " + e.getMessage());
        }

        return lista;
    }



}


