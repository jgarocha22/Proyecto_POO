package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Intermediario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAdmin {

  private JLabel Texto;
  private JButton eliminarClienteButton;
  private JButton agregarClienteButton;
  private JButton cambiarInformacionClienteButton;
  private JPanel panel1;
  private JPanel panel2;
  private JButton salirButton;
  private JButton agregarOEliminarCuentasButton;
  private JFrame frame;

  public VentanaAdmin() {

    frame = new JFrame("Ventana menu cliente"); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    agregarClienteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        VentanaAgregarCliente v1 = new VentanaAgregarCliente();

      }
    });
    salirButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
        VentanaInicioSesion v1 = new VentanaInicioSesion();
      }
    });
    eliminarClienteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        VentanaEliminarCliente v2 = new VentanaEliminarCliente();
      }
    });

    cambiarInformacionClienteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) { VentanaCambiarInformacionCliente v3 = new VentanaCambiarInformacionCliente();}
    });

    agregarOEliminarCuentasButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        VentanaModificarCuentasCliente v1 = new VentanaModificarCuentasCliente();
      }
    });

  }

  private void guardarCliente(Cliente cliente){
    Intermediario.getAdministrador().anadirCliente(cliente);
  }

  private void validacionesGuardarCliente(){

  }


}
