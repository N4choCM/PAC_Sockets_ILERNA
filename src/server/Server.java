package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    //Establecemos un puerto.
    private final int PUERTO = 9876;

    //Definimos la conexión.
    private final ServerSocket serverSocket = new ServerSocket(PUERTO);

    public Server() throws IOException {
    }

    // Procedimiento para iniciar la conexión del servidor.
    public void iniciarServer() throws IOException{

        //Vamos a aceptar los datos que llegarán del cliente
        while (true){
            System.out.println("Esperando al cliente");

            //Iniciamos el cliente.
            Socket socket = this.serverSocket.accept();

            System.out.println("Cliente conectado");

            // Inicializamos las variables para la E/S de datos.
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            DataInputStream entrada = new DataInputStream(socket.getInputStream());

            salida.writeUTF("¿Cómo te llamas?");
            String nombre = entrada.readUTF();

            salida.writeUTF("Hola, " + nombre + ".\n¿Cuántas tareas deseas realizar?");
            int numeroTareas = Integer.parseInt(entrada.readUTF());

            // Inicializamos una lista en la que iremos añadiendo las tareas realizadas
            List<Tarea> tareas = new ArrayList<>();

            // Bucle for que realiza tantas tareas como el usuario haya indicado y las añade una por una en la lista.
            for(int i = 1; i <= numeroTareas; i++){
                Tarea tarea = new Tarea();
                salida.writeUTF("Ejecutando tarea número " + i + ".");
                salida.writeUTF("Por favor, introduzca la descripción de la tarea:");
                tarea.setDescripcion(entrada.readUTF());
                salida.writeUTF("Por favor, introduzca el estado de la tarea:");
                tarea.setEstado(entrada.readUTF());
                tareas.add(tarea);
            }

            // Mensaje informativo
            salida.writeUTF("Se va a proceder a enviar las tareas.");

            // Bucle for que envía las tareas una por una con el método toString().
            for (Tarea tarea : tareas) {
                salida.writeUTF(tarea.toString());
            }

            // Cerramos conexiones.
            socket.close();
            System.out.println("Cliente desconectado.");
            entrada.close();
            salida.close();
        }
    }
}
