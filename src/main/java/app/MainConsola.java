package app;

import datos.*;
import servicio.ServicioLogin;
import servicio.ServicioReportes;
import utilitarios.InicializadorBD;
import vista.MenuConsola;

/** Versión de respaldo: ejecuta el sistema por consola (sin ventanas). */
public class MainConsola {
    public static void main(String[] args) {
        // Crea la base y los datos de prueba si es la primera vez.
        InicializadorBD.iniciar();
        AdministrativoDAO adminDAO = new AdministrativoDAO();
        EspecialidadDAO especialidadDAO = new EspecialidadDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        PacienteDAO pacienteDAO = new PacienteDAO();
        HorarioDAO horarioDAO = new HorarioDAO();
        CitaDAO citaDAO = new CitaDAO();
        RecetaDAO recetaDAO = new RecetaDAO();
        HistorialClinicoDAO historialDAO = new HistorialClinicoDAO();

        ServicioLogin login = new ServicioLogin(adminDAO, medicoDAO);
        ServicioReportes reportes = new ServicioReportes(citaDAO, medicoDAO, especialidadDAO,
                recetaDAO, historialDAO);

        new MenuConsola(login, reportes, pacienteDAO, medicoDAO, especialidadDAO,
                horarioDAO, citaDAO, recetaDAO, historialDAO).login();
    }
}
