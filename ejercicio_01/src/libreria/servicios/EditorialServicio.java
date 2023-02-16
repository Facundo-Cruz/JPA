package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import libreria.entidades.Editorial;
import libreria.persitencia.EditorialDAO;

public class EditorialServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    public final EditorialDAO dao;

    public EditorialServicio() {
        this.dao = new EditorialDAO();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\t.:Menu EDITORIAL:.");
            System.out.println("1 - Cargar una editorial");
            System.out.println("2 - Modificar una editorial");
            System.out.println("3 - Eliminar una editorial");
            System.out.println("4 - Salir");
            System.out.println("Elija su opcion:");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    crearEditorial();
                    break;
                case 2:
                    modificarEditorial();
                    break;
                case 3:
                    eliminarEditorial();
                    break;
                case 4:
                    System.out.println("Hasta Luego...");
                    break;
                default:
                    System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                    break;
            }
        } while (opcion != 4);
    }

    private void crearEditorial() {
        try {
            Editorial editorial = new Editorial();
            editorial.setAlta(true);

            System.out.println("Ingrese el nombre");
            editorial.setNombre(leer.next());
            if (editorial.getNombre().trim().isEmpty()) {
                throw new Exception("Debe indicar un nombre");
            }

            dao.guardar(editorial);
            System.out.println("Editorial registrada con éxito");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarEditorial() {
        try {
            List<Editorial> editoriales = dao.listarTodos();
            for (Editorial aux : editoriales) {
                System.out.println(aux.toString());
            }
            System.out.println("Ingrese el id correspondiente de la editorial a editar");
            Editorial editorial = dao.buscarPorId(leer.nextInt());
            if (editorial == null) {
                throw new Exception("Id inexistente");
            }
            int opcion;
            do {
                System.out.println("\tMenu EDITAR");
                System.out.println("1 - Cambiar nombre");
                System.out.println("2 - Cambiar alta");
                System.out.println("3 - Salir");
                System.out.println("Elija su opcion:");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el nombre");
                        editorial.setNombre(leer.next());
                        System.out.println("Nombre cambiado con éxito");
                        break;
                    case 2:
                        editorial.setAlta(!editorial.getAlta());
                        System.out.println("Alta cambiada con éxito");
                        break;

                    case 3:
                        System.out.println("Hasta Luego...");
                        break;
                    default:
                        System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                        break;
                }
            } while (opcion != 3);
            dao.editar(editorial);
            System.out.println("Editorial actualizada con éxito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void eliminarEditorial() {
        try {
            List<Editorial> editoriales = dao.listarTodos();
            for (Editorial aux : editoriales) {
                System.out.println(aux.toString());
            }
            System.out.println("Ingrese el id correspondiente");
            Editorial editorial = dao.buscarPorId(leer.nextInt());
            if (editorial == null) {
                throw new Exception("Debe indicar una editorial");
            }
            System.out.println("La editorial indicada corresponde a: " + editorial.toString());
            System.out.println("¿Seguro que desea eliminarlo?(S/N)");
            if (leer.next().equalsIgnoreCase("S")) {
                dao.eliminar(editorial.getId());
                System.out.println("Editorial eliminada exitosamente");
            } else {
                System.out.println("Volviendo al menu....");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
