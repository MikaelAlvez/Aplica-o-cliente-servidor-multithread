package TopologiaAnel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ImpServidor implements Runnable {
	
	public Socket socketCliente;
	private Cliente cliente;
	
	private ObjectInputStream objEnt;
	private ObjectOutputStream objSai;
	
	public String processo;
	
	private boolean conexao = true;

	public ImpServidor(String processo, Socket socketcliente, Cliente cliente) {
		this.processo = processo;
		this.socketCliente = socketcliente;
		this.cliente = cliente;
		
		try {
			this.objEnt = new ObjectInputStream(socketCliente.getInputStream());
			this.objSai = new ObjectOutputStream(socketCliente.getOutputStream());
		} catch (IOException e) {
			e.getMessage();
		}
	}

	public void run() {
		Mensagem mensagemEncaminhada;
		try {

			mensagemEncaminhada = (Mensagem) this.objEnt.readObject();

			System.out.println(this.processo + " conectado à " + mensagemEncaminhada.getConteudo() + " por " + socketCliente.getInetAddress().getHostName() + ": " + socketCliente.getInetAddress().getHostAddress());
			objSai.writeObject(new Mensagem(this.processo, null, this.processo, "unicast"));

			while (conexao) {
				mensagemEncaminhada = (Mensagem) objEnt.readObject();

				if (mensagemEncaminhada.getTipo().equals("broadcast") || mensagemEncaminhada.getTipo().equals("Broadcast")) {
					if (mensagemEncaminhada.getConteudo().equalsIgnoreCase("Sair") || mensagemEncaminhada.getConteudo().equalsIgnoreCase("Encerrar"))
						conexao = false;
					else if (!this.processo.equals(mensagemEncaminhada.getEmissor())) {
						System.out.println("\nMensagem recebida " + mensagemEncaminhada);
						encaminharMensagem(mensagemEncaminhada);
					}
				}

				if (mensagemEncaminhada.getTipo().equals("unicast") || mensagemEncaminhada.getTipo().equals("Unicast")) {
					if (mensagemEncaminhada.getDestinatario() != null && mensagemEncaminhada.getDestinatario().equals(this.processo)) {
						if (mensagemEncaminhada.getConteudo().equalsIgnoreCase("Sair") || mensagemEncaminhada.getConteudo().equalsIgnoreCase("Encerrar")) {
							conexao = false;
						} else {
							System.out.println("\nMensagem recebida " + mensagemEncaminhada);
						}
					} else if (!this.processo.equals(mensagemEncaminhada.getEmissor())) {
						encaminharMensagem(mensagemEncaminhada);
					} else {
						System.out.println("Destinatario informado não encontrado!"); 
					}
				}
			}
			
			objEnt.close();
			objSai.close();
			
			System.out.println(socketCliente.getInetAddress().getHostAddress() + " encerrado");
			socketCliente.close();
			
		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void encaminharMensagem(Mensagem mensagemRecebida) {
		System.out.println("\n" + this.processo + " encaminhando mensagem de " + mensagemRecebida.getEmissor() + " com destino " + mensagemRecebida.getDestinatario());
		ObjectOutputStream outPut = cliente.getObjectOutputStream();
		try {
			outPut.writeObject(mensagemRecebida);
			outPut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}