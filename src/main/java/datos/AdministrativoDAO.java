package datos;

import modelo.Administrativo;
import java.sql.*;
import java.io.*;
import utilitarios.Conexion;

/** Acceso a la tabla administrativo (solo se usa para el login). */
public class AdministrativoDAO {
    /** Busca un administrativo por su nombre de usuario. */
    public Administrativo buscarPorUsuario(String usuario){
        String sql = "select id, dni, nombre, apellidos, edad, usuario, clave "
                + "from administrativo where usuario = ?";
        try(Connection cn = Conexion.conecta();
                PreparedStatement st = cn.prepareStatement(sql)){
            st.setString(1, usuario);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    Administrativo a = new Administrativo();
                    a.setId(rs.getInt("id"));
                    a.setDni(rs.getString("dni"));
                    a.setNombre(rs.getString("nombre"));
                    a.setApellidos(rs.getString("apellidos"));
                    a.setEdad(rs.getInt("edad"));
                    a.setUsuario(rs.getString("usuario"));
                    a.setClave(rs.getString("clave"));
                    return a;
                }
            }
        }catch(Exception ex){ex.printStackTrace();}
            return null;
    }
}
