package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Especialidad;
import modelo.Horario;
import modelo.Medico;
import utilitarios.Conexion;

/** Acceso a la tabla horario (CRUD con JDBC). Trae también al médico. */
public class HorarioDAO {

    /** Consulta base: horario unido a su médico y su especialidad. */
    private static final String SELECT_BASE =
        "select h.id as h_id, h.dia_semana, h.hora_inicio, h.hora_fin, "
      + "       m.id as m_id, m.codigo_medico, m.clave, m.dni, m.nombre as m_nombre, "
      + "       m.apellidos, m.edad, "
      + "       e.id as e_id, e.nombre as e_nombre "
      + "from horario h "
      + "join medico m on h.id_medico = m.id "
      + "join especialidad e on m.id_especialidad = e.id ";

    /** Convierte una fila en un Horario con su Medico. */
    private Horario mapear(ResultSet rs) throws SQLException {
        Especialidad e = new Especialidad();
        e.setId(rs.getInt("e_id"));
        e.setNombre(rs.getString("e_nombre"));

        Medico m = new Medico();
        m.setId(rs.getInt("m_id"));
        m.setCodigoMedico(rs.getString("codigo_medico"));
        m.setClave(rs.getString("clave"));
        m.setDni(rs.getString("dni"));
        m.setNombre(rs.getString("m_nombre"));
        m.setApellidos(rs.getString("apellidos"));
        m.setEdad(rs.getInt("edad"));
        m.setEspecialidad(e);

        Horario h = new Horario();
        h.setId(rs.getInt("h_id"));
        h.setDiaSemana(rs.getString("dia_semana"));
        h.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
        h.setHoraFin(rs.getTime("hora_fin").toLocalTime());
        h.setMedico(m);
        return h;
    }

    /** Registra un horario de atención. */
    public boolean insertar(Horario h) {
        String sql = "insert into horario (dia_semana, hora_inicio, hora_fin, id_medico) values (?,?,?,?)";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, h.getDiaSemana());
            st.setTime(2, Time.valueOf(h.getHoraInicio()));
            st.setTime(3, Time.valueOf(h.getHoraFin()));
            st.setInt(4, h.getMedico().getId());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) h.setId(keys.getInt(1));
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Indica si el horario se cruza con otro del mismo médico en el mismo día.
     * idExcluir es el horario que se está editando (0 si es uno nuevo).
     */
    public boolean hayCruce(Medico m, String dia, java.time.LocalTime inicio,
                            java.time.LocalTime fin, int idExcluir) {
        for (Horario h : listarPorMedico(m)) {
            if (h.getId() != idExcluir && h.getDiaSemana().equals(dia)
                    && inicio.isBefore(h.getHoraFin()) && fin.isAfter(h.getHoraInicio())) {
                return true;
            }
        }
        return false;
    }

    /** Busca un horario por su id. */
    public Horario buscar(int id) {
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + "where h.id = ?")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** Modifica un horario. */
    public boolean actualizar(Horario h) {
        String sql = "update horario set dia_semana=?, hora_inicio=?, hora_fin=?, id_medico=? where id=?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, h.getDiaSemana());
            st.setTime(2, Time.valueOf(h.getHoraInicio()));
            st.setTime(3, Time.valueOf(h.getHoraFin()));
            st.setInt(4, h.getMedico().getId());
            st.setInt(5, h.getId());
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Elimina un horario. */
    public boolean eliminar(int id) {
        String sql = "delete from horario where id = ?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Devuelve todos los horarios. */
    public List<Horario> listar() {
        List<Horario> lista = new ArrayList<>();
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + "order by h.id");
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /** Devuelve los horarios de un médico. */
    public List<Horario> listarPorMedico(Medico m) {
        List<Horario> lista = new ArrayList<>();
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + "where h.id_medico = ? order by h.id")) {
            st.setInt(1, m.getId());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}