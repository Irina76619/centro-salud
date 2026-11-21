package vista;

import datos.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import modelo.*;
import servicio.ServicioLogin;
import servicio.ServicioReportes;
import utilitarios.Entrada;

public class MenuConsola {
    private ServicioLogin servicioLogin;
    private ServicioReportes reportes;
    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO;
    private EspecialidadDAO especialidadDAO;
    private HorarioDAO horarioDAO;
    private CitaDAO citaDAO;
    private RecetaDAO recetaDAO;
    private HistorialClinicoDAO historialDAO;

    public MenuConsola(ServicioLogin servicioLogin, ServicioReportes reportes,
                       PacienteDAO pacienteDAO, MedicoDAO medicoDAO, EspecialidadDAO especialidadDAO,
                       HorarioDAO horarioDAO, CitaDAO citaDAO, RecetaDAO recetaDAO,
                       HistorialClinicoDAO historialDAO) {
        this.servicioLogin = servicioLogin;
        this.reportes = reportes;
        this.pacienteDAO = pacienteDAO;
        this.medicoDAO = medicoDAO;
        this.especialidadDAO = especialidadDAO;
        this.horarioDAO = horarioDAO;
        this.citaDAO = citaDAO;
        this.recetaDAO = recetaDAO;
        this.historialDAO = historialDAO;
    }

    // ================= LOGIN =================

    public void login() {
        System.out.println("=== CENTRO DE SALUD ===");
        int rol = 0;
        while (rol != 1 && rol != 2) {
            rol = Entrada.leerInt("1. Administrativo   2. Medico: ");
        }

        for (int intento = 1; intento <= 3; intento++) {
            String usuario = Entrada.leerTexto(rol == 1 ? "Usuario: " : "Codigo de medico: ");
            String clave = Entrada.leerTexto("Clave: ");

            if (rol == 1) {
                Administrativo a = servicioLogin.loginAdministrativo(usuario, clave);
                if (a != null) {
                    System.out.println("Bienvenido(a) " + a.getNombreCompleto());
                    menuAdministrativo();
                    return;
                }
            } else {
                Medico m = servicioLogin.loginMedico(usuario, clave);
                if (m != null) {
                    System.out.println("Bienvenido(a) Dr(a). " + m.getNombreCompleto());
                    menuMedico(m);
                    return;
                }
            }
            System.out.println("Datos incorrectos. Intento " + intento + " de 3.");
        }
        System.out.println("Demasiados intentos. El programa se cierra.");
    }

    // ================= MENU MEDICO =================

    public void menuMedico(Medico medico) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- MENU MEDICO ---");
            System.out.println("1. Ver mis citas");
            System.out.println("2. Ver mi horario");
            System.out.println("3. Atender una cita");
            System.out.println("4. Ver historial de un paciente");
            System.out.println("5. Editar un registro del historial");
            System.out.println("0. Cerrar sesion");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: verMisCitas(medico); break;
                case 2: verMiHorario(medico); break;
                case 3: atenderCita(medico); break;
                case 4: verHistorial(medico); break;
                case 5: editarHistorial(medico); break;
                case 0: System.out.println("Sesion cerrada."); break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void verMisCitas(Medico medico) {
        for (Cita c : citaDAO.listarPorMedico(medico)) {
            System.out.println(c);
        }
    }

    private void verMiHorario(Medico medico) {
        for (Horario h : horarioDAO.listarPorMedico(medico)) {
            System.out.println(h);
        }
    }

    private void atenderCita(Medico medico) {
        Cita cita = citaDAO.buscar(Entrada.leerInt("ID de la cita: "));
        // Seguridad: la cita debe existir y ser de este médico
        if (cita == null || !cita.getMedico().getCodigoMedico().equals(medico.getCodigoMedico())) {
            System.out.println("Esa cita no existe o no es suya.");
            return;
        }
        if (!cita.getEstado().equals("PENDIENTE")) {
            System.out.println("La cita ya esta " + cita.getEstado() + ".");
            return;
        }

        String diagnostico = Entrada.leerTexto("Diagnostico: ");
        String tratamiento = Entrada.leerTexto("Tratamiento: ");
        historialDAO.insertar(new HistorialClinico(diagnostico, tratamiento,
                LocalDate.now(), cita.getPaciente(), cita));

        while (Entrada.leerTexto("Agregar receta? (s/n): ").equalsIgnoreCase("s")) {
            String medicamento = Entrada.leerTexto("Medicamento: ");
            String indicaciones = Entrada.leerTexto("Indicaciones: ");
            recetaDAO.insertar(new Receta(medicamento, indicaciones, LocalDate.now(), cita));
        }

        cita.setEstado("ATENDIDA"); 
        citaDAO.actualizar(cita);
        System.out.println("Cita atendida y cerrada.");
    }

    private Paciente pedirMiPaciente(Medico medico) {
        String dni = Entrada.leerTexto("DNI del paciente: ");
        Paciente p = pacienteDAO.buscar(dni);
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return null;
        }
        for (Cita c : citaDAO.listarPorMedico(medico)) {
            if (c.getPaciente().getDni().equals(dni)) return p;
        }
        System.out.println("Ese paciente no tiene citas con usted.");
        return null;
    }

    private void verHistorial(Medico medico) {
        Paciente p = pedirMiPaciente(medico);
        if (p == null) return;
        for (HistorialClinico h : historialDAO.listarPorPaciente(p)) {
            System.out.println(h);
        }
    }

    private void editarHistorial(Medico medico) {
        Paciente p = pedirMiPaciente(medico);
        if (p == null) return;
        for (HistorialClinico h : historialDAO.listarPorPaciente(p)) {
            System.out.println(h);
        }
        HistorialClinico h = historialDAO.buscar(Entrada.leerInt("ID del registro a editar: "));
        if (h == null || h.getCita() == null
                || !h.getCita().getMedico().getCodigoMedico().equals(medico.getCodigoMedico())) {
            System.out.println("Ese registro no existe o no es suyo.");
            return;
        }
        h.setDiagnostico(Entrada.leerTexto("Nuevo diagnostico: "));
        h.setTratamiento(Entrada.leerTexto("Nuevo tratamiento: "));
        historialDAO.actualizar(h);
        System.out.println("Registro actualizado.");
    }

    // ================= MENU ADMINISTRATIVO =================

    private static final String[] DIAS = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

    public void menuAdministrativo() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- MENU ADMINISTRATIVO ---");
            System.out.println("1. Pacientes");
            System.out.println("2. Medicos");
            System.out.println("3. Especialidades");
            System.out.println("4. Horarios");
            System.out.println("5. Citas");
            System.out.println("6. Reportes");
            System.out.println("0. Cerrar sesion");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: menuPacientes(); break;
                case 2: menuMedicos(); break;
                case 3: menuEspecialidades(); break;
                case 4: menuHorarios(); break;
                case 5: menuCitas(); break;
                case 6: menuReportes(); break;
                case 0: System.out.println("Sesion cerrada."); break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    // ---------- Métodos auxiliares: piden un dato y lo validan ----------

    private String pedirDni() {
        String dni = Entrada.leerTexto("DNI (8 digitos): ");
        while (!dni.matches("\\d{8}")) {
            dni = Entrada.leerTexto("DNI invalido, escriba 8 digitos: ");
        }
        return dni;
    }

    private int pedirEdad(int min, int max) {
        int edad = Entrada.leerInt("Edad: ");
        while (edad < min || edad > max) {
            edad = Entrada.leerInt("Edad invalida (" + min + " a " + max + "): ");
        }
        return edad;
    }

    private String pedirDia() {
        while (true) {
            String dia = Entrada.leerTexto("Dia (Lunes ... Domingo): ");
            for (String d : DIAS) {
                if (d.equalsIgnoreCase(dia)) return d;
            }
            System.out.println("Dia invalido.");
        }
    }

    private Paciente pedirPaciente() {
        Paciente p = pacienteDAO.buscar(Entrada.leerTexto("DNI del paciente: "));
        if (p == null) System.out.println("Paciente no encontrado.");
        return p;
    }

    private Medico pedirMedico() {
        Medico m = medicoDAO.buscar(Entrada.leerTexto("Codigo del medico: "));
        if (m == null) System.out.println("Medico no encontrado.");
        return m;
    }

    private Especialidad pedirEspecialidad() {
        for (Especialidad e : especialidadDAO.listar()) System.out.println(e);
        Especialidad e = especialidadDAO.buscar(Entrada.leerInt("ID de la especialidad: "));
        if (e == null) System.out.println("Especialidad no encontrada.");
        return e;
    }

    private Cita pedirCita() {
        Cita c = citaDAO.buscar(Entrada.leerInt("ID de la cita: "));
        if (c == null) System.out.println("Cita no encontrada.");
        return c;
    }

    // ---------- PACIENTES ----------

    private void menuPacientes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- PACIENTES --");
            System.out.println("1. Registrar  2. Listar  3. Buscar  4. Actualizar  5. Eliminar  0. Volver");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: registrarPaciente(); break;
                case 2:
                    for (Paciente p : pacienteDAO.listar()) p.mostrarInfo();
                    break;
                case 3: buscarPaciente(); break;
                case 4: actualizarPaciente(); break;
                case 5: eliminarPaciente(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarPaciente() {
        String dni = pedirDni();
        String nombre = Entrada.leerTexto("Nombre: ");
        String apellidos = Entrada.leerTexto("Apellidos: ");
        int edad = pedirEdad(0, 120);
        String telefono = Entrada.leerTexto("Telefono: ");
        if (pacienteDAO.insertar(new Paciente(dni, nombre, apellidos, edad, telefono))) {
            System.out.println("Paciente registrado.");
        }
    }

    private void buscarPaciente() {
        Paciente p;
        if (Entrada.leerInt("Buscar por 1. DNI  2. Nombre y apellidos: ") == 1) {
            p = pacienteDAO.buscar(Entrada.leerTexto("DNI: "));
        } else {
            String nombre = Entrada.leerTexto("Nombre: ");
            p = pacienteDAO.buscar(nombre, Entrada.leerTexto("Apellidos: "));
        }
        if (p == null) System.out.println("Paciente no encontrado.");
        else p.mostrarInfo();
    }

    private void actualizarPaciente() {
        Paciente p = pedirPaciente();
        if (p == null) return;
        p.setNombre(Entrada.leerTexto("Nuevo nombre: "));
        p.setApellidos(Entrada.leerTexto("Nuevos apellidos: "));
        p.setEdad(pedirEdad(0, 120));
        p.setTelefono(Entrada.leerTexto("Nuevo telefono: "));
        pacienteDAO.actualizar(p);
        System.out.println("Paciente actualizado.");
    }

    private void eliminarPaciente() {
        Paciente p = pedirPaciente();
        if (p == null) return;
        if (!citaDAO.listarPorPaciente(p).isEmpty()) {
            System.out.println("No se puede eliminar: el paciente tiene citas.");
            return;
        }
        pacienteDAO.eliminar(p.getDni());
        System.out.println("Paciente eliminado.");
    }

    // ---------- MEDICOS ----------

    private void menuMedicos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- MEDICOS --");
            System.out.println("1. Registrar  2. Listar  3. Actualizar  4. Eliminar  0. Volver");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: registrarMedico(); break;
                case 2:
                    for (Medico m : medicoDAO.listar()) m.mostrarInfo();
                    break;
                case 3: actualizarMedico(); break;
                case 4: eliminarMedico(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarMedico() {
        Especialidad e = pedirEspecialidad();
        if (e == null) return;
        String dni = pedirDni();
        String nombre = Entrada.leerTexto("Nombre: ");
        String apellidos = Entrada.leerTexto("Apellidos: ");
        int edad = pedirEdad(18, 100);
        String codigo = Entrada.leerTexto("Codigo de medico: ");
        String clave = Entrada.leerTexto("Clave: ");
        if (medicoDAO.insertar(new Medico(dni, nombre, apellidos, edad, codigo, clave, e))) {
            System.out.println("Medico registrado.");
        }
    }

    private void actualizarMedico() {
        Medico m = pedirMedico();
        if (m == null) return;
        m.setNombre(Entrada.leerTexto("Nuevo nombre: "));
        m.setApellidos(Entrada.leerTexto("Nuevos apellidos: "));
        m.setEdad(pedirEdad(18, 100));
        m.setClave(Entrada.leerTexto("Nueva clave: "));
        System.out.println("Nueva especialidad:");
        Especialidad e = pedirEspecialidad();
        if (e != null) m.setEspecialidad(e);   
        medicoDAO.actualizar(m);
        System.out.println("Medico actualizado.");
    }

    private void eliminarMedico() {
        Medico m = pedirMedico();
        if (m == null) return;
        if (!citaDAO.listarPorMedico(m).isEmpty()) {
            System.out.println("No se puede eliminar: el medico tiene citas.");
            return;
        }
        for (Horario h : horarioDAO.listarPorMedico(m)) horarioDAO.eliminar(h.getId());
        medicoDAO.eliminar(m.getCodigoMedico());
        System.out.println("Medico eliminado.");
    }

    // ---------- ESPECIALIDADES ----------

    private void menuEspecialidades() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- ESPECIALIDADES --");
            System.out.println("1. Registrar  2. Listar  3. Actualizar  4. Eliminar  0. Volver");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1:
                    if (especialidadDAO.insertar(new Especialidad(Entrada.leerTexto("Nombre: ")))) {
                        System.out.println("Especialidad registrada.");
                    }
                    break;
                case 2:
                    for (Especialidad e : especialidadDAO.listar()) System.out.println(e);
                    break;
                case 3: actualizarEspecialidad(); break;
                case 4: eliminarEspecialidad(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void actualizarEspecialidad() {
        Especialidad e = pedirEspecialidad();
        if (e == null) return;
        Especialidad nueva = new Especialidad(Entrada.leerTexto("Nuevo nombre: "));
        nueva.setId(e.getId());
        if (especialidadDAO.actualizar(nueva)) {
            for (Medico m : medicoDAO.listar()) {
                if (m.getEspecialidad().getId() == nueva.getId()) m.setEspecialidad(nueva);
            }
            System.out.println("Especialidad actualizada.");
        }
    }

    private void eliminarEspecialidad() {
        Especialidad e = pedirEspecialidad();
        if (e == null) return;
        for (Medico m : medicoDAO.listar()) {
            if (m.getEspecialidad().getId() == e.getId()) {
                System.out.println("No se puede eliminar: hay medicos con esa especialidad.");
                return;
            }
        }
        especialidadDAO.eliminar(e.getId());
        System.out.println("Especialidad eliminada.");
    }

    // ---------- HORARIOS ----------

    private void menuHorarios() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- HORARIOS --");
            System.out.println("1. Registrar  2. Listar  3. Actualizar  4. Eliminar  0. Volver");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: registrarHorario(); break;
                case 2:
                    for (Horario h : horarioDAO.listar()) System.out.println(h);
                    break;
                case 3: actualizarHorario(); break;
                case 4:
                    System.out.println(horarioDAO.eliminar(Entrada.leerInt("ID del horario: "))
                            ? "Horario eliminado." : "Horario no encontrado.");
                    break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarHorario() {
        Medico m = pedirMedico();
        if (m == null) return;
        String dia = pedirDia();
        LocalTime inicio = Entrada.leerHora("Hora de inicio ");
        LocalTime fin = Entrada.leerHora("Hora de fin ");
        if (!fin.isAfter(inicio)) {
            System.out.println("La hora de fin debe ser posterior a la de inicio.");
            return;
        }
        horarioDAO.insertar(new Horario(dia, inicio, fin, m));
        System.out.println("Horario registrado.");
    }

    private void actualizarHorario() {
        Horario h = horarioDAO.buscar(Entrada.leerInt("ID del horario: "));
        if (h == null) {
            System.out.println("Horario no encontrado.");
            return;
        }
        String dia = pedirDia();
        LocalTime inicio = Entrada.leerHora("Nueva hora de inicio ");
        LocalTime fin = Entrada.leerHora("Nueva hora de fin ");
        if (!fin.isAfter(inicio)) {
            System.out.println("La hora de fin debe ser posterior a la de inicio.");
            return;
        }
        h.setDiaSemana(dia);    
        h.setHoraInicio(inicio);
        h.setHoraFin(fin);
        horarioDAO.actualizar(h);
        System.out.println("Horario actualizado.");
    }

    // ---------- CITAS ----------

    private void menuCitas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- CITAS --");
            System.out.println("1. Registrar  2. Listar  3. Citas de un paciente");
            System.out.println("4. Reprogramar  5. Cancelar  6. Eliminar  0. Volver");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: registrarCita(); break;
                case 2:
                    for (Cita c : citaDAO.listar()) System.out.println(c);
                    break;
                case 3: citasDeUnPaciente(); break;
                case 4: reprogramarCita(); break;
                case 5: cancelarCita(); break;
                case 6: eliminarCita(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrarCita() {
        Paciente p = pedirPaciente();
        if (p == null) return;
        Medico m = pedirMedico();
        if (m == null) return;
        LocalDateTime fechaHora = Entrada.leerFechaHora("Fecha y hora ");
        String motivo = Entrada.leerTexto("Motivo: ");
        if (citaDAO.insertar(new Cita(fechaHora, motivo, "PENDIENTE", p, m))) {
            System.out.println("Cita registrada.");
        }
    }

    private void citasDeUnPaciente() {
        Paciente p = pedirPaciente();
        if (p == null) return;
        for (Cita c : citaDAO.listarPorPaciente(p)) System.out.println(c);
    }

    private void reprogramarCita() {
        Cita c = pedirCita();
        if (c == null) return;
        if (!c.getEstado().equals("PENDIENTE")) {
            System.out.println("Solo se pueden reprogramar citas PENDIENTES.");
            return;
        }
        LocalDateTime anterior = c.getFechaHora();
        c.setFechaHora(Entrada.leerFechaHora("Nueva fecha y hora "));
        if (citaDAO.actualizar(c)) {
            c.setMotivo(Entrada.leerTexto("Nuevo motivo: "));
            System.out.println("Cita actualizada.");
        } else {
            c.setFechaHora(anterior); 
        }
    }

    private void cancelarCita() {
        Cita c = pedirCita();
        if (c == null) return;
        if (!c.getEstado().equals("PENDIENTE")) {
            System.out.println("Solo se pueden cancelar citas PENDIENTES.");
            return;
        }
        c.setEstado("CANCELADA");
        citaDAO.actualizar(c);
        System.out.println("Cita cancelada.");
    }

    private void eliminarCita() {
        Cita c = pedirCita();
        if (c == null) return;
        if (c.getEstado().equals("ATENDIDA")) {
            System.out.println("No se puede eliminar una cita ATENDIDA.");
            return;
        }
        citaDAO.eliminar(c.getId());
        System.out.println("Cita eliminada.");
    }

    // ---------- REPORTES ----------

    private void menuReportes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- REPORTES --");
            System.out.println("1. Citas por medico");
            System.out.println("2. Citas por especialidad");
            System.out.println("3. Citas por dia");
            System.out.println("4. Citas por mes");
            System.out.println("5. Historial clinico de un paciente");
            System.out.println("6. Recetas y tratamientos");
            System.out.println("0. Volver");
            op = Entrada.leerInt("Opcion: ");

            switch (op) {
                case 1: reportes.citasPorMedico(); break;
                case 2: reportes.citasPorEspecialidad(); break;
                case 3: reportes.citasPorDia(Entrada.leerFecha("Fecha ")); break;
                case 4:
                    int mes = Entrada.leerInt("Mes (1-12): ");
                    int anio = Entrada.leerInt("Anio: ");
                    if (mes < 1 || mes > 12) System.out.println("Mes invalido.");
                    else reportes.citasPorMes(mes, anio);
                    break;
                case 5:
                    Paciente p = pedirPaciente();
                    if (p != null) reportes.historialPorPaciente(p);
                    break;
                case 6: reportes.recetasYTratamientos(); break;
                case 0: break;
                default: System.out.println("Opcion invalida.");
            }
        }
    }
}