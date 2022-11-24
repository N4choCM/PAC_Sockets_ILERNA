package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //Establecemos un puerto y una IP.
    private final String HOST = "localhost";
    private final int PUERTO = 9876;

    //Iniciamos el cliente.
    private final Socket socket = new Socket(HOST, PUERTO);

    public Client() throws IOException{
    }

    // Procedimiento para iniciar la conexión del cliente.
    public void iniciarCliente() throws IOException{

        // Inicializamos las variables para la E/S de datos.
        DataInputStream entrada = new DataInputStream(this.socket.getInputStream());
        DataOutputStream salida = new DataOutputStream(this.socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        // ¿Cómo te llamas?
        System.out.println(entrada.readUTF());
        salida.writeUTF(scanner.nextLine());

        // ¿Cuántas tareas deseas realizar?
        System.out.println(entrada.readUTF());
        String numeroTareas = String.valueOf(scanner.nextInt());
        scanner.nextLine();
        salida.writeUTF(numeroTareas);

        // Bucle for que realiza tantas tareas como el usuario haya indicado y con la información que este haya proporcionado.
        for(int i = 0; i < Integer.parseInt(numeroTareas); i++){
            System.out.println(entrada.readUTF());
            salida.writeUTF("Inicio de tarea recibido.");
            System.out.println(entrada.readUTF());
            String descripcion = scanner.nextLine();
            salida.writeUTF(descripcion); //descripción
            System.out.println(entrada.readUTF());
            String estado = scanner.nextLine();
            salida.writeUTF(estado); //estado
        }

        //Mensaje informativo
        System.out.println(entrada.readUTF());

        // Bucle for que recibe las tareas una por una con el método toString().
        for(int i = 0; i < Integer.parseInt(numeroTareas); i++){
            System.out.println(entrada.readUTF());
            salida.writeUTF("Tarea número " + i + " recibida.");

        }

        // Cerramos conexiones.
        this.socket.close();
        entrada.close();
        salida.close();
        System.out.println("Fin, Desconectado del servidor");
        scanner.close();
    }
}
