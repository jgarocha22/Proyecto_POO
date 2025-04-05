package ve.edu.ucab.lab.grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuPrincipal {
  private JPanel panel1;
  private JButton iniciarButton;
  private JFrame frame;// Agregamos un JFrame aquí

  public MenuPrincipal() {
    frame = new JFrame("Menú Principal"); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);



    iniciarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Cerrar la ventana principal
        frame.dispose(); // Cerrar la ventana principal

        // Abrir la ventana secundaria
        VentanaInicioSesion ventana1 = new VentanaInicioSesion();

      }
    });

    frame.setVisible(true); // Hacer visible el JFrame
  }


  public JPanel getPanel1() {
    return panel1;
  }




}
