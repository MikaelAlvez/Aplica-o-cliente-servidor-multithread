package PraticaOnline1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class AnelServidor {
	 private ServerSocket servidor;
	    private int porta;
	    private Anel topologia;
	    
	    public AnelServidor(int porta) {
	        this.porta = porta;
	        this.topologia = new Anel();
	    }
	    
	    public void iniciar() {
	        try {
	            servidor = new ServerSocket(porta);
	            System.out.println("Servidor do Anel iniciado na porta " + porta);
	            
	            while (true) {
	                Socket cliente = servidor.accept();
	                System.out.println("Nova conexão: " + cliente.getInetAddress().getHostAddress());
	                
	                new Thread(new ClienteHandler(cliente)).start();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private class ClienteHandler implements Runnable {
	        private Socket cliente;
	        
	        public ClienteHandler(Socket cliente) {
	            this.cliente = cliente;
	        }
	        
	        @Override
	        public void run() {
	            try {
	                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
	                Mensagem mensagem = (Mensagem) entrada.readObject();
	                entrada.close();
	                
	                System.out.println("Mensagem recebida de " + mensagem.getRemetente() + ": " + mensagem.getConteudo());
	                
	                encaminharMensagem(mensagem);
	            } catch (IOException | ClassNotFoundException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    cliente.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        private void encaminharMensagem(Mensagem mensagem) {
	            int remetente = mensagem.getRemetente();
	            int destino = mensagem.getDestino();
	            
	            List<Integer> vizinhos = topologia.getConexoes(remetente);
	            
	            int indiceDestino = vizinhos.indexOf(destino);
	            for (int i = 1; i <= vizinhos.size(); i++) {
	                int vizinho = vizinhos.get((indiceDestino + i) % vizinhos.size());
	                if (vizinho != remetente) {
	                    try {
	                        Socket socketVizinho = new Socket("localhost", vizinho);
	                        ObjectOutputStream saida = new ObjectOutputStream(socketVizinho.getOutputStream());
	                        saida.writeObject(mensagem);
	                        saida.close();
	                        socketVizinho.close();
	                        
	                        System.out.println("Mensagem encaminhada para " + vizinho + ": " + mensagem.getConteudo());
	                        break;
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }
	    
	    public static void main(String[] args) {
	        int porta = 4096;
	        AnelServidor servidor = new AnelServidor(porta);
	        servidor.iniciar();
	    }
}
