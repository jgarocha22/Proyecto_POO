package ve.edu.ucab.lab;

import ve.edu.ucab.lab.grafica.MenuPrincipal;

public class Main {
  protected Main() {
  }

  public static void main(final String[] args) {


    Banco b1 = new Banco();
    Intermediario.setBanco(b1);
    Administrador admin = b1.getAdmin();
    admin.obtenerClientesdeArchivo();
    admin.actualizarTiposCuentas();
    admin.cargarTransaccionesDesdeArchivo();

    //Inicio de parte gráfica.
    MenuPrincipal ventana1= new MenuPrincipal();
  }


}
