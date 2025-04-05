package ve.edu.ucab.lab.grafica;

//Import propios
import ve.edu.ucab.lab.Cliente;
import ve.edu.ucab.lab.Cuenta;
import ve.edu.ucab.lab.Intermediario;
import ve.edu.ucab.lab.Transaccion;

//Import de Java
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

public class VentanaTransacciones {
  private JTable table1;
  private JComboBox<Cuenta> comboBox1;
  private JFrame frame;
  private JPanel panel1;
  private JButton salirButton;

  public VentanaTransacciones(Cliente clienteActual) {
    frame = new JFrame("Transacciones Usuario: " + clienteActual.getNombreCliente()); // Inicializamos el JFrame
    frame.setContentPane(panel1);
    frame.setSize(400, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    panel1.setLayout(new BorderLayout());

    colocarOpciones(clienteActual);

    // Inicializar el JTable con un modelo de tabla que incluya los títulos de las columnas
    String[] columnas = {"Fecha (DD/MM/YYYY hh:mm)", "Monto"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
      @Override
      public boolean isCellEditable(int filas, int columnas) {
        return false; // Hacer que todas las celdas no sean editables
      }
    };
    table1.setModel(modelo);

    // Deshabilitar el redimensionamiento de las columnas
    table1.getTableHeader().setResizingAllowed(false);

    // Render personalizado para la columna de monto
    table1.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component celda = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String monto = (String) value;
        if (monto.startsWith("-")) { // Saber si el monto es negativo se verifica si empieza con menos y se pinta en rojo
          celda.setForeground(Color.RED);
        } else {
          celda.setForeground(Color.GREEN);
        }
        setHorizontalAlignment(SwingConstants.CENTER); // Esto es para centrar los montos
        return celda;
      }
    });

    // Render personalizado para la columna de fecha
    DefaultTableCellRenderer fechaRenderer = new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component celda = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(SwingConstants.CENTER); // Esto es para centrar las fechas
        return celda;
      }
    };

    // Render personalizado para la columna de fecha
    table1.getColumnModel().getColumn(0).setCellRenderer(fechaRenderer);

    // Ajustar el ancho de la columna de fecha
    table1.getColumnModel().getColumn(0).setPreferredWidth(150);

    // Hacer que la tabla tenga un scroll si es necesario
    JScrollPane scrollPane = new JScrollPane(table1);

    // Añadir el JComboBox en la parte superior, el JScrollPane en el centro
    panel1.add(comboBox1, BorderLayout.NORTH);
    panel1.add(scrollPane, BorderLayout.CENTER);

    // Crear un panel para el botón de salir con FlowLayout centrado
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(salirButton);
    panel1.add(buttonPanel, BorderLayout.SOUTH);

    comboBox1.addActionListener(e -> {
      Cuenta cuentaSeleccionada = (Cuenta) comboBox1.getSelectedItem();
      if (cuentaSeleccionada != null) {
        actualizarTabla(cuentaSeleccionada);
      }
    });

    salirButton.addActionListener(e -> frame.dispose());
  }

  private void colocarOpciones(Cliente clienteActual) {
    LinkedList<Cuenta> temp = Intermediario.getCuentasCliente(clienteActual);
    for (Cuenta it : temp) {
      comboBox1.addItem(it);
    }
  }

  private void actualizarTabla(Cuenta cuentaSeleccionada) {
    LinkedList<Transaccion> transacciones = Intermediario.getTransacciones(cuentaSeleccionada);
    DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
    modelo.setRowCount(0); // Limpiar las filas existentes

    for (Transaccion t : transacciones) {
      String fecha = t.getFechaEmision();
      String monto = String.valueOf(t.getMonto());
      modelo.addRow(new Object[]{fecha, monto});
    }
  }

}