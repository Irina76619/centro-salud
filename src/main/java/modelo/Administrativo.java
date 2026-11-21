package modelo;

/**
 * HERENCIA: Administrativo hereda de Persona y agrega usuario y clave de acceso.
 */
public class Administrativo extends Persona {
    private String usuario;
    private String clave;

    public Administrativo() { }

    // SOBRECARGA DE CONSTRUCTORES: este recibe todos los datos.
    public Administrativo(String dni, String nombre, String apellidos, int edad, String usuario, String clave) {
        super(dni, nombre, apellidos, edad);
        this.usuario = usuario;
        this.clave = clave;
    }

    /** POLIMORFISMO: muestra el usuario del administrativo. */
    @Override
    public void mostrarInfo() {
        System.out.println("[Administrativo " + getId() + "] " + usuario + " | " + getNombreCompleto());
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
