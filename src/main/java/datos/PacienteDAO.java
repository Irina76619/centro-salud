package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelo.Paciente;
import utilitarios.*;

/** Acceso a la tabla paciente (CRUD con JDBC). */
public class PacienteDAO {

    /** Convierte una fila de la base en un objeto Paciente. */
    private Paciente mapear(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getInt("id"));
        p.setDni(rs.getString("dni"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setEdad(rs.getInt("edad"));
        p.setTelefono(rs.getString("telefono"));
        return p;
    }

    /** Registra un paciente. Devuelve false si el DNI ya existe. */
    public boolean insertar(Paciente p) {
        String sql = "insert into paciente(dni, nombre, apellidos, edad, telefono) values(?,?,?,?,?)";
        try (
                Connection cn = Conexion.conecta(); PreparedStatement st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, p.getDni());
            st.setString(2, p.getNombre());
            st.setString(3, p.getApellidos());
            st.setInt(4, p.getEdad());
            st.setString(5, p.getTelefono());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                }
            }
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Ese DNI ya esta registrado");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** SOBRECARGA DE MÉTODOS: busca por DNI. */
    public Paciente buscar(String dni) {
        String sql = "select * from paciente where dni = ?";
        try (
                Connection cn = Conexion.conecta(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, dni);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** SOBRECARGA DE MÉTODOS: busca por nombre y apellidos. */
    public Paciente buscar(String nombre, String apellidos) {
        String sql = "select * from paciente where nombre = ? and apellidos = ?";
        try (
                Connection cn = Conexion.conecta(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, nombre);
            st.setString(2, apellidos);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** Modifica los datos de un paciente. */
    public boolean actualizar(Paciente p) {
        String sql = "update paciente set dni = ?, "
                + "nombre = ?, "
                + "apellidos = ?, "
                + "edad = ?, "
                + "telefono = ? where id = ?";
        try (
                Connection cn = Conexion.conecta(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, p.getDni());
            st.setString(2, p.getNombre());
            st.setString(3, p.getApellidos());
            st.setInt(4, p.getEdad());
            st.setString(5, p.getTelefono());
            st.setInt(6, p.getId());
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            System.out.println("Ya existe otro paciente con ese DNI");
            ex.printStackTrace();
        }
        return false;
    }

    /** Elimina un paciente por su DNI. */
    public boolean eliminar(String dni) {
        String sql = "delete from paciente where dni = ?";
        try (Connection cn = Conexion.conecta(); PreparedStatement st = cn.prepareStatement(sql)) {
            st.setString(1, dni);
            return st.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /** Devuelve todos los pacientes. */
    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "select * from paciente order by id";
        try (Connection cn = Conexion.conecta(); PreparedStatement st = cn.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
