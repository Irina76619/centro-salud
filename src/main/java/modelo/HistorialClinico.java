package modelo;

import java.time.LocalDate;

/** Registro de diagnóstico y tratamiento de un paciente, ligado a la cita donde se atendió. */
public class HistorialClinico {
    private int id;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;
    private Paciente paciente;
    private Cita cita;

    public HistorialClinico() { }

    public HistorialClinico(String diagnostico, String tratamiento, LocalDate fecha, Paciente paciente, Cita cita) {
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
        this.paciente = paciente;
        this.cita = cita;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + fecha + " | Dx: " + diagnostico + " | Tto: " + tratamiento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
}
