package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Especialidad;
import modelo.Medico;
import utilitarios.Conexion;

/** Acceso a la tabla medico (CRUD con JDBC). Trae también su especialidad. */
public class MedicoDAO {

    /** Consulta base: médico unido a su especialidad. */
    private static final String SELECT_BASE =
        "select m.id, m.codigo_medico, m.clave, m.dni, m.nombre, m.apellidos, m.edad, "
      + "       e.id as esp_id, e.nombre as esp_nombre "
      + "from medico m join especialidad e on m.id_especialidad = e.id ";

    /** Convierte una fila en un Medico con su Especialidad. */
    private Medico mapear(ResultSet rs) throws SQLException {
        Especialidad e = new Especialidad();
        e.setId(rs.getInt("esp_id"));
        e.setNombre(rs.getString("esp_nombre"));

        Medico m = new Medico();
        m.setId(rs.getInt("id"));
        m.setCodigoMedico(rs.getString("codigo_medico"));
        m.setClave(rs.getString("clave"));
        m.setDni(rs.getString("dni"));
        m.setNombre(rs.getString("nombre"));
        m.setApellidos(rs.getString("apellidos"));
        m.setEdad(rs.getInt("edad"));
        m.setEspecialidad(e);
        return m;
    }

    /** Registra un médico. Falla si el código o el DNI ya existen. */
    public boolean insertar(Medico m) {
        String sql = "insert into medico (codigo_medico, clave, dni, nombre, apellidos, edad, id_especialidad) "
                   + "values (?,?,?,?,?,?,?)";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, m.getCodigoMedico());
            st.setString(2, m.getClave());
            st.setString(3, m.getDni());
            st.setString(4, m.getNombre());
            st.setString(5, m.getApellidos());
            st.setInt(6, m.getEdad());
            st.setInt(7, m.getEspecialidad().getId());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) m.setId(keys.getInt(1));
            }
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("No se pudo registrar: codigo o DNI repetido, o especialidad invalida.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Busca un médico por su código (se usa en el login). */
    public Medico buscar(String codigoMedico) {
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + "where m.codigo_medico = ?")) {
            st.setString(1, codigoMedico);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** Modifica los datos de un médico. */
    public boolean actualizar(Medico m) {
        String sql = "update medico set codigo_medico=?, clave=?, dni=?, nombre=?, apellidos=?, "
                   + "edad=?, id_especialidad=? where id=?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, m.getCodigoMedico());
            st.setString(2, m.getClave());
            st.setString(3, m.getDni());
            st.setString(4, m.getNombre());
            st.setString(5, m.getApellidos());
            st.setInt(6, m.getEdad());
            st.setInt(7, m.getEspecialidad().getId());
            st.setInt(8, m.getId());
            return st.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Codigo o DNI ya usado por otro medico.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Elimina un médico. Falla si tiene citas registradas. */
    public boolean eliminar(String codigoMedico) {
        String sql = "delete from medico where codigo_medico = ?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, codigoMedico);
            return st.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("No se puede eliminar: el medico tiene citas registradas.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Devuelve todos los médicos. */
    public List<Medico> listar() {
        List<Medico> lista = new ArrayList<>();
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + "order by m.id");
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}