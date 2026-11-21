package modelo;

/**
 * HERENCIA: Paciente hereda de Persona y agrega el teléfono.
 */
public class Paciente extends Persona {

    private String telefono;

    // SOBRECARGA DE CONSTRUCTORES: uno vacío y otro con todos los datos.
    public Paciente() {
    }

    public Paciente(String dni, String nombre, String apellidos, int edad, String telefono) {
        super(dni, nombre, apellidos, edad);
        this.telefono = telefono;
    }

    /** POLIMORFISMO: muestra DNI, edad y teléfono del paciente. */
    @Override
    public void mostrarInfo() {
        System.out.println("[Paciente " + getId() + "] DNI " + getDni() + " | " + getNombreCompleto()
                + " | " + getEdad() + " anios | Tel: " + telefono);
    }

    /** Texto que se ve en las listas desplegables. */
    @Override
    public String toString() {
        return getDni() + " - " + getNombreCompleto();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
