package TopologiaAnel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente implements Runnable{
	
	private ImpCliente cliente;
	InetAddress inet;
	
	String processo;
	String ip;
	int porta;
	
	private final Object lock = new Object();
	
	public Cliente(String nome, String ip, int porta) {
		this.processo = nome;
		this.ip = ip;
		this.porta = porta;
	}
	
	Socket socket = new Socket();

	public Socket getSocket() {
		return this.socket;
	}
	
	public ObjectOutputStream getObjectOutputStream() {
		return this.cliente.getObjectOutputStream();
	}
	
	public Mensagem getMensagemEncaminhada() {
		return this.cliente.getMensagemEncaminhada();
	}
	
	public void run() {
		while (!this.socket.isConnected()) {
			try {
	            socket = new Socket(ip, porta);
	            inet = socket.getInetAddress();
	            
	            System.out.println("\n" + this.processo + " conectado no servidor");
	            System.out.println(inet.getHostName()+ ": " + inet.getHostAddress() + "\n");
	            
	        } catch (IOException e) {
	        	System.err.println(this.processo + " conectando ao próximo processo " + this.porta + "...");
	        	try {
	        		synchronized(lock) {
	        	        lock.wait(15000);
	        	    }				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
	        } 
			
		}
		this.cliente = new ImpCliente(this.processo, socket);
		Thread t = new Thread(this.cliente);
        t.start();
	}
}