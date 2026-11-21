package app;

import gui.LoginFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import utilitarios.Conexion;
import utilitarios.InicializadorBD;

/**
 * Punto de entrada de la aplicación gráfica (Swing).
 */
public class Main {

    public static void main(String[] args) {
        // Crea la base y los datos de prueba si es la primera vez.
        InicializadorBD.iniciar();
        if (!Conexion.hayConexion()) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo abrir la base de datos H2.\n"
                    + "Verifique que la carpeta del proyecto tenga permisos de escritura\n"
                    + "y que no haya otra copia del programa abierta.",
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        // Un solo Sistema: todas las ventanas comparten los mismos DAO y datos.
        Sistema sistema = new Sistema();
        SwingUtilities.invokeLater(() -> new LoginFrame(sistema).setVisible(true));
    }
}
