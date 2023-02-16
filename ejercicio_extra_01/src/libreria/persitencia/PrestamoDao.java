package libreria.persitencia;

import java.util.List;
import libreria.entidades.Prestamo;

public class PrestamoDao extends DAO<Prestamo>{
    @Override
    public void guardar(Prestamo prestamo) {
        super.guardar(prestamo);
    }

    public void eliminar(int id) throws Exception {
        Prestamo prestamo = buscarPorId(id);
        super.eliminar(prestamo);
    }

    @Override
    public void editar(Prestamo prestamo) {
        super.editar(prestamo);
    }

    public Prestamo buscarPorId(int id) throws Exception {
        conectar();
        Prestamo prestamo = em.find(Prestamo.class, id);
        desconectar();
        return prestamo;
    }
    public Prestamo validarPrestamo(int id) throws Exception {
        conectar();
        Prestamo prestamo = em.find(Prestamo.class, id);
        desconectar();
        return prestamo;
    }

    public List<Prestamo> listarTodos() throws Exception {
        conectar();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p")
                .getResultList();
        desconectar();
        return prestamos;
    }

    public List<Prestamo> buscarPorCliente(String nombre) throws Exception {
        conectar();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE"
                + " p.nombre LIKE '%" + nombre + "%'")
                .getResultList();
        desconectar();
        return prestamos;
    }
    
    public boolean buscarPorClienteID(int id) throws Exception {
        conectar();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE"
                + " p.CLIENTE_ID = " + id + "")
                .getResultList();
        desconectar();
        return prestamos.isEmpty();
    }
    public List<Prestamo> buscarPrestamosPorCliente(Long documento) {
        conectar();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p WHERE"
                + " p.CLIENTE_ID.DOCUMENTO = " + documento)
                .getResultList();
        desconectar();
        return prestamos;
    }
}
