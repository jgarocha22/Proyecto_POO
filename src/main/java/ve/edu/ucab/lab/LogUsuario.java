package ve.edu.ucab.lab;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class LogUsuario {
  private String cliente;
  private LocalDateTime fecha;
  private String estadoSesion;

  /** <p>Metodo para construtir el log</p>
   * @param cliente nombre del cliente
   * @param fecha fecha de inicio o fin de sesion
   * @param estadoSesion estado de la sesion
   */
  public LogUsuario(String cliente, LocalDateTime fecha, String estadoSesion) {
    this.cliente = cliente;
    this.fecha = fecha;
    this.estadoSesion = estadoSesion;
  }

  // Métodos getter
  public String getCliente() {
    return cliente;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public String getEstadoSesion() {
    return estadoSesion;
  }

  /** <p>Metodo de mensaje de entrada de un usuario</p>
   * @return del mensaje de entrada del usuario
   */
  public String mensajeEntrada() {
    return "Cliente: " + cliente +
      "\nFecha de inicio: " + fecha +
      "\nEstado de sesión: " + estadoSesion + "\n";
  }

  /** <p>Metodo de mensaje de salida de un usuario</p>
   * @return del mensaje de salida del usuario
   */
  public String mensajeSalida() {
    return "Cliente: " + cliente +
      "\nFecha de fin: " + fecha +
      "\nEstado de sesión: " + estadoSesion + "\n";
  }

  /** <p>Metodo para leer los logs del archivo y guardarlos en una lista</p>
   * @param filePath ruta del archivo
   * @return lista de logs
   */
  public static LinkedList<LogUsuario> leerArchivoLog(String filePath) {
    LinkedList<LogUsuario> logs = new LinkedList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String cliente = line.split(": ")[1];
        LocalDateTime fecha = LocalDateTime.parse(reader.readLine().split(": ")[1]);
        String estadoSesion = reader.readLine().split(": ")[1];
        logs.add(new LogUsuario(cliente, fecha, estadoSesion));
        reader.readLine(); // Salta las lineas vacias en el log
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return logs;
  }

  /** <p>Metodo para guardar los logs en un archivo</p>
   * @param filePath ruta del archivo
   * @param logs lista de logs
   */
  public static void guardarLogs(String filePath, LinkedList<LogUsuario> logs) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (LogUsuario log : logs) {
        if(log.estadoSesion.equals("ACTIVA")) {
          writer.write(log.mensajeEntrada().replace("\n", "\r\n"));
          writer.write("\r\n"); // Asegura que cada log esté separado por una línea CRLF
        }
        else{
          writer.write(log.mensajeSalida().replace("\n", "\r\n"));
          writer.write("\r\n"); // Asegura que cada log esté separado por una línea CRLF
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** <p>Metodo para agregar un log a la lista de logs</p>
   * @param filePath ruta del archivo
   * @param newLog log a agregar
   */
  public static void addLog(String filePath, LogUsuario newLog) {
    LinkedList<LogUsuario> logs = leerArchivoLog(filePath);
    logs.add(newLog);
    guardarLogs(filePath, logs);
  }
}