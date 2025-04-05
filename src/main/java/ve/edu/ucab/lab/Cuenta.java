package ve.edu.ucab.lab;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Cuenta {
  private double saldo=0;
  private LinkedList<Transaccion> transacciones=new LinkedList<>();
  private String tipo = "";

  public Cuenta(String tipo) {
    this.tipo = tipo;
  }

  /** <p>Metodo para registrar la transaccion</p>
   * @param monto monto de la transaccion
   */
  private void registrarTransaccion(double monto){
    transacciones.add(new Transaccion(LocalDateTime.now() ,monto));
  }

  /** <p>Metodo para obtener saldo de la cuenta</p>
   * @return saldo de la cuenta
   */
  public double obtenerSaldo(){
    return saldo;
  }

  /** <p>Metodo para depositar saldo a la cuenta</p>
   * @param monto monto de la transaccion
   */
  public void depositarSaldo(double monto){
    saldo += monto;
    registrarTransaccion(monto);
  }

  /** <p>Metodo para retirar saldo a la cuenta</p>
   * @param monto monto de la transaccion
   */
  public void retirarSaldo(double monto){
    saldo -=monto;
    registrarTransaccion(-monto);
  }

  /** <p>Metodo para verificar si se puede retirar saldo con el monto deseado</p>
   * @param monto monto de la transaccion
   * @return retorna true si el monto es menor al saldo y false en caso contrario
   */
  public boolean retiroPosible(double monto){
    Boolean posible=true;
    if (saldo<monto){
      posible=false;
    }
    return posible;
  }

  /** <p>Metodo para mostrar transacciones por consola</p>
   */
  public void mostrarTransacciones(){
    for (Transaccion t:transacciones){
      t.mostrarTransaccion();
    }
  }

  public String getTipo() {
    return tipo;
  }

  public double getSaldo() {
    return saldo;
  }

  public LinkedList<Transaccion> getTransacciones() {
    return transacciones;
  }

  @Override
  public String toString() {
    return tipo + " ---- " + saldo;
  }
}