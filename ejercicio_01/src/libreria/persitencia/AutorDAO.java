package libreria.persitencia;

import java.util.List;
import libreria.entidades.Autor;

public final class AutorDAO extends DAO<Autor> {

    @Override
    public void guardar(Autor autor) {
        super.guardar(autor);
    }

    public void eliminar(int id) throws Exception {
        Autor autor = buscarPorId(id);
        super.eliminar(autor);
    }

    @Override
    public void editar(Autor autor) {
        super.editar(autor);
    }

    public Autor buscarPorId(int id) throws Exception {
        conectar();
        Autor autor = em.find(Autor.class, id);
        desconectar();
        return autor;
    }

    public List<Autor> listarTodos() throws Exception {
        conectar();
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a")
                .getResultList();
        desconectar();
        return autores;
    }

    public List<Autor> buscarPorNombre(String nombre) throws Exception {
        conectar();
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a WHERE"
                + " a.nombre LIKE '%" + nombre + "%'")
                .getResultList();
        desconectar();
        return autores;
    }
}
