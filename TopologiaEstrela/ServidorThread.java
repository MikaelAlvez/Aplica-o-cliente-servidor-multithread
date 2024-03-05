package TopologiaEstrela;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServidorThread implements Runnable {
    private Socket socketCliente;
    private int numeroCliente;
    private ObjectOutputStream saidaP1;
    private ObjectOutputStream[] saidasP2P4;

    public ServidorThread(Socket socketCliente, int numeroCliente, ObjectOutputStream[] saidasP2P4) {
        this.socketCliente = socketCliente;
        this.numeroCliente = numeroCliente;
        this.saidasP2P4 = saidasP2P4; // Inicializar saidasP2P4
    }

    @Override
    public void run() {
        try (
                ObjectInputStream entrada = new ObjectInputStream(socketCliente.getInputStream())
        ) {
            Mensagem mensagem;

            do {
                mensagem = (Mensagem) entrada.readObject();
                System.out.println("Cliente " + numeroCliente + " enviou: " + mensagem);

                if (mensagem.getOperacao().equalsIgnoreCase("unicast")) {
                    if (mensagem.getDestinatario().equalsIgnoreCase("P1")) {
                        if (saidaP1 == null) {
                            saidaP1 = new ObjectOutputStream(socketCliente.getOutputStream());
                        }
                        System.out.println("Encaminhando mensagem de " + mensagem.getRemetente() + " para " + mensagem.getDestinatario());
                        saidaP1.writeObject(mensagem);
                        saidaP1.flush();
                    } else {
                        // Encaminha a mensagem para o destinatário
                        for (ObjectOutputStream saidaDestino : saidasP2P4) {
                            if (saidaDestino != null) {
                                saidaDestino.writeObject(mensagem);
                                saidaDestino.flush();
                            }
                        }
                    }
                } else if (mensagem.getOperacao().equalsIgnoreCase("broadcast")) {
                    // Envia a mensagem para todos os clientes, incluindo o remetente
                    System.out.println("Broadcast: Mensagem de " + mensagem.getRemetente() + " para todos os clientes");

                    // Enviar a mensagem para cada saída na lista
                    for (ObjectOutputStream s : saidasP2P4) {
                        if (s != null) {
                            s.writeObject(mensagem);
                            s.flush();
                        }
                    }
                }

            } while (!mensagem.getMensagem().equalsIgnoreCase("Encerrar"));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socketCliente.close();
                if (saidaP1 != null) {
                    saidaP1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
