package servicio;

import datos.CitaDAO;
import datos.EspecialidadDAO;
import datos.HistorialClinicoDAO;
import datos.MedicoDAO;
import datos.RecetaDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import modelo.Cita;
import modelo.Especialidad;
import modelo.HistorialClinico;
import modelo.Medico;
import modelo.Paciente;
import modelo.Receta;
import java.util.ArrayList;

/**
 * Genera los reportes del sistema (RN-13: solo lectura, no modifican datos).
 *
 * Cada reporte tiene dos versiones: - textoXxx(): DEVUELVE el reporte como
 * String. Lo usa la interfaz gráfica (JTextArea). - xxx(): lo IMPRIME en
 * consola. Lo usa MenuConsola (versión de respaldo). La versión de consola solo
 * llama a la de texto, así no se repite la lógica.
 */
public class ServicioReportes {

    private CitaDAO citaDAO;
    private MedicoDAO medicoDAO;
    private EspecialidadDAO especialidadDAO;
    private RecetaDAO recetaDAO;
    private HistorialClinicoDAO historialDAO;

    private static final DateTimeFormatter FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ServicioReportes(CitaDAO citaDAO, MedicoDAO medicoDAO, EspecialidadDAO especialidadDAO,
            RecetaDAO recetaDAO, HistorialClinicoDAO historialDAO) {
        this.citaDAO = citaDAO;
        this.medicoDAO = medicoDAO;
        this.especialidadDAO = especialidadDAO;
        this.recetaDAO = recetaDAO;
        this.historialDAO = historialDAO;
    }

    /**
     * Una línea de reporte para una cita; si incluirMedico es false se omite el
     * médico.
     */
    private String lineaCita(Cita c, boolean incluirMedico) {
        String linea = "  " + c.getFechaHora().format(FECHA_HORA) + " | " + c.getPaciente().getNombreCompleto();
        if (incluirMedico) {
            linea += " | Dr(a). " + c.getMedico().getNombreCompleto();
        }
        return linea + " | " + c.getMotivo() + " | " + c.getEstado() + "\n";
    }

    // ---------------------------------------------------------------- CITAS POR MÉDICO
    public String textoCitasPorMedico() {
        StringBuilder sb = new StringBuilder("=== CITAS POR MÉDICO ===\n");
        for (Medico m : medicoDAO.listar()) {
            int total = 0;
            sb.append("\n").append(m.getNombreCompleto()).append(" (").append(m.getCodigoMedico()).append(")\n");
            for (Cita c : citaDAO.listarPorMedico(m)) {
                sb.append(lineaCita(c, false));
                total++;
            }
            sb.append("  Total: ").append(total).append("\n");
        }
        return sb.toString();
    }

    public void citasPorMedico() {
        System.out.print("\n" + textoCitasPorMedico());
    }

    // ---------------------------------------------------------------- CITAS POR ESPECIALIDAD
    public String textoCitasPorEspecialidad() {
        StringBuilder sb = new StringBuilder("=== CITAS POR ESPECIALIDAD ===\n");
        for (Especialidad e : especialidadDAO.listar()) {
            int total = 0;
            sb.append("\n").append(e.getNombre()).append("\n");
            for (Cita c : citaDAO.listar()) {
                if (c.getMedico().getEspecialidad().getId() == e.getId()) {
                    sb.append(lineaCita(c, true));
                    total++;
                }
            }
            sb.append("  Total: ").append(total).append("\n");
        }
        return sb.toString();
    }

    public void citasPorEspecialidad() {
        System.out.print("\n" + textoCitasPorEspecialidad());
    }

    // ---------------------------------------------------------------- CITAS POR DÍA
    public String textoCitasPorDia(LocalDate fecha) {
        StringBuilder sb = new StringBuilder("=== CITAS DEL " + fecha.format(FECHA) + " ===\n\n");
        int total = 0;
        for (Cita c : citaDAO.listar()) {
            if (c.getFechaHora().toLocalDate().equals(fecha)) {
                sb.append(lineaCita(c, true));
                total++;
            }
        }
        sb.append("\nTotal: ").append(total).append("\n");
        return sb.toString();
    }

    public void citasPorDia(LocalDate fecha) {
        System.out.print("\n" + textoCitasPorDia(fecha));
    }

    // ---------------------------------------------------------------- CITAS POR MES
    public String textoCitasPorMes(int mes, int anio) {
        StringBuilder sb = new StringBuilder("=== CITAS DE " + mes + "/" + anio + " ===\n\n");
        int total = 0;
        for (Cita c : citaDAO.listar()) {
            if (c.getFechaHora().getMonthValue() == mes && c.getFechaHora().getYear() == anio) {
                sb.append(lineaCita(c, true));
                total++;
            }
        }
        sb.append("\nTotal: ").append(total).append("\n");
        return sb.toString();
    }

    public void citasPorMes(int mes, int anio) {
        System.out.print("\n" + textoCitasPorMes(mes, anio));
    }

    // ---------------------------------------------------------------- HISTORIAL POR PACIENTE
    public String textoHistorialPorPaciente(Paciente p) {
        StringBuilder sb = new StringBuilder("=== HISTORIAL CLÍNICO DE " + p.getNombreCompleto() + " ===\n\n");
        List<HistorialClinico> lista = historialDAO.listarPorPaciente(p);   // es una copia
        lista.sort(Comparator.comparing(HistorialClinico::getFecha));       // ordenado por fecha
        for (HistorialClinico h : lista) {
            sb.append("  ").append(h.getFecha().format(FECHA)).append(" | Diagnóstico: ")
                    .append(h.getDiagnostico()).append(" | Tratamiento: ").append(h.getTratamiento()).append("\n");
        }
        if (lista.isEmpty()) {
            sb.append("  (sin registros)\n");
        }
        return sb.toString();
    }

    public void historialPorPaciente(Paciente p) {
        System.out.print("\n" + textoHistorialPorPaciente(p));
    }

    // ---------------------------------------------------------------- RECETAS Y TRATAMIENTOS
    public String textoRecetasYTratamientos() {
        StringBuilder sb = new StringBuilder("=== RECETAS Y TRATAMIENTOS ===\n");
        for (HistorialClinico h : historialDAO.listar()) {
            sb.append("\n").append(h.getFecha().format(FECHA)).append(" | ")
                    .append(h.getPaciente().getNombreCompleto())
                    .append(" | Diagnóstico: ").append(h.getDiagnostico())
                    .append(" | Tratamiento: ").append(h.getTratamiento()).append("\n");
            if (h.getCita() != null) {
                List<Receta> recetas = recetaDAO.listarPorCita(h.getCita());
                if (recetas.isEmpty()) {
                    sb.append("    (sin recetas)\n");
                }
                for (Receta r : recetas) {
                    sb.append("    Receta: ").append(r.getMedicamento())
                            .append(" - ").append(r.getIndicaciones()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public void recetasYTratamientos() {
        System.out.print("\n" + textoRecetasYTratamientos());
    }

    // =====================================================================================
    // VERSIÓN PARA TABLAS (JTable): filasXxx() devuelve una lista de filas en lugar de texto.
    // No modifican datos (RN-13). Los encabezados los pone la pantalla (PanelReportes).
    // =====================================================================================
    /**
     * Columnas: Médico | Fecha y hora | Paciente | Motivo | Estado (con una
     * fila TOTAL por médico).
     */
    public List<Object[]> filasCitasPorMedico() {
        List<Object[]> filas = new ArrayList<>();
        for (Medico m : medicoDAO.listar()) {
            int total = 0;
            for (Cita c : citaDAO.listarPorMedico(m)) {
                filas.add(new Object[]{m.getNombreCompleto(), c.getFechaHora().format(FECHA_HORA),
                    c.getPaciente().getNombreCompleto(), c.getMotivo(), c.getEstado()});
                total++;
            }
            filas.add(new Object[]{m.getNombreCompleto(), "TOTAL: " + total, "", "", ""});
        }
        return filas;
    }

    /**
     * Columnas: Especialidad | Fecha y hora | Paciente | Médico | Motivo |
     * Estado (con TOTAL por especialidad).
     */
    public List<Object[]> filasCitasPorEspecialidad() {
        List<Object[]> filas = new ArrayList<>();
        for (Especialidad e : especialidadDAO.listar()) {
            int total = 0;
            for (Cita c : citaDAO.listar()) {
                if (c.getMedico().getEspecialidad().getId() == e.getId()) {
                    filas.add(new Object[]{e.getNombre(), c.getFechaHora().format(FECHA_HORA),
                        c.getPaciente().getNombreCompleto(), "Dr(a). " + c.getMedico().getNombreCompleto(),
                        c.getMotivo(), c.getEstado()});
                    total++;
                }
            }
            filas.add(new Object[]{e.getNombre(), "TOTAL: " + total, "", "", "", ""});
        }
        return filas;
    }

    /**
     * Columnas: Fecha y hora | Paciente | Médico | Motivo | Estado (con una
     * fila TOTAL al final).
     */
    public List<Object[]> filasCitasPorDia(LocalDate fecha) {
        List<Object[]> filas = new ArrayList<>();
        int total = 0;
        for (Cita c : citaDAO.listar()) {
            if (c.getFechaHora().toLocalDate().equals(fecha)) {
                filas.add(filaCitaConMedico(c));
                total++;
            }
        }
        filas.add(new Object[]{"TOTAL: " + total, "", "", "", ""});
        return filas;
    }

    /**
     * Columnas: Fecha y hora | Paciente | Médico | Motivo | Estado (con una
     * fila TOTAL al final).
     */
    public List<Object[]> filasCitasPorMes(int mes, int anio) {
        List<Object[]> filas = new ArrayList<>();
        int total = 0;
        for (Cita c : citaDAO.listar()) {
            if (c.getFechaHora().getMonthValue() == mes && c.getFechaHora().getYear() == anio) {
                filas.add(filaCitaConMedico(c));
                total++;
            }
        }
        filas.add(new Object[]{"TOTAL: " + total, "", "", "", ""});
        return filas;
    }

    /**
     * Columnas: Fecha | Diagnóstico | Tratamiento (ordenado por fecha).
     */
    public List<Object[]> filasHistorialPorPaciente(Paciente p) {
        List<Object[]> filas = new ArrayList<>();
        List<HistorialClinico> lista = historialDAO.listarPorPaciente(p);   // es una copia
        lista.sort(Comparator.comparing(HistorialClinico::getFecha));
        for (HistorialClinico h : lista) {
            filas.add(new Object[]{h.getFecha().format(FECHA), h.getDiagnostico(), h.getTratamiento()});
        }
        return filas;
    }

    /**
     * Columnas: Fecha | Paciente | Diagnóstico | Tratamiento | Receta (una fila
     * por receta).
     */
    public List<Object[]> filasRecetasYTratamientos() {
        List<Object[]> filas = new ArrayList<>();
        for (HistorialClinico h : historialDAO.listar()) {
            String fecha = h.getFecha().format(FECHA);
            String paciente = h.getPaciente().getNombreCompleto();
            List<Receta> recetas = (h.getCita() == null) ? new ArrayList<>() : recetaDAO.listarPorCita(h.getCita());
            if (recetas.isEmpty()) {
                filas.add(new Object[]{fecha, paciente, h.getDiagnostico(), h.getTratamiento(), "(sin recetas)"});
            }
            for (Receta r : recetas) {
                filas.add(new Object[]{fecha, paciente, h.getDiagnostico(), h.getTratamiento(),
                    r.getMedicamento() + " - " + r.getIndicaciones()});
            }
        }
        return filas;
    }

    // CITAS NO ASISTIDAS POR MÉDICO
    private int contarNoAsistidas(Medico m) {
        int total = 0;
        for (Cita c : citaDAO.listarPorMedico(m)) {
            if (c.getEstado().equals("NO_ASISTIO")) {
                total++;
            }
        }
        return total;
    }

    public String textoCitasNoAsistidasPorMedico() {
        StringBuilder sb = new StringBuilder("=== CITAS NO ASISTIDAS POR MÉDICO ===\n\n");
        for (Medico m : medicoDAO.listar()) {
            sb.append("  ").append(m.getNombreCompleto()).append(" | ")
                    .append(m.getEspecialidad().getNombre()).append(" | Inasistencias: ")
                    .append(contarNoAsistidas(m)).append("\n");
        }
        return sb.toString();
    }

    public void citasNoAsistidasPorMedico() {
        System.out.print("\n" + textoCitasNoAsistidasPorMedico());
    }

    /**
     * Columnas: Médico | Especialidad | Inasistencias.
     */
    public List<Object[]> filasCitasNoAsistidasPorMedico() {
        List<Object[]> filas = new ArrayList<>();
        for (Medico m : medicoDAO.listar()) {
            filas.add(new Object[]{m.getNombreCompleto(), m.getEspecialidad().getNombre(), contarNoAsistidas(m)});
        }
        return filas;
    }

    /**
     * Fila común de los reportes por día y por mes.
     */
    private Object[] filaCitaConMedico(Cita c) {
        return new Object[]{c.getFechaHora().format(FECHA_HORA), c.getPaciente().getNombreCompleto(),
            "Dr(a). " + c.getMedico().getNombreCompleto(), c.getMotivo(), c.getEstado()};
    }
}
