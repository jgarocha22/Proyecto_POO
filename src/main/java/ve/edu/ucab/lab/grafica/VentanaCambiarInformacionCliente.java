package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Intermediario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class VentanaCambiarInformacionCliente {
  private JPanel panel1;
  private JButton guardarCambiosButton;
  private JButton volverButton;
  private JTextField nombreCliente;
  private JTextField usuarioCliente;
  private JTextField contraseñaCliente;
  private JTextField direccionCliente;
  private JComboBox<Cliente> comboBox1;
  private JFrame frame;

  public VentanaCambiarInformacionCliente() {
    frame = new JFrame("Ventana cambiar información cliente");
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    colocarOpciones();
    llenarCamposConDatosCliente();

    volverButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });


    guardarCambiosButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        guardarCambios();
      }
    });

    // Actualiza las celdas si se cambia de cliente
    comboBox1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        llenarCamposConDatosCliente();
      }
    });
  }

  /**
   * Método que coloca los clientes en las opciones del combobox
   */
  private void colocarOpciones() {

    LinkedList<Cliente> listTemp = Intermediario.getClientesBanco();
    for (Cliente it : listTemp) {
      comboBox1.addItem(it);
    }

  }

  private void llenarCamposConDatosCliente() {
    if (comboBox1.getSelectedItem() != null) {
      Cliente clienteElegido = (Cliente) comboBox1.getSelectedItem();
      nombreCliente.setText(clienteElegido.getNombreCliente());
      usuarioCliente.setText(clienteElegido.getNombreUsuario());
      contraseñaCliente.setText(clienteElegido.getContraseña());
      direccionCliente.setText(clienteElegido.getDireccion());
    }
  }

  private void guardarCambios() {
    if (validarCampos()) {
      String nuevoNombre = nombreCliente.getText();
      String usuario = usuarioCliente.getText();
      String nuevaContraseña = contraseñaCliente.getText();
      String nuevaDireccion = direccionCliente.getText();

      try {
        Intermediario.getAdministrador().actualizarCliente(nuevoNombre, usuario, nuevaContraseña, nuevaDireccion);
        Intermediario.getAdministrador().modificarClienteArchivo(nuevoNombre, usuario, nuevaContraseña, nuevaDireccion);
        JOptionPane.showMessageDialog(frame, "Usuario actualizado satisfactoriamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(frame, "Error al actualizar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private boolean validarCampos() {
    String nuevoNombre = nombreCliente.getText().trim();
    String usuario = usuarioCliente.getText().trim();
    String nuevaContraseña = contraseñaCliente.getText().trim();
    String nuevaDireccion = direccionCliente.getText().trim();

    if (nuevoNombre.isEmpty() || usuario.isEmpty() || nuevaContraseña.isEmpty() || nuevaDireccion.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
      return false; // Si algún campo está vacío retorna false
    } else {
      return true; // Si todos los campos están llenos retorna true
    }
  }
}