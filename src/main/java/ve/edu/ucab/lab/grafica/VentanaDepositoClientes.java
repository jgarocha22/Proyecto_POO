package ve.edu.ucab.lab.grafica;

/*  LIBRERIAS DE LAS CLASES   */
import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Cuenta;
import ve.edu.ucab.lab.Intermediario;

/*  LIBRERIAS DE LAS VENTANAS   */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class VentanaDepositoClientes {
  private JPanel panel1;
  private JLabel text;
  private JComboBox comboBox1;
  private JButton cancelarBoton;
  private JButton confirmarBoton;
  private JFrame frame;
  private JComboBox<String> opcionesComboBox = new JComboBox<>();
  private Cliente clienteActual;
  private double monto = 0;

  /**
   * <p>Constructor de la clase VentanaDepositoClientes</p>
   *
   * @param clienteActual cliente actual con el que se trabajara
   */

  public VentanaDepositoClientes(Cliente clienteActual) {
    frame = new JFrame("Ventana cuentas clientes");
    frame.setContentPane(panel1);
    frame.setSize(600, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    this.clienteActual = clienteActual;

    colocarOpciones(clienteActual);

    confirmarBoton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        ventanaDepositarMonto();
        frame.dispose();
      }
    });
    cancelarBoton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
  }


  /**
   * <p>Metodo para agregar las cuentas de un cliente al comboBox</p>
   *
   * @param clienteActual cliente actual con el que se trabajara
   */
  private void colocarOpciones(Cliente clienteActual) {
    LinkedList<Cuenta> listTemp = Intermediario.getCuentasCliente(clienteActual);
    for (Cuenta c : listTemp) {
      comboBox1.addItem(c.toString());
    }
  }

  private void ventanaDepositarMonto() {
    if (comboBox1.getSelectedItem() != null) {
      String cuentaSeleccionada =(String) comboBox1.getSelectedItem();
      Cuenta cuenta = Intermediario.getCuenta(clienteActual,cuentaSeleccionada);
      String dato = JOptionPane.showInputDialog(null, "Por favor, ingresa el monto a depositar:");
      // Verificar si el usuario ingresó algo
      if (dato != null && !dato.trim().isEmpty()) {
        // Verificar si el dato ingresado es un número válido
        if (dato.matches("\\d+(\\.\\d+)?")) {  // Expresión regular para verificar si es un número

          monto = Double.parseDouble(dato);
          if (monto > 1000000) {
            JOptionPane.showMessageDialog(null, "No se puede depositar más de 1.000.000 Bs.");
            return;
          }
          else {
            JOptionPane.showMessageDialog(null, "Has ingresado: " + monto);
            accionDeposito(cuenta, monto);
          }

        } else {
          // Si no es un número válido, mostrar un mensaje de error
          JOptionPane.showMessageDialog(null, "Por favor, ingresa un monto válido.");
        }
      } else {
        JOptionPane.showMessageDialog(null, "No se ingresó ningún dato.");
      }

    }
  }

  /**
   * <p>Metodo para depositar a la cuenta del cliente</p>
   *
   * @param monto double monto a depositar
   * @param cuenta Cuenta a depositar
   */
  private void accionDeposito(Cuenta cuenta, double monto) {
    cuenta.depositarSaldo(monto);
    clienteActual.resumenTransacciones(cuenta);
    guardarTransaccion(cuenta,monto);
  }

  private void guardarTransaccion(Cuenta cuenta, double monto) {
    String filename = "docs\\transacciones.txt" ;

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {

      String transactionData = clienteActual.getNombreUsuario()+"," +LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +","+cuenta.getTipo() + "," + monto + ",Deposito";
      bw.write(transactionData);
      bw.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error al guardar la transacción: " + e.getMessage());
    }
  }
}
