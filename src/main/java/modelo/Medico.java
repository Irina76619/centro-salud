package modelo;

/**
 * HERENCIA: Medico hereda de Persona (id, dni, nombre, apellidos, edad).
 * Agrega su código de acceso, su clave y su especialidad.
 */
public class Medico extends Persona {

    private String codigoMedico;
    private String clave;
    private Especialidad especialidad;

    public Medico() {
    }

    // SOBRECARGA DE CONSTRUCTORES: este recibe todos los datos del médico.
    public Medico(String dni, String nombre, String apellidos, int edad,
            String codigoMedico, String clave, Especialidad especialidad) {
        super(dni, nombre, apellidos, edad);
        this.codigoMedico = codigoMedico;
        this.clave = clave;
        this.especialidad = especialidad;
    }

    /** POLIMORFISMO: muestra el código y la especialidad del médico. */
    @Override
    public void mostrarInfo() {
        System.out.println("[Medico " + getId() + "] " + codigoMedico + " | Dr(a). " + getNombreCompleto()
                + " | Especialidad: " + especialidad.getNombre());
    }

    /** Texto que se ve en las listas desplegables. */
    @Override
    public String toString() {
        return codigoMedico + " - " + getNombreCompleto();
    }

    public String getCodigoMedico() { return codigoMedico; }
    public void setCodigoMedico(String codigoMedico) { this.codigoMedico = codigoMedico; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }
}
