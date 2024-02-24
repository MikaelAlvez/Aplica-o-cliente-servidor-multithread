package PraticaOnline1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AnelCliente {
	private String servidorIP;
    private int servidorPorta;
    
    public AnelCliente(String servidorIP, int servidorPorta) {
        this.servidorIP = servidorIP;
        this.servidorPorta = servidorPorta;
    }
    
    public void enviarMensagem(int remetente, int destino, String conteudo) {
        try {
            Socket socket = new Socket(servidorIP, servidorPorta);
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            Mensagem mensagem = new Mensagem(remetente, destino, conteudo);
            
            saida.writeObject(mensagem);
            saida.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String servidorIP = "localhost";
        int servidorPorta = 4096;
        AnelCliente cliente = new AnelCliente(servidorIP, servidorPorta);
        
        cliente.enviarMensagem(1, 2, "Prática Online 1 de distribuidos");
    }
}
