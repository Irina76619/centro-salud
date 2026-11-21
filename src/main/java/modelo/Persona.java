package modelo;

/**
 * CLASE ABSTRACTA: base común de Medico, Paciente y Administrativo.
 * No se puede instanciar; solo sirve para que las subclases hereden sus datos.
 * ENCAPSULAMIENTO: los atributos son privados y se usan solo con getters/setters.
 */
public abstract class Persona {
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;
    private int edad;

    // SOBRECARGA DE CONSTRUCTORES: uno vacío y otro con todos los datos.
    public Persona() { }

    public Persona(String dni, String nombre, String apellidos, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    /** POLIMORFISMO: cada subclase lo implementa a su manera. */
    public abstract void mostrarInfo();

    public String getNombreCompleto() { return nombre + " " + apellidos; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
}
