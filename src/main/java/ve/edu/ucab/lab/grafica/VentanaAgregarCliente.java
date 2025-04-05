package ve.edu.ucab.lab.grafica;

import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Cuenta;
import ve.edu.ucab.lab.Intermediario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class VentanaAgregarCliente {
  private JTextField nombreCliente;
  private JTextField usuarioCliente;
  private JTextField contraCliente;
  private JTextField confirmarContra;
  private JTextField direccion;
  private JPanel panel1;
  private JButton cancelarButton;
  private JButton confirmarButton;
  private JCheckBox corrienteCheckBox;
  private JCheckBox ahorroCheckBox;
  private JCheckBox dolaresCheckBox;
  private JFrame frame;

  //Variables auxiliares para que el codigo se vea bonito ;D
  private String nombreClienteTexto;
  private String usuarioClienteTexto;
  private String contraClienteTexto;
  private String confirmarContraTexto;
  private String direccionTexto;
  private Set<String> mensajesInvalidos = new HashSet<>();



  public VentanaAgregarCliente(){
    frame = new JFrame("Ventana agregar cliente"); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);


    confirmarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        acctualizarVariablesTexto();

        if (validacionGeneral()) {
          if (!usuarioRepetido()) {

            accionAgregarCliente();
            JOptionPane.showMessageDialog(null, "Se ha registrado correctamente el cliente");
            frame.dispose();

          }
          else {
            creadorMensajeErro(usuarioCliente,"Este usuario ya ha sido elegido porfavor seleccione otro");
          }

        }

      }
    });

    // Agregar MouseListener a cada JTextField
    agregarMouseListener(nombreCliente);
    agregarMouseListener(usuarioCliente);
    agregarMouseListener(contraCliente);
    agregarMouseListener(confirmarContra);
    agregarMouseListener(direccion);

    cancelarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });


  }

  /**<p>Agrega un MouseListener a un JTextField para manejar el evento de clic del mouse.
   * Este MouseListener se encarga de limpiar el mensaje de error en el JTextField
   * cuando el usuario hace clic en él y el texto actual es "No puedes dejar este campo vacío".</p>
   *
   * @param textField El JTextField al que se le agregará el MouseListener.
   */
  private void agregarMouseListener(JTextField textField) {
    textField.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // Limpiar el mensaje de error si es que está presente
        if (mensajesInvalidos.contains(textField.getText())) {
          textField.setText(""); // Limpiar el campo de texto
          textField.setForeground(Color.BLACK); // Restablecer el color del texto
        }
      }
    });
  }

  /**<p>Metodo que verificar si el string recibido del texfield esta vacio</p>
   * @param dato string del texfield a validar
   * @return true si esta vacio y false en caso contrario
   */
  private boolean datoVacio(String dato){
    boolean tof = false;

    if (dato == null || dato.trim().isEmpty()) {
      tof=true;
    }

    return tof;
  }

  /**<p>Metodo que verificar si algun texfield esta vacio  y en caso de estarlo le coloca mensaje de error </p>
   * @return true si alguno esta vacio y false en caso contrario
   */
  private boolean algunDatoVacio(){
    boolean tof = false;

    LinkedList<JTextField> list= new LinkedList<>();
    list.add(nombreCliente);
    list.add(usuarioCliente);
    list.add(contraCliente);
    list.add(confirmarContra);
    list.add(direccion);

    for (JTextField datoTem: list){
      if (datoVacio(datoTem.getText())== true || datoTem.getText().matches("No puedes dejar este campo vacío")){
        tof=true;
        creadorMensajeErro(datoTem,"No puedes dejar este campo vacío");
      }
    }
    return tof;
  }

  /**<p>Metodo que verificar si el nombre del cliente no tiene numero , si la contraseña no tiene letras
   * y que la contraseña sea igual a confirmar contraseña </p>
   * @return true si las opciones no tienen lo anterior mencionado y false en caso de tener alguna
   */
  private boolean validacionesPuntuales(){
    boolean tof=true;

    if (nombreClienteTexto.matches(".*\\d.*")) {
      creadorMensajeErro(nombreCliente,"No puedes colocar numeros en este campo");
      tof=false;
    }
    //Si se deja asi tengo que anidar estos if
    if (contraClienteTexto.matches(".*[a-zA-Z].*") && !datoVacio(contraClienteTexto))  {
      creadorMensajeErro(contraCliente,"No puedes colocar letras en la contraseña");
      tof=false;
    }
    if (!contraClienteTexto.matches(confirmarContraTexto)) {
      creadorMensajeErro(confirmarContra,"La contraseña colocada no es igual");
      tof=false;
    }
    //
    return tof;
  }

  /**<p>Metodo que coloca el mensaje de error proporcionado en el texfield
   * y guarda el mensaje de error en la lista de mensajes de error</p>
   */
  private void creadorMensajeErro(JTextField textField,String mensaje){
    textField.setText(mensaje);
    textField.setForeground(Color.red);

    //Agrega el mensaje de error a la lista de errores
    mensajesInvalidos.add(mensaje);
  }

  /**<p>Metodo que guarda en string los textos que se colocaron en el texfield </p>
   */
  private void acctualizarVariablesTexto(){
    nombreClienteTexto=nombreCliente.getText().strip();
    usuarioClienteTexto=usuarioCliente.getText().strip();
    contraClienteTexto=contraCliente.getText().strip();
    confirmarContraTexto=confirmarContra.getText().strip();
    direccionTexto=direccion.getText().strip();
  }

  /**<p>Metodo que verifica si el usuario colocado esta repetido </p>
   * @return true en caso de estarlo y falso en caso contrario
   */
  private boolean usuarioRepetido(){
    boolean tof=false;

    //Hacer mas bonito este if
    if (Intermediario.getAdministrador().estaCliente(usuarioClienteTexto) || mensajesInvalidos.contains(usuarioClienteTexto) ){
      tof=true;
    }

    return tof;
  }

  private boolean validacionGeneral(){
    boolean tof=true;
    if(algunDatoVacio()){
      tof=false;
    }
    if (validacionesPuntuales()==false){
      tof=false;
    }
    return tof;
  }

  private void accionAgregarCliente(){
    Cliente client = new Cliente(nombreClienteTexto,usuarioClienteTexto,contraClienteTexto,direccionTexto);
    
    if(corrienteCheckBox.isSelected()){
      client.setCuentas("Corriente");
    }
    if (ahorroCheckBox.isSelected()){
      client.setCuentas("Ahorro");
    }
    if (dolaresCheckBox.isSelected()){
      client.setCuentas("Dolares");
    }
    Intermediario.getClientesBanco().add(client);
    guardarClienteArchivo(client);
    guardarCuentasArchivo(client);

    
  }


  public void guardarClienteArchivo( Cliente cliente) {
    Cliente clienteAGuardar = new Cliente(nombreClienteTexto,usuarioClienteTexto,contraClienteTexto,direccionTexto);
       try {
      // Crear escritor para el archivo
      FileWriter writer = new FileWriter("docs\\clientes.txt", true);
      BufferedWriter almacenamiento = new BufferedWriter(writer);

      almacenamiento.write( usuarioClienteTexto + "," + nombreClienteTexto + "," + contraClienteTexto + "," + direccionTexto);
      almacenamiento.newLine();
      almacenamiento.close();
      System.out.println("Datos del cliente guardados en clientes.txt");

    } catch (IOException e) {
      System.out.println("Error al guardar clientes: " + e.getMessage());
    }
  }

  public void guardarCuentasArchivo (Cliente cliente ){

    try {
      FileWriter writer = new FileWriter("docs\\cuentas.txt", true);
      BufferedWriter almacenamiento = new BufferedWriter(writer);

      almacenamiento.write(usuarioClienteTexto );
      for (Cuenta cuenta : cliente.getCuentas()) {
        almacenamiento.write("," + cuenta.getTipo());
      }
      almacenamiento.newLine();
      almacenamiento.close();
      System.out.println("cuentas del cliente guardadas en cuentas.txt ");
    } catch (IOException e) {
      System.out.println("Error al guardar las cuentas del cliente");
    }

  }

}











