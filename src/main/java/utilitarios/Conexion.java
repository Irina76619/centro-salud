package utilitarios;

import java.sql.*;

/**
 * Conexión a la base de datos H2 (embebida). Los datos se guardan en el archivo
 * data/centro_salud, dentro de la carpeta del proyecto.
 */
public class Conexion {

    private static final String URL
            = "jdbc:h2:./data/centro_salud;MODE=MySQL;DATABASE_TO_LOWER=TRUE;CASE_INSENSITIVE_IDENTIFIERS=TRUE";
    private static final String USUARIO = "sa";
    private static final String CLAVE = "";

    //    private static final String URL     = "jdbc:mysql://localhost:3306/centro_salud";
    //    private static final String USUARIO = "root";
    //    private static final String CLAVE   = "alumno";
    /**
     * Abre una conexión nueva. Devuelve null si no se pudo abrir.
     */
    public static Connection conecta() {
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (Exception ex) {
            System.out.println("error conexion " + ex.getMessage());
        }
        return cn;
    }

    /**
     * Indica si se puede conectar a la base.
     */
    public static boolean hayConexion() {
        try (Connection cn = conecta()) {
            return cn != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
