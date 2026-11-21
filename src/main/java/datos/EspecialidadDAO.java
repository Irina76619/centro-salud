package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelo.Especialidad;
import utilitarios.Conexion;


/** Acceso a la tabla especialidad (CRUD con JDBC). */
public class EspecialidadDAO {

    /** Registra una especialidad nueva. */
    public boolean insertar(Especialidad e) {
        String sql = "insert into especialidad (nombre) values (?)";
        try(Connection cn = Conexion.conecta();
                PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ){
            st.setString(1, e.getNombre());
            st.executeUpdate();
            try(ResultSet keys = st.getGeneratedKeys()){
                if(keys.next()) e.setId(keys.getInt(1));
            }
            return true;
        }catch(SQLIntegrityConstraintViolationException ex){
            System.out.println("Esa especialidad ya existe");
        }catch(Exception ex){ex.printStackTrace();}
        return false;
    }

    /** Busca una especialidad por su id. */
    public Especialidad buscar(int id) {
        String sql = "select id, nombre from especialidad where id = ?";
        try(Connection cn = Conexion.conecta();
            PreparedStatement st = cn.prepareStatement(sql)){
            st.setInt(1,id);
            try(ResultSet rs = st.executeQuery()){
                if(rs.next()){
                    Especialidad e = new Especialidad();
                    e.setId(rs.getInt(1));
                    e.setNombre(rs.getString(2));
                    return e;
                }
            }
        }catch(Exception ex){ ex.printStackTrace();}
        return null;
    }

    /** Modifica el nombre de una especialidad. */
    public boolean actualizar(Especialidad e) {
        String sql = "update especialidad set nombre = ? where id = ?";
        try(Connection cn = Conexion.conecta();
            PreparedStatement st = cn.prepareStatement(sql)){
            st.setString(1, e.getNombre());
            st.setInt(2, e.getId());
            return st.executeUpdate() > 0;
        }catch(Exception ex){ ex.printStackTrace();}
        return false;
    }

    /** Elimina una especialidad. Falla si hay médicos que la usan. */
    public boolean eliminar(int id) {
        String sql = "delete from especialidad where id = ?";
        try(Connection cn = Conexion.conecta();
            PreparedStatement st = cn.prepareStatement(sql)){
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        }catch(SQLIntegrityConstraintViolationException ex){ 
            System.out.println("No se puede eliminar: hay medicos con esta especialidad");}
        catch(Exception ex){ ex.printStackTrace();}
        return false;
    }

    /** Devuelve todas las especialidades. */
    public List<Especialidad> listar() {
        List<Especialidad> lista = new ArrayList<>();
        String sql = "select id, nombre from especialidad order by id";
        try(Connection cn = Conexion.conecta();
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet rs = st.executeQuery()){
            while(rs.next()){
                Especialidad e = new Especialidad();
                e.setId(rs.getInt(1));
                e.setNombre(rs.getString(2));
                lista.add(e);
            }
        }catch(Exception ex){ ex.printStackTrace();}
        return lista;
    }
     
}
