package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Cuenta;
import ve.edu.ucab.lab.Intermediario;
import ve.edu.ucab.lab.LogUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class VentanaMenuCliente {
  private JPanel panel1;
  private JLabel nombreUsuario;
  private JLabel fotoPerfil;
  private JButton depositarButton;
  private JButton retirarButton;
  private JButton salirButton;
  private JButton cambiarContraseñaButton;
  private JComboBox comboBox1;
  private JButton VerTtransaccionesbutton;
  private final JFrame frame;
  private final Cliente clienteActual;

  public VentanaMenuCliente(Cliente clienteActual) {
    //Guardamos el cliente que inicio sesion
    this.clienteActual = clienteActual;
    actualizarOpciones();

    frame = new JFrame("Ventana menu cliente"); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    colocarNombreGrafico();

    //fotoPerfil.setIcon(new ImageIcon(getClass().getResource("/java/ve/edu/ucab/lab/Imagenes/fotoperfil.jpg")));

    salirButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
        VentanaInicioSesion ventana1 = new VentanaInicioSesion();
        LogUsuario logtemp = new LogUsuario(clienteActual.getNombreCliente(), LocalDateTime.now(), "CERRADA");
        LogUsuario.addLog("docs\\logs.txt",logtemp);
        System.out.println(logtemp.mensajeSalida());
      }
    });


    depositarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ventanaEmergenteDeposito();
        actualizarOpciones();
      }
    });


    retirarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ventanaEmergenteRetiro();
        actualizarOpciones();
      }
    });

    cambiarContraseñaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String dato = JOptionPane.showInputDialog(frame, "Por favor, ingresa su nueva contraseña:");
        if (dato != null && !dato.trim().isEmpty()) {
          String dato2 = JOptionPane.showInputDialog(frame, "Por favor, confirme su nueva contraseña:");

          if (dato.equals(dato2)) {
            JOptionPane.showMessageDialog(null, "Se ha cambiado correctamente la contraseña");
            cambiarContrasena(dato);
            cambiarContraArchivo(dato);
          } else {
            confirmarContrasena(dato);
          }
        }
      }
    });

    VerTtransaccionesbutton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        VentanaTransacciones ventana1 = new VentanaTransacciones(clienteActual);
      }
    });
  }

  private void actualizarOpciones() {
    comboBox1.removeAllItems();
    colocarOpciones(clienteActual);
  }

  private void colocarOpciones(Cliente clienteActual) {
    LinkedList<Cuenta> listTemp = Intermediario.getCuentasCliente(clienteActual);
    for (Cuenta c : listTemp) {
      comboBox1.addItem(c);
    }
  }

  private void ventanaEmergenteDeposito() {
    VentanaDepositoClientes v1 = new VentanaDepositoClientes(clienteActual);
    actualizarOpciones();
  }

  private void colocarNombreGrafico() {
    nombreUsuario.setText(nombreCuentaActual());
  }

  private String nombreCuentaActual() {
    return clienteActual.getNombreCliente();
  }

  private void ventanaEmergenteRetiro() {
    VentanaRetiroClientes v1 = new VentanaRetiroClientes(clienteActual);
    actualizarOpciones();
  }

  private void cambiarContrasena(String contrasena) {
    clienteActual.cambiarContraseña(contrasena);
  }

  private void cambiarContraArchivo(String nuevaContrana) {
    String archivo = "docs\\clientes.txt";
    String archivoTemporal = "docs\\clientes_temp.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(archivo));
         BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {
      String linea;

      while ((linea = br.readLine()) != null) {
        String[] datos = linea.split(",");
        String usuario = datos[0];

        if (usuario.equals(clienteActual.getNombreUsuario())) {
          datos[2] = nuevaContrana;
          linea = String.join(",", datos);
        }

        bw.write(linea);
        bw.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    // Reemplazar el archivo original con el archivo temporal
    try {
      Files.deleteIfExists(Paths.get(archivo));
      Files.move(Paths.get(archivoTemporal), Paths.get(archivo));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  private void confirmarContrasena(String dato) {
    int intento = 0;
    boolean contrasenaConfirmada = false;

    while (intento < 3 && !contrasenaConfirmada) {
      intento++;
      JOptionPane.showMessageDialog(null, "Por favor, coloque nuevamente su nueva contraseña, intento " + intento);
      String dato2 = JOptionPane.showInputDialog(null, "Por favor, confirme su nueva contraseña:");

      if (dato.equals(dato2)) {
        contrasenaConfirmada = true;
        JOptionPane.showMessageDialog(null, "Se ha cambiado correctamente la contraseña");
        cambiarContrasena(dato);
      }

    }

    if (!contrasenaConfirmada) {
      JOptionPane.showMessageDialog(null, "Se han agotado los intentos para confirmar la contraseña.");
    }

  }

}

