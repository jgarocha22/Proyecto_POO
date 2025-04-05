package ve.edu.ucab.lab;

import java.util.LinkedList;

public class Intermediario {
  private static Banco banco;

  public static void setBanco(Banco banco1){
    banco=banco1;
  }

  public static Banco getBanco(){
    return banco;
  }
  public static Administrador getAdministrador(){
    return banco.getAdmin();
  }

  public static LinkedList<Cliente> getClientesBanco(){
    return banco.getClientes();
  }


  public static Cuenta getCuenta(Cliente cliente, String cuenta){
    for (Cuenta c:cliente.getCuentas()){
      if (c.toString().equals(cuenta)){
        return c;
      }
    }
    return null;
  }



  //borrar y pasar a cliente
  public static LinkedList<Cuenta> getCuentasCliente(Cliente cliente){
    return cliente.getCuentas();
  }

  public static LinkedList<Transaccion> getTransacciones(Cuenta cuenta){
    return cuenta.getTransacciones();
  }





}
