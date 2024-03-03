package TopologiaAnel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ImpCliente implements Runnable {
    
    private Socket socketcliente;
    private String processo;
    
    private boolean conexao = true;
    private Mensagem mensagemEncaminhada;
    
    private ObjectInputStream objEnt;
    private ObjectOutputStream objSai;

    public ImpCliente(String processo, Socket socket) {
        this.processo = processo;
        this.socketcliente = socket;
    }

    public void run() {
        try {
            
            this.objSai = new ObjectOutputStream(socketcliente.getOutputStream());
            this.objSai.writeObject(new Mensagem(this.processo, null, this.processo, "unicast"));
            this.objEnt = new ObjectInputStream(socketcliente.getInputStream());
            
            Mensagem serverNome = (Mensagem) this.objEnt.readObject();
            
            System.out.println(this.processo + " está conectado ao servidor " + serverNome.getEmissor());
            
            Scanner operacao = new Scanner(System.in);
            Mensagem mensagem = null;
            String opcaoString = "";
            while (conexao) {
                
                System.out.println("\nA mensagem será enviada por: unicast ou Broadcast? ");
                do {
                    opcaoString = operacao.nextLine();
                    
                } while(!opcaoString.equals("unicast") && !opcaoString.equals("Unicast") && !opcaoString.equals("Broadcast") && !opcaoString.equals("broadcast"));
                
                if (opcaoString.equals("unicast") || opcaoString.equals("Unicast")) {
                    mensagem = construirMensagem("unicast", operacao);
                } else if (opcaoString.equals("broadcast") || opcaoString.equals("Broadcast")) {
                    mensagem = construirMensagem("broadcast", operacao);
                }
                
                mensagemEncaminhada = mensagem;
                
                if (mensagem.getConteudo().equalsIgnoreCase("Sair") || mensagem.getConteudo().equalsIgnoreCase("Encerrar")) {
                    conexao = false;
                }else {
                    System.out.println("\n" + mensagem.getEmissor() + " enviando mensagem para " + mensagem.getDestinatario() + "...");
                this.objSai.writeObject(mensagem);
                }
            }
            
            objEnt.close();
            objSai.close();
            operacao.close();
            
            socketcliente.close();
            System.out.println("Processo " + this.processo + " encerrrado");
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public ObjectOutputStream getObjectOutputStream() {
        return this.objSai;
    }

    public Mensagem getMensagemEncaminhada() {
        return this.mensagemEncaminhada;
    }
    
    private Mensagem construirMensagem(String tipo, Scanner in) {
        String emissor = this.processo;
        String destinatario;
        if(tipo.equals("unicast")) {
            System.out.println("Destinatário: ");
            destinatario = in.nextLine();
        } else {
            destinatario = "todos os processos";
        }
        System.out.println("Mensagem: ");
        String conteudo = in.nextLine();
        return new Mensagem(emissor, destinatario, conteudo, tipo);
    }
}
