package dao;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setActivo(rs.getBoolean("activo"));
                }
            }

        } catch (Exception e) {
            System.out.printf("Error en "+e.getMessage());;
        }

        return usuario;
    }
}
