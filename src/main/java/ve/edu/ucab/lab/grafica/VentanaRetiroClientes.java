package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Cuenta;
import ve.edu.ucab.lab.Intermediario;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class VentanaRetiroClientes {
  private JPanel panel1;
  private JLabel text;
  private JComboBox comboBox1;
  private JButton cancelarBoton;
  private JButton confirmarBoton;
  private JFrame frame;
  private Cliente clienteActual;
  private double monto = 0;

  /**
   * <p>Constructor de la clase VentanaRetiroClientes</p>
   *
   * @param clienteActual cliente actual con el que se trabajara
   */


  public VentanaRetiroClientes(Cliente clienteActual) {
    frame = new JFrame("Ventana cuentas clientes");
    frame.setContentPane(panel1);
    frame.setSize(600, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    this.clienteActual = clienteActual;

    colocarOpciones(clienteActual);

    confirmarBoton.addActionListener(e -> {
      if(validarMontoCuenta()) {
        ventanaRetirarMonto();
        frame.dispose();
      }
    });
    cancelarBoton.addActionListener(e -> {
      frame.dispose();
    });
  }

  /**
   * <p>Metodo para colocar las opciones de las cuentas del cliente</p>
   *
   * @param clienteActual cliente actual con el que se trabajara
   */
  private void colocarOpciones(Cliente clienteActual) {
    LinkedList<Cuenta> temp = Intermediario.getCuentasCliente(clienteActual);
    for (Cuenta it : temp) {
      comboBox1.addItem(it);
    }
  }

  /**
   * <p>Metodo para realizar la accion de retirar monto</p>
   */
  private void ventanaRetirarMonto() {
    if (comboBox1.getSelectedItem() != null) {
      Cuenta cuentaSeleccionada = (Cuenta) comboBox1.getSelectedItem();
      String dato = JOptionPane.showInputDialog(null, "Por favor, ingresa el monto a retirar:");
      // Verificar si el usuario ingresó algo
      if (dato != null && !dato.trim().isEmpty()) {
        // Verificar si el dato ingresado es un número válido
        if (dato.matches("\\d+(\\.\\d+)?")) {  // Expresión regular para verificar si es un número
          monto = Double.parseDouble(dato);
          if (verificarRetirarSaldo(cuentaSeleccionada,monto) == true) {
            JOptionPane.showMessageDialog(null, "Has retirado: " + monto);
            clienteActual.retirarSaldo(monto,cuentaSeleccionada);
            guardarTransaccion(cuentaSeleccionada,monto);
          } else {
            JOptionPane.showMessageDialog(null, "Error, el monto a retirar es mayor a su saldo disponible");
          }

        } else {
          // Si no es un número válido, mostrar un mensaje de error
          JOptionPane.showMessageDialog(null, "Por favor, ingresa un monto válido.");
        }
      } else {
        JOptionPane.showMessageDialog(null, "No se ingreso ningun dato.");
      }
    } else {
      JOptionPane.showMessageDialog(null, "No se selecciono ninguna cuenta");
    }
  }


  /**
   * <p>Metodo para verificar si se puede retirar saldo con el monto deseado</p>
   *
   * @param monto double monto a retirar
   * @param cuenta Cuenta a verificar el saldo
   * @return retorna true si el monto es menor al saldo y false en caso contrario
   */
  private boolean verificarRetirarSaldo(Cuenta cuenta, double monto) {
    boolean tof = false;
    //Verificacion
    if (clienteActual.saldoDisponible(cuenta) >= monto) {
      tof = true;
    }
    return tof;
  }

  private boolean validarMontoCuenta() {
    boolean tof = false;
    if (comboBox1.getSelectedItem() != null) {
      Cuenta cuentaSeleccionada = (Cuenta) comboBox1.getSelectedItem();
      if (clienteActual.saldoDisponible(cuentaSeleccionada) > 0) {
        tof = true;
      } else {
        JOptionPane.showMessageDialog(null, "Error, no tiene saldo disponible en la cuenta seleccionada");
      }
    } else {
      JOptionPane.showMessageDialog(null, "No se seleccionó ninguna cuenta");
    }
    return tof;
  }

  private void guardarTransaccion(Cuenta cuenta, double monto) {
    String filename = "docs\\transacciones.txt" ;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {

      String transactionData = clienteActual.getNombreUsuario()+"," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +","+cuenta.getTipo() + "," + monto + ",Retiro";
      bw.write(transactionData);
      bw.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al guardar la transacción: " + e.getMessage());
    }
  }
}
