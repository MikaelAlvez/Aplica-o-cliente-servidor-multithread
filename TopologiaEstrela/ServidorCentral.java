package TopologiaEstrela;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorCentral {
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ServidorCentral(int porta) {
        try {
            serverSocket = new ServerSocket(porta);
            System.out.println("Servidor aguardando conexões na porta: " + porta);
            socket = serverSocket.accept();
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conexão estabelecida com cliente: " + socket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mensagem receberMensagem() {
        try {
            return (Mensagem) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void enviarMensagem(Mensagem mensagem) {
        try {
            output.writeObject(mensagem);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fecharConexao() {
        try {
            output.close();
            input.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
