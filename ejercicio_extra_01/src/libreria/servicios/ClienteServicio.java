package libreria.servicios;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Cliente;
import libreria.persitencia.ClienteDAO;
import libreria.persitencia.PrestamoDao;

public class ClienteServicio {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    ClienteDAO dao = new ClienteDAO();
    PrestamoDao daoP = new PrestamoDao();

    public void menu() {
        int opcion;
        do {
            System.out.println("\tMenu de Cliente");
            System.out.println("1 - Crear cliente");
            System.out.println("2 - Editar cliente");
            System.out.println("3 - Eliminar cliente");
            System.out.println("4 - Salir");
            System.out.println("Elija su opcion:");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    crearCliente();
                    break;
                case 2:
                    editarCliente();
                    break;
                case 3:
                    eliminarCliente();
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

    public boolean validarCliente(Long documento) throws Exception {
        Cliente cliente = dao.buscarPorDocumento(documento);
        return cliente != null;
    }
    
    public Cliente validarClientePrestamo(Long documento) throws Exception {
        Cliente cliente = dao.buscarPorDocumento(documento);
        return cliente;
    }

    public void crearCliente() {
        try {
            Cliente cliente = new Cliente();
            System.out.println("Ingrese el documento");
            cliente.setDocumento(leer.nextLong());

            if (String.valueOf(cliente.getDocumento()).length() != 8) {
                throw new Exception("Ingresó un documento inválido");
            }
            if (validarCliente(cliente.getDocumento())) {
                throw new Exception("Ya existe un cliente con ese documento");
            }

            System.out.println("Ingrese el nombre");
            cliente.setNombre(leer.next());
            if (cliente.getNombre().trim().isEmpty()) {
                throw new Exception("Debe ingresar un nombre válido");
            }

            System.out.println("Ingrese el apellido");
            cliente.setApellido(leer.next());
            if (cliente.getApellido().trim().isEmpty()) {
                throw new Exception("Debe ingresar un apellido válido");
            }

            System.out.println("Ingrese el teléfono");
            String numeroTelefono = leer.next().replaceAll(" ", "");
            if (esNumero(numeroTelefono)) {
                cliente.setTelefono(numeroTelefono);
            } else {
                throw new Exception("Solo debe ingresar Numeros");
            }
            System.out.println("Cliente registrado con éxito");
            dao.guardar(cliente);
        } catch (InputMismatchException e) {
            System.out.println("Ingresó un documento inválido");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean esNumero(String numeroTelefono) {
        try {
            Long numeroTelefonico = Long.parseLong(numeroTelefono);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public void editarCliente() {
        try {
            List<Cliente> clientes = buscarClientePorNombre();
            for (Cliente aux : clientes) {
                System.out.println(aux.toString());
            }
            System.out.println("Ingrese el Id del cliente para editar");
            Cliente cliente = dao.buscarPorId(leer.nextInt());
            if (cliente == null) {
                throw new Exception("El id del cliente no Existe");
            }
            System.out.println("Se va a modificar a: " + cliente.toString());
            menuEditarCliente(cliente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuEditarCliente(Cliente cliente) {
        try {
            int opcion;
            do {
                System.out.println("\t.:Menu de Edicion:.");
                System.out.println("1 - Editar Nombre");
                System.out.println("2 - Editar Apellido");
                System.out.println("3 - Editar Numero de Telefono");
                System.out.println("4 - Editar Documento");
                System.out.println("5 - Salir");
                System.out.println("Elija su opcion:");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese el Nuevo nombre");
                        cliente.setNombre(leer.next());
                        if (cliente.getNombre().trim().isEmpty()) {
                            throw new Exception("Debe ingresar un nombre válido");
                        }
                        dao.editar(cliente);
                        System.out.println("Se Modifico el nombre Correctamente");
                        break;
                    case 2:
                        System.out.println("Ingrese el Nuevo Apellido");
                        cliente.setApellido(leer.next());
                        if (cliente.getApellido().trim().isEmpty()) {
                            throw new Exception("Debe ingresar un Apellido válido");
                        }
                        dao.editar(cliente);
                        System.out.println("Se Modifico el Apellido Correctamente");
                        break;
                    case 3:
                        System.out.println("Ingrese el nuevo numero de Telefono");
                        String numeroTelefono = leer.next().replaceAll(" ", "");
                        if (esNumero(numeroTelefono)) {
                            cliente.setTelefono(numeroTelefono);
                            dao.editar(cliente);
                        } else {
                            throw new Exception("Solo debe ingresar Numeros");
                        }
                        break;
                    case 4:
                        System.out.println("Ingrese el Nuevo Documento");
                        cliente.setDocumento(leer.nextLong());
                        if (String.valueOf(cliente.getDocumento()).length() != 8) {
                            throw new Exception("Ingresó un documento inválido");
                        }
                        if (validarCliente(cliente.getDocumento())) {
                            throw new Exception("Ya existe un cliente con ese documento");
                        }
                        dao.editar(cliente);
                        break;
                    default:
                        System.out.println("Esa no es una opcion valida, vuelva a intentar...");
                        break;
                }
            } while (opcion != 5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarCliente() {
        try {
            List<Cliente> clientes = buscarClientePorNombre();
            for (Cliente aux : clientes) {
                System.out.println(aux.toString());
            }
            System.out.println("Ingrese el Id del cliente para Eliminar");
            Cliente cliente = dao.buscarPorId(leer.nextInt());
            if (cliente == null) {
                throw new Exception("El id del cliente no Existe");
            }

            if (!daoP.buscarPorClienteID(cliente.getId())) {
                throw new Exception("No se puede Eliminar. El cliente tiene libros prestados");
            }
            System.out.println("Estas Seguro que deseas Eliminar a: " + cliente.toString() + " ? (S/N)");
            if (leer.next().equalsIgnoreCase("S")) {
                dao.eliminar(cliente.getId());
                System.out.println("Se Elimino Correctamente al Cliente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Cliente> buscarClientePorNombre() {
        List<Cliente> clientes = null;
        try {
            System.out.println("Ingrese el nombre a buscar");
            clientes = dao.buscarPorNombre(leer.next());
            if (clientes.isEmpty()) {
                throw new Exception("No se encontraron resultados");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return clientes;
    }

}
