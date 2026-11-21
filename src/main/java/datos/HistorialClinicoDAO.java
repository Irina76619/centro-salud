package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.HistorialClinico;
import modelo.Paciente;
import utilitarios.Conexion;

/** Acceso a la tabla historial_clinico (diagnóstico y tratamiento por paciente). */
public class HistorialClinicoDAO {

    private final CitaDAO citaDAO = new CitaDAO();

    private static final String SELECT_BASE =
        "select h.id as h_id, h.diagnostico, h.tratamiento, h.fecha, h.id_cita, "
      + "       p.id as p_id, p.dni, p.nombre, p.apellidos, p.edad, p.telefono "
      + "from historial_clinico h join paciente p on h.id_paciente = p.id ";

    /** Convierte una fila en un HistorialClinico. */
    private HistorialClinico mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getInt("p_id"));
        p.setDni(rs.getString("dni"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setEdad(rs.getInt("edad"));
        p.setTelefono(rs.getString("telefono"));

        HistorialClinico h = new HistorialClinico();
        h.setId(rs.getInt("h_id"));
        h.setDiagnostico(rs.getString("diagnostico"));
        h.setTratamiento(rs.getString("tratamiento"));
        h.setFecha(rs.getDate("fecha").toLocalDate());
        h.setPaciente(p);

        int idCita = rs.getInt("id_cita");
        if (!rs.wasNull()) {
            h.setCita(citaDAO.buscar(idCita));
        }
        return h;
    }

    /** Ejecuta la consulta base más un filtro y devuelve los registros. */
    private List<HistorialClinico> consultar(String complemento, Object... params) {
        List<HistorialClinico> lista = new ArrayList<>();
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

    /** Registra un diagnóstico y tratamiento. */
    public boolean insertar(HistorialClinico h) {
        String sql = "insert into historial_clinico (diagnostico, tratamiento, fecha, id_paciente, id_cita) "
                   + "values (?,?,?,?,?)";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, h.getDiagnostico());
            st.setString(2, h.getTratamiento());
            st.setDate(3, Date.valueOf(h.getFecha()));
            st.setInt(4, h.getPaciente().getId());
            if (h.getCita() != null) st.setInt(5, h.getCita().getId());
            else st.setNull(5, Types.INTEGER);
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

    /** Busca un registro por su id. */
    public HistorialClinico buscar(int id) {
        List<HistorialClinico> r = consultar("where h.id = ?", id);
        return r.isEmpty() ? null : r.get(0);
    }

    /** Modifica un registro del historial. */
    public boolean actualizar(HistorialClinico h) {
        String sql = "update historial_clinico set diagnostico=?, tratamiento=?, fecha=?, "
                   + "id_paciente=?, id_cita=? where id=?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, h.getDiagnostico());
            st.setString(2, h.getTratamiento());
            st.setDate(3, Date.valueOf(h.getFecha()));
            st.setInt(4, h.getPaciente().getId());
            if (h.getCita() != null) st.setInt(5, h.getCita().getId());
            else st.setNull(5, Types.INTEGER);
            st.setInt(6, h.getId());
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Devuelve el historial completo de un paciente. */
    public List<HistorialClinico> listarPorPaciente(Paciente p) {
        return consultar("where h.id_paciente = ? order by h.fecha, h.id", p.getId());
    }

    /** Devuelve todo el historial. */
    public List<HistorialClinico> listar() {
        return consultar("order by h.fecha, h.id");
    }
}