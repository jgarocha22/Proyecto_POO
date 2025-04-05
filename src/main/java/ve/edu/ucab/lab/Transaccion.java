package ve.edu.ucab.lab;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
  private LocalDateTime fechaEmision;
  private double monto;

  /** <p>Constructor Transaccion</p>
   * @param fechaEmision cuando se hizo la transaccion
   * @param monto monto de la transaccion
   */
  public Transaccion(LocalDateTime fechaEmision, double monto) {
    this.fechaEmision = fechaEmision;
    this.monto = monto;
  }

  /** <p>Metodo para mostrar transaccion por consola</p>
   */
  public void mostrarTransaccion(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"); // formato de la fecha
    System.out.println(fechaEmision.format(formatter)+" "+ monto); //se aplica formato


  }

  /** <p>Metodo para la fecha de emisión de la transacción</p>
   */
  public String getFechaEmision() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"); // formato de la fecha
    return fechaEmision.format(formatter);
  }

  /** <p>Metodo para obtener el monto de la transaccion</p>
   * @return monto de la transaccion
   */
  public double getMonto() {
    return monto;
  }
}
