package servicio;

import datos.AdministrativoDAO;
import datos.MedicoDAO;
import modelo.Administrativo;
import modelo.Medico;

/** Valida el usuario y la clave de administrativos y médicos. */
public class ServicioLogin {
    private AdministrativoDAO adminDAO;
    private MedicoDAO medicoDAO;

    public ServicioLogin(AdministrativoDAO adminDAO, MedicoDAO medicoDAO) {
        this.adminDAO = adminDAO;
        this.medicoDAO = medicoDAO;
    }

    /** Devuelve el administrativo si usuario y clave son correctos; si no, null. */
    public Administrativo loginAdministrativo(String usuario, String clave) {
        Administrativo a = adminDAO.buscarPorUsuario(usuario);   
        if (a != null && a.getClave().equals(clave)) {        
            return a;                                        
        }
        return null;                                            
    }

    /** Devuelve el médico si código y clave son correctos; si no, null. */
    public Medico loginMedico(String codigo, String clave) {
        Medico m = medicoDAO.buscar(codigo);
        if (m != null && m.getClave().equals(clave)) {
            return m;
        }
        return null;
    }
}