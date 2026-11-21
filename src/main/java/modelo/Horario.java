package modelo;

import java.time.LocalTime;
/** Día y rango de horas en que un médico atiende. */
public class Horario {
    private int id;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Medico medico;

    public Horario() { }

    public Horario(String diaSemana, LocalTime horaInicio, LocalTime horaFin, Medico medico) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "[" + id + "] Dr(a). " + medico.getNombreCompleto() + " | " + diaSemana + " " + horaInicio + " - " + horaFin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
}
