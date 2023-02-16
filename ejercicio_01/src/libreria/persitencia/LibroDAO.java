package libreria.persitencia;

import java.util.List;
import libreria.entidades.Libro;

public final class LibroDAO extends DAO<Libro> {

    @Override
    public void guardar(Libro libro) {
        super.guardar(libro);
    }

    public void eliminar(long id) throws Exception {
        Libro libro = buscarPorId(id);
        super.eliminar(libro);
    }

    @Override
    public void editar(Libro libro) {
        super.editar(libro);
    }

    public Libro buscarPorId(long id) throws Exception {
        conectar();
        Libro libro = em.find(Libro.class, id);
        desconectar();
        return libro;
    }

    public List<Libro> listarTodos() throws Exception {
        conectar();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l")
                .getResultList();
        desconectar();
        return libros;
    }

    public Libro buscarPorTitulo(String titulo) {
        conectar();
        Libro libro = (Libro) em.createQuery("SELECT l FROM Libro l WHERE"
                + " l.titulo = '" + titulo + "'")
                .getSingleResult();
        desconectar();
        return libro;
    }

    public List<Libro> buscarLibrosPorNombreDeAutor(String nombre) {
        conectar();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE"
                + " l.autor.nombre LIKE '%" + nombre + "%'")
                .getResultList();
        desconectar();
        return libros;
    }

    public List<Libro> buscarLibrosPorNombreDeEditorial(String nombre) {
        conectar();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE"
                + " l.editorial.nombre LIKE '%" + nombre + "%'")
                .getResultList();
        desconectar();
        return libros;
    }
}
