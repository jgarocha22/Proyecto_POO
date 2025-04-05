package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class VentanaInicioSesion {
  private JPanel panel1;
  private JTextField usuarioField;
  private JPasswordField passwordField;
  private JButton regresarButton;
  private JButton confirmarButton;
  private JPanel panelUsu;
  private JPanel panelContra;
  private final JFrame frame; // Agregamos un JFrame aquí


  public VentanaInicioSesion(){


    frame = new JFrame("Ventana Inicio sesion"); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);

    regresarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
        MenuPrincipal ventana1= new MenuPrincipal();
      }
    });

    confirmarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (verificadorUsuarioYContraseña()){
          VentanaMenuCliente ventanaMenucliente=new VentanaMenuCliente(sesionCliente());
          frame.dispose();
          LogUsuario logtemp = new LogUsuario(sesionCliente().getNombreCliente(), LocalDateTime.now(), "ACTIVA");
          LogUsuario.addLog("docs\\logs.txt",logtemp);
          System.out.println("\n"+logtemp.mensajeEntrada());
        }
        else if (verficadorAdmin()) {
          frame.dispose();
          VentanaAdmin ventanaAdmin=new VentanaAdmin();
        }
        else{
          JOptionPane.showMessageDialog(frame, "¡Error usuario no encontrado! , intente nuevamente", "Información", JOptionPane.INFORMATION_MESSAGE);
          System.out.println(verificadorUsuarioYContraseña());
        }
      }
    });

    frame.setVisible(true); // Hacer visible el JFrame
  }
  public JPanel getPanel1() {
    return panel1;
  }


  private boolean verificadorUsuarioYContraseña(){
    boolean bool=false;
    LinkedList<Cliente> clientes=Intermediario.getClientesBanco();
    for(Cliente cliente:clientes){
      if (cliente.getNombreUsuario().equals( usuarioField.getText().strip()) && cliente.getContraseña().equals(passwordField.getText())) {

        bool=true;

      }

    }
    return bool;
  }

  private Cliente sesionCliente(){
    Cliente temp=null;
    LinkedList<Cliente> clientes=Intermediario.getClientesBanco();
    for(Cliente cliente:clientes){
      if (cliente.getNombreUsuario().equals( usuarioField.getText().strip()) && cliente.getContraseña().equals(passwordField.getText())) {
        temp=cliente;
      }
    }
    return temp;
  }

  /**
   * <p>Verficador de admin </p>
   * @return retorna true si los datos colocados concuerdan con el usuario y contraseña del administrador y false en caso contrario
   */
  private boolean verficadorAdmin() {
    boolean tof = false;
    Administrador admin = Intermediario.getAdministrador();
    if (usuarioField.getText().strip().equals(admin.getNombreUsuario()) && passwordField.getText().equals(admin.getContraseña())) {
      tof=true;
    }
    return tof;
  }
}
