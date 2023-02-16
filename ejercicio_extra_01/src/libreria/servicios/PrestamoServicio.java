package libreria.servicios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Cliente;
import libreria.entidades.Libro;
import libreria.entidades.Prestamo;
import libreria.persitencia.PrestamoDao;

public class PrestamoServicio {

    PrestamoDao dao = new PrestamoDao();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    ClienteServicio clienteService = new ClienteServicio();
    LibroServicio libroService = new LibroServicio();

    public void menu() {
        int opcion;
        do {
            System.out.println("\tMenu ");
            System.out.println("1 - Crear prestamo");
            System.out.println("2 - Devolver prestamo");
            System.out.println("3 - Buscar prestamos por cliente");
            System.out.println("4 - Salir");
            System.out.println("Elija su opcion:");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    registrarPrestamo();
                    break;
                case 2:
                    devolverDeUnLibro();
                    break;
                case 3:
                    buscarPrestamosPorCliente();
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

    private void registrarPrestamo() {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Prestamo prestamo = new Prestamo();
            prestamo.setFechaPrestamo(new Date());
            System.out.println("Ingrese la cantidad de días");
            int cant = leer.nextInt();
            Date date_hasta = DateFor.parse((prestamo.getFechaPrestamo().getYear() + 1900)
                    + "-" + (prestamo.getFechaPrestamo().getMonth() + 1) + "-"
                    + (prestamo.getFechaPrestamo().getDate() + cant));
            prestamo.setFechaDevolucion(date_hasta);
            System.out.println("Ingrese el DNI del cliente");
            Cliente cliente = clienteService.validarClientePrestamo(leer.nextLong());
            if (cliente == null) {
                throw new Exception("El DNI no corresponde a ningún cliente");
            }
            prestamo.setCliente(cliente);
            libroService.buscarLibroPorTitulo();
            System.out.println("Ingrese el ISBN del libro a seleccionar");
            Libro libro = libroService.buscarLibroPorISBNPrestamo(leer.nextLong());
            if (libro == null) {
                throw new Exception("El ISBN no corresponde a ningún libro");
            }
            if (libro.getEjemplaresRestantes() == 0) {
                throw new Exception("Lo sentimos, no nos quedan de esos libros a prestar");
            }
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
            prestamo.setLibro(libro);
            dao.guardar(prestamo);
            System.out.println("Prestamo registrado con éxito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validarPrestamo(int id) throws Exception {
        return dao.validarPrestamo(id) == null;
    }

    private void devolverDeUnLibro() {
        try {
            buscarPrestamosPorCliente();
            System.out.println("Ingrese el id del prestamo a devolver");
            int idPrestamo = leer.nextInt();
            if (validarPrestamo(idPrestamo)) {
                throw new Exception("El id ingresado no corresponde a ningún prestamo");
            }
            Prestamo prestamo = dao.buscarPorId(idPrestamo);
            libroService.devolverLibro(prestamo.getLibro());
            dao.eliminar(idPrestamo);
            System.out.println("Libro devuelto... Prestamo eliminado con éxito");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarPrestamosPorCliente() {
        try {
            System.out.println("Ingrese el DNI del cliente");
            Long documento = leer.nextLong();
            if (!clienteService.validarCliente(documento)) {
                throw new Exception("El documento ingresado no pertenece a nigún cliente");
            }
            List<Prestamo> prestamos = dao.buscarPrestamosPorCliente(documento);
            for (Prestamo aux : prestamos) {
                System.out.println(aux.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
