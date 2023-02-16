package libreria;

import java.util.Scanner;
import libreria.servicios.AutorServicio;
import libreria.servicios.EditorialServicio;
import libreria.servicios.LibroServicio;

public class Ejercicio_01 {

    public static void main(String[] args) throws Exception {
        AutorServicio svA = new AutorServicio();
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        AutorServicio autorService = new AutorServicio();
        EditorialServicio editorialService = new EditorialServicio();
        LibroServicio libroService = new LibroServicio();
        int opcion;
        do {
            System.out.println("\tMenu PRINCIPAL");
            System.out.println("1 - Manipular autores");
            System.out.println("2 - Manipular editoriales");
            System.out.println("3 - Manipular libros");
            System.out.println("4 - Salir");
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
                    System.out.println("Hasta Luego...");
                    break;
                default:
                    System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                    break;
            }
        } while (opcion != 5);
    }

}
