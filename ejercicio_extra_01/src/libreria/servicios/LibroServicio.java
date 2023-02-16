package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.persitencia.LibroDAO;

public class LibroServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    private final LibroDAO dao;
    AutorServicio autorService = new AutorServicio();
    EditorialServicio editorialService = new EditorialServicio();

    public LibroServicio() {
        this.dao = new LibroDAO();
    }

    public void menu() throws Exception {
        int opcion;
        do {
            System.out.println("\t.:Menu LIBRO:. ");
            System.out.println("1 - Cargar un libro");
            System.out.println("2 - Modificar un libro");
            System.out.println("3 - Eliminar un libro");
            System.out.println("4 - Buscar libro por ISBN");
            System.out.println("5 - Buscar libro por título");
            System.out.println("6 - Búsqueda de un libro/s por nombre de Autor");
            System.out.println("7 - Búsqueda de un libro/s por nombre de Editorial");
            System.out.println("8 - Salir");
            System.out.println("Elija su opcion:");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    crearLibro();
                    break;
                case 2:
                    modificarLibro();
                    break;
                case 3:
                    eliminarLibro();
                    break;
                case 4:
                    buscarLibroPorISBN();
                    break;
                case 5:
                    buscarLibroPorTitulo();
                    break;
                case 6:
                    buscarLibrosPorNombreDeAutor();
                    break;
                case 7:
                    buscarLibrosPorNombreDeEditorial();
                    break;
                case 8:
                    System.out.println("Hasta Luego...");
                    break;
                default:
                    System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                    break;
            }
        } while (opcion != 8);
    }

    private void crearLibro() throws Exception {
        try {
            Libro libro = new Libro();
            libro.setAlta(true);
            libro.setEjemplaresPrestados(0);

            System.out.println("Ingrese el titulo");
            libro.setTitulo(leer.next());
            if (libro.getTitulo().trim().isEmpty()) {
                throw new Exception("Debe ingresar un titulo");
            }

            System.out.println("Ingrese el año de publicación");
            libro.setAnio(leer.nextInt());
            if (libro.getAnio() <= 0) {
                throw new Exception("Debe ingresar un año válido");
            }

            System.out.println("Ingrese la cantidad de ejemplares");
            libro.setEjemplares(leer.nextInt());
            if (libro.getEjemplares() <= 0) {
                throw new Exception("Debe ingresar una cantidad válida de ejemplares");
            }
            libro.setEjemplaresRestantes(libro.getEjemplares());

            Autor autor = buscarAutor();
            if (autor == null) {
                throw new Exception("Debe seleccionar un autor");
            }
            libro.setAutor(autor);

            Editorial editorial = buscarEditorial();
            if (editorial == null) {
                throw new Exception("Debe seleccionar una editorial");
            }
            libro.setEditorial(editorial);
            dao.guardar(libro);
            System.out.println("Libro creado con éxito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Autor buscarAutor() {
        Autor autor = null;
        try {
            if (!autorService.buscarAutorPorNombre()) {
                System.out.println("Ingrese el id del autor a seleccionar");
                autor = autorService.dao.buscarPorId(leer.nextInt());
                if (autor == null) {
                    throw new Exception("Debe indicar un autor existente");
                }
                System.out.println("El autor " + autor.toString() + "fue seleccionado con éxito");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return autor;
        }
    }
    
    public Editorial buscarEditorial() {
        Editorial editorial = null;
        try {
            List<Editorial> editoriales = editorialService.dao.listarTodos();
            for (Editorial aux : editoriales) {
                System.out.println(aux.toString());
            }
            System.out.println("Ingrese el id de la editorial a seleccionar");
            editorial = editorialService.dao.buscarPorId(leer.nextInt());
            if (editorial == null) {
                throw new Exception("Debe indicar una editorial existente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return editorial;
        }
    }

    private void modificarLibro() throws Exception {
        try {
            List<Libro> libros = dao.listarTodos();
            for (Libro aux : libros) {
                System.out.println(aux.toString());
            }

            System.out.println("Ingrese el id del libro a modificar");
            Libro libro = dao.buscarPorId(leer.nextLong());
            if (libro == null) {
                throw new Exception("Debe indicar un id existente");
            }
            menuEditarLibro(libro);
            dao.editar(libro);
            System.out.println("Libro editado con éxito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuEditarLibro(Libro libro) throws Exception {
        try {
            int opcion;
            do {
                System.out.println("\tMenu LIBRO");
                System.out.println("1 - Editar título");
                System.out.println("2 - Editar año");
                System.out.println("3 - Editar ejemplares");
                System.out.println("4 - Editar ejemplares prestados");
                System.out.println("5 - Editar alta");
                System.out.println("6 - Editar autor");
                System.out.println("7 - Editar editorial");
                System.out.println("8 - Salir");
                System.out.println("Elija su opcion:");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el nuevo título");
                        libro.setTitulo(leer.next());
                        if (libro.getTitulo().trim().isEmpty()) {
                            throw new Exception("Debe indicar un título válido");
                        }
                        System.out.println("Título editado con éxito");
                        break;
                    case 2:
                        System.out.println("Ingrese el año nuevo");
                        libro.setAnio(leer.nextInt());
                        if (libro.getAnio() <= 0) {
                            throw new Exception("Debe indicar un año válido");
                        }
                        System.out.println("Año editado con éxito");
                        break;
                    case 3:
                        System.out.println("Ingrese la nueva cantidad de ejemplares");
                        libro.setEjemplares(leer.nextInt());
                        if (libro.getEjemplares() < libro.getEjemplaresPrestados()) {
                            throw new Exception("Indique una cantidad válida");
                        }
                        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
                        System.out.println("Cantidad de ejemplares actualizada con éxito");
                        break;
                    case 4:
                        System.out.println("Ingrese la nueva cantidad de ejemplares prestados");
                        libro.setEjemplaresPrestados(leer.nextInt());
                        if (libro.getEjemplaresPrestados() > libro.getEjemplares()) {
                            throw new Exception("Ingrese una cantidad valida");
                        }
                        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
                        System.out.println("Cantidad de ejemplares prestados actualizada con éxito");
                        break;
                    case 5:
                        libro.setAlta(!libro.getAlta());
                        System.out.println("Alta cambiada con éxito");
                        break;
                    case 6:
                        List<Autor> autores = autorService.dao.listarTodos();
                        autores.remove(libro.getAutor());
                        if (autores.isEmpty()) {
                            throw new Exception("No se encontraron autores");
                        }
                        for (Autor aux : autores) {
                            System.out.println(aux.toString());
                        }
                        System.out.println("Ingrese el id del autor a cambiar");
                        Autor autor = autorService.dao.buscarPorId(leer.nextInt());
                        if (autor == null) {
                            throw new Exception("La id ingresada no corresponde a ningún autor");
                        }
                        libro.setAutor(autor);
                        System.out.println("Autor actualizado con éxito");
                        break;
                    case 7:
                        List<Editorial> editoriales = editorialService.dao.listarTodos();
                        editoriales.remove(libro.getEditorial());
                        if (editoriales.isEmpty()) {
                            throw new Exception("No se encontraron editoriales");
                        }

                        for (Editorial aux : editoriales) {
                            System.out.println(aux.toString());
                        }
                        System.out.println("Ingrese el id de la editorial a cambiar");
                        Editorial editorial = editorialService.dao.buscarPorId(leer.nextInt());
                        if (editorial == null) {
                            System.out.println("La id ingresada no corresponde a ninguna editorial");
                        }
                        libro.setEditorial(editorial);
                        System.out.println("Editorial actualizada con éxito");
                        break;

                    case 8:
                        System.out.println("Hasta Luego...");
                        break;
                    default:
                        System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                        break;
                }
            } while (opcion != 8);
        } catch (Exception e) {
            throw e;
        }
    }

    private void eliminarLibro() {
        try {
            List<Libro> libros = dao.listarTodos();
            if (libros.isEmpty()) {
                throw new Exception("No se encontraron libros");
            }
            for (Libro aux : libros) {
                System.out.println(aux.toString());
            }
            System.out.println("Ingrese el isbn del libro a borrar");
            Libro libro = dao.buscarPorId(leer.nextLong());
            if (libro == null) {
                throw new Exception("El id ingresado no corresponde a ningún libro");
            }
            dao.eliminar(libro.getIsbn());
            System.out.println("Libro eliminado con éxitoß");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarLibroPorISBN() {
        try {
            System.out.println("Ingrese el ISBN a buscar");
            Libro libro = dao.buscarPorId(leer.nextLong());
            if (libro == null) {
                throw new Exception("El ISBN ingresado no corresponde a ningún libro");
            } else {
                System.out.println(libro.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarLibroPorTitulo() {
        try {
            System.out.println("Ingrese el título a buscar");
            Libro libro = dao.buscarPorTitulo(leer.next());
            if (libro == null) {
                throw new Exception("El título ingresado no corresponde a ningún libro");
            } else {
                System.out.println(libro.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Libro buscarLibroPorISBNPrestamo(Long ISBN) throws Exception {
        return dao.buscarPorId(ISBN);
    }

    private void buscarLibrosPorNombreDeAutor() {
        try {
            System.out.println("Ingrese el nombre del autor");
            List<Libro> libros = dao.buscarLibrosPorNombreDeAutor(leer.next());
            if (libros == null) {
                throw new Exception("El nombre de autor ingresado no corresponde a ningún libro");
            }
            for (Libro aux : libros) {
                System.out.println(aux.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarLibrosPorNombreDeEditorial() {
        try {
            System.out.println("Ingrese el nombre de la editorial");
            List<Libro> libros = dao.buscarLibrosPorNombreDeEditorial(leer.next());
            if (libros == null) {
                throw new Exception("El nombre de editorial ingresado no corresponde a ningún libro");
            }
            for (Libro aux : libros) {
                System.out.println(aux.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void devolverLibro(Libro libro){
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()-1);
        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()+1);
    }
}
