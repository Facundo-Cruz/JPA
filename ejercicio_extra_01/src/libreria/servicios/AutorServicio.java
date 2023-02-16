package libreria.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.persitencia.AutorDAO;

public class AutorServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    public final AutorDAO dao;

    public AutorServicio() {
        this.dao = new AutorDAO();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\t.:Menu AUTOR:. ");
            System.out.println("1 - Cargar un autor");
            System.out.println("2 - Modificar un autor");
            System.out.println("3 - Eliminar un autor");
            System.out.println("4 - Buscar autor por nombre");
            System.out.println("5 - Salir");
            System.out.println("Elija su opcion:");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    crearAutor();
                    break;
                case 2:
                    modificarAutor();
                    break;
                case 3:
                    eliminarAutor();
                    break;
                case 4:
                    buscarAutorPorNombre();
                    break;
                case 5:
                    System.out.println("Hasta Luego...");
                    break;
                default:
                    System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                    break;
            }
        } while (opcion != 5);
    }

    private void crearAutor() {
        try {
            Autor autor = new Autor();
            autor.setAlta(true);

            System.out.println("Ingrese el nombre");
            autor.setNombre(leer.next());
            if (autor.getNombre().trim().isEmpty()) {
                throw new Exception("Debe indicar el nombre");
            }

            dao.guardar(autor);
            System.out.println("Autor creado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarAutor() {
        try {
            if (!buscarAutorPorNombre()) {
                System.out.println("Ingrese el id del autor a modificar");
                Autor autor = dao.buscarPorId(leer.nextInt());
                if (autor == null) {
                    throw new Exception("Debe indicar un autor");
                }
                System.out.println("El autor indicado corresponde a: " + autor.toString());
                int opcion;
                do {
                    System.out.println("\tMenu editor ");
                    System.out.println("1 - Cambiar el nombre");
                    System.out.println("2 - Cambiar el alta");
                    System.out.println("3 - Salir");
                    System.out.println("Elija su opcion:");
                    opcion = leer.nextInt();

                    switch (opcion) {
                        case 1:
                            System.out.println("Ingrese el nuevo nombre");
                            autor.setNombre(leer.next());
                            System.out.println("Nombre cambiado exitosamente");
                            break;
                        case 2:
                            autor.setAlta(!autor.getAlta());
                            System.out.println("Alta cambiada exitosamente");
                            break;
                        case 3:
                            System.out.println("Hasta Luego...");
                            break;
                        default:
                            System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                            break;
                    }
                } while (opcion != 3);
                dao.editar(autor);
                System.out.println("Autor editado exitosamente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarAutor() {
        try {
            if (!buscarAutorPorNombre()) {
                System.out.println("Ingrese el id del autor a eliminar");
                Autor autor = dao.buscarPorId(leer.nextInt());
                if (autor == null) {
                    throw new Exception("Debe indicar un autor");
                }
                System.out.println("El autor indicado corresponde a: " + autor.toString());
                System.out.println("¿Seguro que desea eliminarlo?(S/N)");
                if (leer.next().equalsIgnoreCase("S")) {
                    dao.eliminar(autor.getId());
                    System.out.println("Autor eliminado exitosamente");
                } else {
                    System.out.println("Volviendo al menu....");
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean buscarAutorPorNombre() {
        List<Autor> autores = new ArrayList();
        try {
            System.out.println("Ingrese el nombre del autor a buscar");
            String nombre = leer.next();
            autores = dao.buscarPorNombre(nombre);
            for (Autor aux : autores) {
                System.out.println(aux.toString());
            }
            if (autores.isEmpty()) {
                System.out.println("No se encontraron resultados");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return autores.isEmpty();
    }
}
