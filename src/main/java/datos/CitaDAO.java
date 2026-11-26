package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Cita;
import modelo.Especialidad;
import modelo.Medico;
import modelo.Paciente;
import utilitarios.Conexion;

/** Acceso a la tabla cita (CRUD con JDBC). Trae también paciente, médico y especialidad. */
public class CitaDAO {

    /** Consulta base: cita unida a paciente, médico y especialidad. */
    private static final String SELECT_BASE =
        "select c.id as c_id, c.fecha_hora, c.motivo, c.estado, "
      + "       p.id as p_id, p.dni as p_dni, p.nombre as p_nombre, "
      + "       p.apellidos as p_apellidos, p.edad as p_edad, p.telefono, "
      + "       m.id as m_id, m.codigo_medico, m.clave, m.dni as m_dni, "
      + "       m.nombre as m_nombre, m.apellidos as m_apellidos, m.edad as m_edad, "
      + "       e.id as e_id, e.nombre as e_nombre "
      + "from cita c "
      + "join paciente p on c.id_paciente = p.id "
      + "join medico m on c.id_medico = m.id "
      + "join especialidad e on m.id_especialidad = e.id ";

    /** Convierte una fila en una Cita completa. */
    private Cita mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getInt("p_id"));
        p.setDni(rs.getString("p_dni"));
        p.setNombre(rs.getString("p_nombre"));
        p.setApellidos(rs.getString("p_apellidos"));
        p.setEdad(rs.getInt("p_edad"));
        p.setTelefono(rs.getString("telefono"));

        Especialidad e = new Especialidad();
        e.setId(rs.getInt("e_id"));
        e.setNombre(rs.getString("e_nombre"));

        Medico m = new Medico();
        m.setId(rs.getInt("m_id"));
        m.setCodigoMedico(rs.getString("codigo_medico"));
        m.setClave(rs.getString("clave"));
        m.setDni(rs.getString("m_dni"));
        m.setNombre(rs.getString("m_nombre"));
        m.setApellidos(rs.getString("m_apellidos"));
        m.setEdad(rs.getInt("m_edad"));
        m.setEspecialidad(e);

        Cita c = new Cita();
        c.setId(rs.getInt("c_id"));
        c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        c.setMotivo(rs.getString("motivo"));
        c.setEstado(rs.getString("estado"));
        c.setPaciente(p);
        c.setMedico(m);
        return c;
    }

    /** Ejecuta la consulta base más un filtro (where/order by) y devuelve las citas. */
    private List<Cita> consultar(String complemento, Object... params) {
        List<Cita> lista = new ArrayList<>();
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + complemento)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    private boolean hayChoque(Cita c) {
        String sql = "select count(*) from cita "
                   + "where id_medico = ? and fecha_hora = ? and estado not in ('CANCELADA','NO_ASISTIO') and id <> ?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, c.getMedico().getId());
            st.setTimestamp(2, Timestamp.valueOf(c.getFechaHora()));
            st.setInt(3, c.getId());
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Registra una cita nueva. */
    public boolean insertar(Cita c) {
        if (hayChoque(c)) {
            System.out.println("El medico ya tiene una cita a esa fecha y hora.");
            return false;
        }
        String sql = "insert into cita (fecha_hora, motivo, estado, id_paciente, id_medico) values (?,?,?,?,?)";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setTimestamp(1, Timestamp.valueOf(c.getFechaHora()));
            st.setString(2, c.getMotivo());
            st.setString(3, c.getEstado());
            st.setInt(4, c.getPaciente().getId());
            st.setInt(5, c.getMedico().getId());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) c.setId(keys.getInt(1));
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Busca una cita por su id. */
    public Cita buscar(int id) {
        List<Cita> r = consultar("where c.id = ?", id);
        return r.isEmpty() ? null : r.get(0);
    }

    /** Modifica fecha, motivo, estado, paciente o médico de una cita. */
    public boolean actualizar(Cita c) {
        if (hayChoque(c)) {
            System.out.println("El medico ya tiene una cita a esa fecha y hora.");
            return false;
        }
        String sql = "update cita set fecha_hora=?, motivo=?, estado=?, id_paciente=?, id_medico=? where id=?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setTimestamp(1, Timestamp.valueOf(c.getFechaHora()));
            st.setString(2, c.getMotivo());
            st.setString(3, c.getEstado());
            st.setInt(4, c.getPaciente().getId());
            st.setInt(5, c.getMedico().getId());
            st.setInt(6, c.getId());
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Elimina una cita. */
    public boolean eliminar(int id) {
        String sql = "delete from cita where id = ?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Devuelve todas las citas ordenadas por fecha. */
    public List<Cita> listar() {
        return consultar("order by c.fecha_hora");
    }

    /** Devuelve las citas de un médico (lo usa el menú médico). */
    public List<Cita> listarPorMedico(Medico m) {
        return consultar("where c.id_medico = ? order by c.fecha_hora", m.getId());
    }

    /** Devuelve las citas de un paciente. */
    public List<Cita> listarPorPaciente(Paciente p) {
        return consultar("where c.id_paciente = ? order by c.fecha_hora", p.getId());
    }
}