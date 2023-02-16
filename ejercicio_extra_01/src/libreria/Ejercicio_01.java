package libreria;

import java.util.Scanner;
import libreria.servicios.AutorServicio;
import libreria.servicios.ClienteServicio;
import libreria.servicios.EditorialServicio;
import libreria.servicios.LibroServicio;
import libreria.servicios.PrestamoServicio;

public class Ejercicio_01 {

    public static void main(String[] args) throws Exception {
       
        AutorServicio svA = new AutorServicio();
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        AutorServicio autorService = new AutorServicio();
        EditorialServicio editorialService = new EditorialServicio();
        LibroServicio libroService = new LibroServicio();
        ClienteServicio clienteService = new ClienteServicio();
        PrestamoServicio prestamoService = new PrestamoServicio();
        int opcion;
        do {
            System.out.println("\tMenu PRINCIPAL");
            System.out.println("1 - autores");
            System.out.println("2 - editorales");
            System.out.println("3 - libros");
            System.out.println("4 - clientes");
            System.out.println("5 - prestamos");
            System.out.println("6 - Salir");
            System.out.println("Elija su opcion:");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    autorService.menu();
                    break;
                case 2:
                    editorialService.menu();
                    break;
                case 3:
                    libroService.menu();
                    break;
                case 4:
                    clienteService.menu();
                    break;
                case 5:
                    prestamoService.menu();
                    break;
                case 6:
                    System.out.println("Hasta Luego...");
                    break;
                default:
                    System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                    break;
            }
        } while (opcion != 6);
    }

}
