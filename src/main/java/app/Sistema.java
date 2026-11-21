package app;

import datos.*;
import servicio.*;

/** Reúne los DAO y servicios en un solo objeto que comparten todas las ventanas. */
public class Sistema {
    public final AdministrativoDAO adminDAO = new AdministrativoDAO();
    public final EspecialidadDAO especialidadDAO = new EspecialidadDAO();
    public final MedicoDAO medicoDAO = new MedicoDAO();
    public final PacienteDAO pacienteDAO = new PacienteDAO();
    public final HorarioDAO horarioDAO = new HorarioDAO();
    public final CitaDAO citaDAO = new CitaDAO();
    public final RecetaDAO recetaDAO = new RecetaDAO();
    public final HistorialClinicoDAO historialDAO = new HistorialClinicoDAO();

    public final ServicioLogin login = new ServicioLogin(adminDAO, medicoDAO);
    public final ServicioReportes reportes =
        new ServicioReportes(citaDAO, medicoDAO, especialidadDAO, recetaDAO, historialDAO);

    public Sistema() { }
}