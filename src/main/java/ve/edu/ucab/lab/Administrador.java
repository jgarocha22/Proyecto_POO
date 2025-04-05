package ve.edu.ucab.lab;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;



public class Administrador extends Usuario {

  private LinkedList<Cliente> clientesBanco;


  /**
   * <p>Constructor de administrador</p>
   */
  public Administrador() {
    super("Tu", "admin", "1234", "Generica");
  }

  /**
   * <p>Metodo para settear la lista de clientes del banco a administrar</p>
   *
   * @param clientesBanco lista clientes a administrar
   */
  public void setClientesAdministrado(LinkedList<Cliente> clientesBanco) {
    this.clientesBanco = clientesBanco;
  }

  /**
   * <p>Metodo para agregar cliente a el banco a administrar</p>
   *
   * @param cliente nuevo cliente
   */
  public void anadirCliente(Cliente cliente) {
    clientesBanco.add(cliente);
  }

  /**
   * <p>Metodo para eliminar cliente del banco por nombre de usuario </p>
   *
   * @param nombreClienteUsuario a eliminar
   */
  public void eliminarCliente(String nombreClienteUsuario) {
    clientesBanco.remove(buscarCliente(nombreClienteUsuario));
  }

  /**
   * <p>Metodo para buscar cliente por nombre de usuario </p>
   *
   * @param nombreClienteUsuario a buscar
   * @return cliente buscado
   */
  private Cliente buscarCliente(String nombreClienteUsuario) {
    Cliente clienteBuscado = null;
    for (Cliente ic : clientesBanco) {
      if (ic.getNombreUsuario() == nombreClienteUsuario) {
        clienteBuscado = ic;
        break;
      }
    }

    return clienteBuscado;
  }

  /**
   * <p>Metodo para boleano para saber si un cliente esta en el banco por su nombre de usuario </p>
   *
   * @param nombreClienteUsuario a buscar
   * @return true si esta o false en caso contrario
   */
  public boolean estaCliente(String nombreClienteUsuario) {
    boolean bool = false;
    for (Cliente ic : clientesBanco) {
      if (ic.getNombreUsuario().matches(nombreClienteUsuario)) {
        bool = true;
        break;
      }
    }
    return bool;
  }

  //metodo para actualizar los datos del cliente pero hay que ver como es
  public void actualizarCliente(String nuevoNombre, String Usuario,
                                String nuevaContraseña, String nuevaDireccion) {

    for (Cliente cliente : clientesBanco) {
      if (cliente.getNombreUsuario().equals(Usuario)) {
        cliente.setNombreCliente(nuevoNombre);
        cliente.setContraseña(nuevaContraseña);
        cliente.setDireccion(nuevaDireccion);
        break;
      }
    }


  }

  //obtiene los clientes del archivo y los añade a la lista de clientes del banco
  public void obtenerClientesdeArchivo() {
    try (BufferedReader br = new BufferedReader(new FileReader("docs\\clientes.txt"))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        String[] datos = linea.split(",");
        // Validación de la longitud del arreglo
        if (datos.length != 4) {
          System.err.println("Línea con formato incorrecto: " + linea);
          continue;
        }

        String usuario = datos[0];
        String nombre = datos[1];
        String contrasena = datos[2];
        String direccion = datos[3];

        Cliente cliente = new Cliente(nombre, usuario, contrasena, direccion);

        if (clientesBanco.isEmpty()) {
          anadirCliente(cliente);
        } else {

          anadirCliente(cliente);

        }

      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public void modificarClienteArchivo(String nuevoNombre, String Usuario,
                                      String nuevaContraseña, String nuevaDireccion) {
    LinkedList<Cliente> clientes = new LinkedList<>();
    Cliente clienteActualizar = new Cliente(nuevoNombre, Usuario, nuevaContraseña, nuevaDireccion);

    // Leer todos los clientes del archivo
    try (BufferedReader br = new BufferedReader(new FileReader("docs\\clientes.txt"))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        Cliente cliente = parsearCliente(linea);
        clientes.add(cliente);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Modificar el cliente específico
    for (int i = 0; i < clientes.size(); i++) {
      if (clientes.get(i).getNombreUsuario().equals(clienteActualizar.getNombreUsuario())) {
        clientes.set(i, clienteActualizar);
        break;
      }
    }

    // Escribir todos los clientes de vuelta al archivo
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("docs\\clientes.txt"))) {
      for (Cliente cliente : clientes) {
        bw.write(cliente.getNombreUsuario() + "," + cliente.getNombreCliente() + "," +
          cliente.getContraseña() + "," + cliente.getDireccion());
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //cambia las lineas de texto de un archivo a un objeto cliente
  public static Cliente parsearCliente(String linea) {
    String[] datos = linea.split(",");
    if (datos.length!= 4) {
      throw new IllegalArgumentException("la linea no tiene el formato correcto");
    }
    String usuario = datos[0];
    String nombre = datos[1];
    String contraseña = datos[2];
    String direccion = datos[3];
    return new Cliente(nombre, usuario, contraseña, direccion);
  }

  // actualiza las cuentas de la lista de clientes del banco con respecto
  // a lo que esta en el archivo
  public void actualizarTiposCuentas() {
    for (Cliente cliente : clientesBanco) {
      cliente.obtenerCuentasDeArchivo();
    }
  }

  public void cargarTransaccionesDesdeArchivo() {
    String filename = "docs\\transacciones.txt"; // Ajusta el nombre del archivo según sea necesario

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");

        String nombreUsuario = data[0];
        LocalDateTime fechaHora = LocalDateTime.parse(data[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String tipoCuenta = data[2];
        double monto = Double.parseDouble(data[3]);
        String tipoTransaccion = data[4];
        // Busca al cliente por nombre de usuario
        for (Cliente cliente : clientesBanco) {
          if (cliente.getNombreUsuario().equals(nombreUsuario)) {
          }

          if (cliente != null) {
            // Busca la cuenta por tipo
            Cuenta cuenta = cliente.buscarCuenta(tipoCuenta);

            if (cuenta != null) {
              // Realiza la transacción
              if (tipoTransaccion.equalsIgnoreCase("Deposito")) {
                cliente.depositar(monto, cuenta);
              } else if (tipoTransaccion.equalsIgnoreCase("Retiro")) {
                cliente.retirarSaldo(monto, cuenta);
              } else {
                System.out.println("Tipo de transacción no válido: " + tipoTransaccion);
              }
            } else {
              System.out.println("Cuenta no encontrada " );
            }


          } else {
            System.out.println("Cliente no encontrado para la transacción: " + nombreUsuario);
          }
        }
      }
    }
    catch (IOException | DateTimeParseException e) {
      e.printStackTrace();
      System.err.println("Error al leer o parsear el archivo de transacciones");
    }
  }

}




