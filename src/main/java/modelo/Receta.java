package modelo;

import java.time.LocalDate;

/** Medicamento e indicaciones que el médico receta en una cita. */
public class Receta {
    private int id;
    private String medicamento;
    private String indicaciones;
    private LocalDate fecha;
    private Cita cita;

    public Receta() { }

    public Receta(String medicamento, String indicaciones, LocalDate fecha, Cita cita) {
        this.medicamento = medicamento;
        this.indicaciones = indicaciones;
        this.fecha = fecha;
        this.cita = cita;
    }

    @Override
    public String toString() { return "[" + id + "] " + fecha + " | " + medicamento + " | " + indicaciones; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
}
