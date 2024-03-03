package TopologiaAnel;

public class ClienteServidor {
	Cliente cliente;
	Servidor servidor;
	
	public ClienteServidor(String processo, int endereco, int porta) {
		this.cliente = new Cliente(processo, "localhost", porta);
		Thread threadCliente = new Thread(this.cliente);
		threadCliente.start();
		
		this.servidor = new Servidor(processo, endereco, this.cliente);
		Thread threadServidor = new Thread(this.servidor);
		threadServidor.start();
		
	}
}

