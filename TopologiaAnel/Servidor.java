package TopologiaAnel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{

	ServerSocket socketServidor;
	Socket socketcliente;
	
	Cliente cliente;
	
	String processo;
	int porta;
	
    
	public Servidor(String processo, int porta, Cliente cliente) {
		this.processo = processo;
		this.porta = porta;
		this.cliente = cliente;
	}

	public void run() {
		try {
            socketServidor = new ServerSocket(porta);
            System.out.println(this.processo + " iniciado na porta " + socketServidor.getLocalPort());
            
            while (true) {
            	socketcliente = socketServidor.accept();
                ImpServidor servidor = new ImpServidor(this.processo, socketcliente, cliente);
                Thread threadServidor = new Thread(servidor);
                threadServidor.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
