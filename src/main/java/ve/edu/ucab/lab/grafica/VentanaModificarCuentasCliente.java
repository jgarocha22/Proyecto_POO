package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Cuenta;
import ve.edu.ucab.lab.Intermediario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class VentanaModificarCuentasCliente {
  private JPanel panel1;
  private JComboBox comboBox1;
  private JCheckBox ahorroCheckBox;
  private JCheckBox corrienteCheckBox;
  private JCheckBox dolaresCheckBox;
  private JButton confirmarButton;
  private JButton cancelarButton;
  private JFrame frame;


  public VentanaModificarCuentasCliente() {
    frame = new JFrame("Ventana modificar Cuentas cliente");
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    //actualizarTiposCuentas(Intermediario.getClientesBanco());
    colocarOpciones();
    llenarCheckboxs();

    comboBox1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        llenarCheckboxs();

        Cliente client = (Cliente) comboBox1.getSelectedItem();
        System.out.println("La persona " + client.getNombreCliente());
        for (Cuenta c : client.getCuentas()) {
          System.out.println(c.getTipo());
        }
      }
    });

    confirmarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modificarCuenta();
        modificarArchivo();

        frame.dispose();
      }
    });
    cancelarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
  }

  /**
   * <p>Metodo que coloca los clientes en las opciones del combobox </p>
   */
  private void colocarOpciones() {
    LinkedList<Cliente> listTemp = Intermediario.getClientesBanco();
    for (Cliente it : listTemp) {
      comboBox1.addItem(it);
    }

  }

  /**
   * <p>Metodo que llena la ckeckboxs si la cuenta esta </p>
   */
  private void llenarCheckboxs() {
    reiniciarCheckboxs();
    Cliente client = (Cliente) comboBox1.getSelectedItem();

    for (Cuenta cuenta : client.getCuentas()) {
      switch (cuenta.getTipo()) {
        case "Corriente":
          corrienteCheckBox.setSelected(true);
          break;
        case "Ahorro":
          ahorroCheckBox.setSelected(true);
          break;
        case "Dolares":
          dolaresCheckBox.setSelected(true);
          break;
      }
    }
  }

  /**
   * <p>Metodo que reinicia los checkbox </p>
   */
  private void reiniciarCheckboxs() {
    corrienteCheckBox.setSelected(false);
    ahorroCheckBox.setSelected(false);
    dolaresCheckBox.setSelected(false);
  }

  /**
   * <p>Metodo que agrega o elimina cuenta en funcion de lo elegido por el combobox </p>
   */


  private void modificarCuenta() {
    Cliente client = (Cliente) comboBox1.getSelectedItem();
    // Crear un mapa que asocie cada JCheckBox con su tipo de cuenta correspondiente
    Map<JCheckBox, String> cuentasMap = new HashMap<>();
    cuentasMap.put(corrienteCheckBox, "Corriente");
    cuentasMap.put(ahorroCheckBox, "Ahorro");
    cuentasMap.put(dolaresCheckBox, "Dolares");

    // Contar el número de cuentas seleccionadas
    int cuentasSeleccionadas = 0;
    for (JCheckBox checkBox : cuentasMap.keySet()) {
      if (checkBox.isSelected()) {
        cuentasSeleccionadas++;
      }
    }

    // Verificar si hay al menos una cuenta seleccionada
    if (cuentasSeleccionadas == 0) {
      JOptionPane.showMessageDialog(frame, "Debe seleccionar al menos una cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }else {
      // Iterar sobre el mapa para modificar las cuentas
      for (Map.Entry<JCheckBox, String> entry : cuentasMap.entrySet()) {
        JCheckBox checkBox = entry.getKey();
        String tipoCuenta = entry.getValue();

        if (checkBox.isSelected()) {
          client.setCuentas(tipoCuenta);
        } else {
          client.eliminarCuenta(tipoCuenta);
        }
      }
      JOptionPane.showMessageDialog(frame, " Cuenta de Usuario actualizado satisfactoriamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public void modificarArchivo() {
    String archivo = "docs\\cuentas.txt";
    LinkedList<Cliente> clientes = Intermediario.getClientesBanco();
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
      for (Cliente cliente : clientes) {
        bw.write(cliente.getNombreUsuario() );
        for (Cuenta cuenta : cliente.getCuentas()) {
          bw.write( ","+ cuenta.getTipo());
        }
        bw.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}