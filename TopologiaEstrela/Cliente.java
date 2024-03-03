package TopologiaEstrela;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Cliente(String servidorHost, int porta) {
        try {
            socket = new Socket(servidorHost, porta);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conectado ao servidor: " + servidorHost + ":" + porta);
        } catch (IOException e) {
            e.printStackTrace();
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

    public Mensagem receberMensagem() {
        try {
            return (Mensagem) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void fecharConexao() {
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
