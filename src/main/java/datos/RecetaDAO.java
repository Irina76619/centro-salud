package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Cita;
import modelo.Receta;
import utilitarios.Conexion;

/** Acceso a la tabla receta (CRUD con JDBC). Cada receta pertenece a una cita. */
public class RecetaDAO {

    private final CitaDAO citaDAO = new CitaDAO();

    private static final String SELECT_BASE =
        "select id, medicamento, indicaciones, fecha, id_cita from receta ";

    /** Convierte una fila en una Receta ligada a su cita. */
    private Receta mapear(ResultSet rs, Cita cita) throws SQLException {
        Receta r = new Receta();
        r.setId(rs.getInt("id"));
        r.setMedicamento(rs.getString("medicamento"));
        r.setIndicaciones(rs.getString("indicaciones"));
        // DATE de la base -> LocalDate de Java
        r.setFecha(rs.getDate("fecha").toLocalDate());
        r.setCita(cita != null ? cita : citaDAO.buscar(rs.getInt("id_cita")));
        return r;
    }

    /** Ejecuta la consulta base más un filtro y devuelve las recetas. */
    private List<Receta> consultar(Cita cita, String complemento, Object... params) {
        List<Receta> lista = new ArrayList<>();
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(SELECT_BASE + complemento)) {
            for (int i = 0; i < params.length; i++) {
                st.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs, cita));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /** Registra una receta. */
    public boolean insertar(Receta r) {
        String sql = "insert into receta (medicamento, indicaciones, fecha, id_cita) values (?,?,?,?)";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, r.getMedicamento());
            st.setString(2, r.getIndicaciones());
            st.setDate(3, Date.valueOf(r.getFecha()));
            st.setInt(4, r.getCita().getId());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getInt(1));
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Busca una receta por su id. */
    public Receta buscar(int id) {
        List<Receta> r = consultar(null, "where id = ?", id);
        return r.isEmpty() ? null : r.get(0);
    }

    /** Modifica una receta. */
    public boolean actualizar(Receta r) {
        String sql = "update receta set medicamento=?, indicaciones=?, fecha=?, id_cita=? where id=?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, r.getMedicamento());
            st.setString(2, r.getIndicaciones());
            st.setDate(3, Date.valueOf(r.getFecha()));
            st.setInt(4, r.getCita().getId());
            st.setInt(5, r.getId());
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Elimina una receta. */
    public boolean eliminar(int id) {
        String sql = "delete from receta where id = ?";
        try (Connection cn = Conexion.conecta();
             PreparedStatement st = cn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Devuelve las recetas de una cita. */
    public List<Receta> listarPorCita(Cita c) {
        return consultar(c, "where id_cita = ? order by id", c.getId());
    }

    /** Devuelve todas las recetas. */
    public List<Receta> listar() {
        return consultar(null, "order by fecha, id");
    }
}