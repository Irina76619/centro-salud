package modelo;

/** Área médica del centro (Pediatría, Cardiología, etc.). Cada médico tiene una. */
public class Especialidad {
    private int id;
    private String nombre;

    public Especialidad() { }
    public Especialidad(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() { return id + " - " + nombre; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
