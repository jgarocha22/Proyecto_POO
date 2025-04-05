package ve.edu.ucab.lab;

import java.util.LinkedList;

public class Banco {
  private LinkedList<Cliente>clientes = new LinkedList<>();
  private Administrador admin= new Administrador();

  public Banco(){
    admin.setClientesAdministrado(clientes);
  }

  /**<p>Metodo para obtener la lista de clientes</p>
   * @return lista de clientes
   */
  public LinkedList getClientes(){

    return clientes;
  }

  /**<p>Metodo para settear la lista de clientes</p>
   */
  public void setclientes(LinkedList<Cliente> clientes){
    this.clientes=clientes;
  }

  /**<p>Metodo para obtener el administrador</p>
   * @return admin
   */
  public Administrador getAdmin(){
    return admin;
  }
}
