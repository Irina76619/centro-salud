package utilitarios;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/** Crea las tablas y los datos de prueba la primera vez (si la base aún no existe). */
public class InicializadorBD {

    /** Si no existe la tabla especialidad, ejecuta el script con tablas y datos de prueba. */
    public static void iniciar() {
        try (Connection cn = Conexion.conecta()) {
            if (cn == null || existeTabla(cn)) return;
            ejecutarScript(cn, "/db/centro_salud_h2.sql");
            System.out.println("Base de datos creada con datos de prueba.");
        } catch (Exception e) {
            System.out.println("Error al iniciar la base: " + e.getMessage());
        }
    }

    private static boolean existeTabla(Connection cn) throws SQLException {
        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(
                 "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE LOWER(TABLE_NAME)='especialidad'")) {
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    /** Lee el script desde el classpath y ejecuta cada sentencia separada por ';'. */
    private static void ejecutarScript(Connection cn, String ruta) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = InicializadorBD.class.getResourceAsStream(ruta);
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) sb.append(linea).append('\n');
        }
        try (Statement st = cn.createStatement()) {
            for (String sentencia : sb.toString().split(";")) {
                if (!sentencia.trim().isEmpty()) st.execute(sentencia);
            }
        }
    }
}
