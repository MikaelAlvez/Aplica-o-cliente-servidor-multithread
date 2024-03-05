package TopologiaEstrela;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        final int PORTA = 4096;
        int contadorClientes = 0;

        try (ServerSocket servidorSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            System.out.println("Aguardando conexões...\n");

            while (true) {
                Socket socketCliente = servidorSocket.accept();
                System.out.println("Cliente conectado: " + socketCliente);

                contadorClientes++;

                Thread threadCliente = new Thread(new ServidorThread(socketCliente, contadorClientes, null));
                threadCliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
