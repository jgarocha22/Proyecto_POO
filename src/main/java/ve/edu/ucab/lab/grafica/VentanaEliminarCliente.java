package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Intermediario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class VentanaEliminarCliente {
  private JPanel panel1;
  private JComboBox comboBox1;
  private JButton confirmarButton;
  private JButton cancelarButton;
  private JLabel texto;
  private JFrame frame;
  private JComboBox<Cliente> opcionesComboBox = new JComboBox<>();

  public VentanaEliminarCliente(){
    frame = new JFrame("Ventana menu cliente"); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    colocarOpciones();


    confirmarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        accionEliminar();

      }
    });
    cancelarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();

      }
    });
  }

  private void colocarOpciones(){

    LinkedList<Cliente> listTemp = Intermediario.getClientesBanco();
    for (Cliente it: listTemp){
      comboBox1.addItem(it);
    }

  }

  private void accionEliminar(){

    if (comboBox1.getSelectedItem() != null){

      String seleccion = comboBox1.getSelectedItem().toString();
      Cliente clienteElegido= (Cliente) comboBox1.getSelectedItem();



      Intermediario.getAdministrador().eliminarCliente(clienteElegido.getNombreUsuario());
      comboBox1.removeItem(clienteElegido);

      eliminarClienteArchivo(clienteElegido.getNombreUsuario(),"docs\\clientes.txt");
      eliminarClienteArchivo(clienteElegido.getNombreUsuario(),"docs\\cuentas.txt");


      JOptionPane.showMessageDialog(frame, "Se ha elimina correctamente a "+ seleccion);
    }
    else {
      JOptionPane.showMessageDialog(frame, "Seleccione una opcion");
    }
  }


  public void eliminarClienteArchivo (String nombreUsuario,String nombreArchivo){
      File archivo = new File(nombreArchivo);
      LinkedList<String> lineas = new LinkedList<>();

      // Leer el archivo y cargar los datos en una lista
      try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
          lineas.add(linea);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      // Buscar y eliminar el cliente
      boolean clienteEliminado = false;
      for (int i = 0; i < lineas.size(); i++) {
        String[] datosCliente = lineas.get(i).split(",");
        if (datosCliente[0].equals(nombreUsuario)) {
          lineas.remove(i);
          clienteEliminado = true;
          break;
        }
      }

      // Paso 3: Escribir la lista actualizada de vuelta al archivo
      if (clienteEliminado) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
          for (String linea : lineas) {
            bw.write(linea);
            bw.newLine();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
  }
}
    


