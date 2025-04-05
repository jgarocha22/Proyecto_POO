package ve.edu.ucab.lab;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Cliente extends Usuario{
  private LinkedList<Cuenta> cuentas = new LinkedList<>();

  /**
   * <p>Constructor de Cliente</p>
   * @param nombreCliente nombre del cliente
   * @param nombreUsuario nombre del usuario
   * @param contraseña    contraseña del usuario
   * @param direccion     direccion del usuario
   */
  public Cliente(String nombreCliente,String nombreUsuario, String contraseña, String direccion) {
    super(nombreCliente,nombreUsuario, contraseña, direccion);
  }

  public void setCuentas(String tipoDeCuenta){
    if (estaCuenta(tipoDeCuenta)==false) {
      cuentas.add(new Cuenta(tipoDeCuenta));
    }
  }


  /** <p>Metodo para depositar saldo a la cuenta</p>
   * @param monto monto de la transaccion
   */
  public void depositar(double monto,Cuenta cuenta){
    cuenta.depositarSaldo(monto);
  }

  /** <p>Metodo para retirar saldo a la cuenta</p>
   * @param monto monto de la transaccion
   */
  public void retirarSaldo(double monto,Cuenta cuenta){
    if(cuenta.retiroPosible(monto)==true) {
      cuenta.retirarSaldo(monto);
    }
    else {

      try {
        throw new Exception("No puede retirar un monto mayor a su saldo");
      }
      catch (Exception e){
        e.getMessage();
      }

    }
  }

  /** <p>Metodo para obtener saldo de la cuenta</p>
   * @return saldo de la cuenta
   */
  public double saldoDisponible(Cuenta cuenta){
    return cuenta.obtenerSaldo();
  }

  /** <p>Metodo para validar contraseña</p>
   * @return true si es igual false en caso contrario
   */
  public boolean validarContraseña(String contraseña){

    boolean boleano= false;
    if (super.getContraseña() == contraseña){
      boleano=true;
    }
    return boleano;
  }

  /** <p>Metodo para mostrar las transacciones de la cuenta en consola</p>
   */
  public void resumenTransacciones(Cuenta cuenta){
    cuenta.mostrarTransacciones();
  }

  public LinkedList<Cuenta> getCuentas() {
    return this.cuentas;
  }

  @Override
  public String toString() {
    return getNombreCliente() +" // Usuario: "+getNombreUsuario();
  }

  /** <p>Metodo para validar si esta una cuenta</p>
   * @param tipoCuenta a buscar si esta
   * @return true si esta y false en caso contrario
   */
  public boolean estaCuenta(String tipoCuenta){
    boolean tof =false;

    for (Cuenta cuenta : cuentas){
      if (cuenta.getTipo().matches(tipoCuenta)){
        tof=true;
        break;
      }
    }
    return tof;
  }

  /** <p>Metodo para eliminar una cuenta</p>
   * @param tipoCuenta se le pasa la cuenta a eliminar
   */
  public void eliminarCuenta(String tipoCuenta) {
    if (estaCuenta(tipoCuenta)) {
      Cuenta cuentaAEliminar = buscarCuenta(tipoCuenta);
      double saldo = cuentaAEliminar.obtenerSaldo();
      cuentas.remove(cuentaAEliminar);
      transferirSaldo(tipoCuenta, saldo);
    }
  }

  /** <p>Metodo para transferir saldo de una cuenta eliminada a otra</p>
   * @param tipoCuenta se le pasa la cuenta a buscar
   * @param saldo se le pasa el saldo a transferira otra cuenta
   *              en caso de que se elimine la cuenta
   */
  private void transferirSaldo(String tipoCuenta, double saldo) {

    if (tipoCuenta.equals("Corriente") && estaCuenta("Ahorro")) {
      Cuenta cuentaAhorro = buscarCuenta("Ahorro");
      cuentaAhorro.depositarSaldo(saldo);
    }
    else if (tipoCuenta.equals("Ahorro") && estaCuenta("Corriente")) {
      Cuenta cuentaCorriente = buscarCuenta("Corriente");
      cuentaCorriente.depositarSaldo(saldo);
    }
    else if (tipoCuenta.equals("Dolares") && estaCuenta("Corriente")){
      Cuenta cuentaCorriente = buscarCuenta("Corriente");
      double saldoCorriente = Math.round((saldo * 54) * 100.0) / 100.0;
      cuentaCorriente.depositarSaldo(saldoCorriente);
    }
    else if(tipoCuenta.equals("Dolares") && estaCuenta("Ahorro")){
      Cuenta cuentaAhorro = buscarCuenta("Ahorro");
      double saldoAhorro = Math.round((saldo * 54) * 100.0) / 100.0;
      cuentaAhorro.depositarSaldo(saldoAhorro);
    }
    else if (estaCuenta("Dolares")) {
      Cuenta cuentaDolares = buscarCuenta("Dolares");
      double saldoDolares = Math.round((saldo / 54) * 100.0) / 100.0;
      cuentaDolares.depositarSaldo(saldoDolares);
    }
  }

  /** <p>Metodo para buscar una cuenta del cliente</p>
   * @param tipoCuenta a buscar si esta
   * @return retorna la cuenta si la encuentra y null en caso contrario
   */
  public Cuenta buscarCuenta(String tipoCuenta){
    if (estaCuenta(tipoCuenta)) {
      for (Cuenta cuenta : cuentas) {
        if (cuenta.getTipo().equals(tipoCuenta)) {
          return cuenta;
        }
      }
    }
    return null;
  }

  /** <p>Metodo para obtener las cuentas del archivo</p>
   * @return retorna las cuentas del archivo
   */
  public LinkedList<Cuenta> obtenerCuentasDeArchivo(){
    try (BufferedReader br = new BufferedReader(new FileReader("docs\\cuentas.txt"))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        String[] datos = linea.split(",");
        String usuario = datos[0];
        if (usuario.matches(getNombreUsuario())){
          for (int i =1;i<datos.length;i++){
            setCuentas(datos[i]);
          }
          break;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return cuentas;
  }

}