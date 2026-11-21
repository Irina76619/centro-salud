package modelo;

import java.time.LocalDateTime;

/** Cita médica: une a un paciente con un médico en una fecha y hora. Su estado es PENDIENTE, ATENDIDA o CANCELADA. */
public class Cita {
    private int id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
    private Paciente paciente;
    private Medico medico;

    public Cita() { }

    public Cita(LocalDateTime fechaHora, String motivo, String estado, Paciente paciente, Medico medico) {
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = estado;
        this.paciente = paciente;
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + fechaHora.toString().replace('T', ' ') + " | Paciente: " + paciente.getNombreCompleto()
                + " | Dr(a). " + medico.getNombreCompleto() + " (" + medico.getEspecialidad().getNombre() + ")"
                + " | " + motivo + " | " + estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
}
