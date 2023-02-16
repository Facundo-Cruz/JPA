package libreria.persitencia;

import java.util.List;
import libreria.entidades.Cliente;

public class ClienteDAO extends DAO<Cliente> {

    @Override
    public void guardar(Cliente cliente) {
        super.guardar(cliente);
    }

    public void eliminar(int id) throws Exception {
        Cliente cliente = buscarPorId(id);
        super.eliminar(cliente);
    }

    @Override
    public void editar(Cliente cliente) {
        super.editar(cliente);
    }

    public Cliente buscarPorId(int id) throws Exception {
        conectar();
        Cliente cliente = em.find(Cliente.class, id);
        desconectar();
        return cliente;
    }

    public Cliente buscarPorDocumento(Long documento) throws Exception {
        try {
            conectar();
            Cliente cliente = (Cliente) em.createQuery("SELECT c FROM Cliente c "
                    + "WHERE c.documento LIKE " + documento)
                    .getSingleResult();

            return cliente;
        } catch (Exception e) {
            return null;
        } finally {
            desconectar();
        }
    }

    public List<Cliente> listarTodos() throws Exception {
        conectar();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c")
                .getResultList();
        desconectar();
        return clientes;
    }

    public List<Cliente> buscarPorNombre(String nombre) throws Exception {
        conectar();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c WHERE"
                + " c.nombre LIKE '%" + nombre + "%'")
                .getResultList();
        desconectar();
        return clientes;
    }
}
