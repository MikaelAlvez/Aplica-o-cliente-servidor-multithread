package TopologiaEstrela;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        final String SERVIDOR_IP = "localhost";
        final int SERVIDOR_PORTA = 4096;

        try (
                Socket socketCliente = new Socket(SERVIDOR_IP, SERVIDOR_PORTA);
                ObjectOutputStream saida = new ObjectOutputStream(socketCliente.getOutputStream())
        ) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Digite o tipo de mensagem (Broadcast ou Unicast): ");
                String tipoMensagem = scanner.nextLine();

                System.out.println("Digite o destinatário: ");
                String destinatario = scanner.nextLine();

                System.out.println("Digite a mensagem (ou 'Encerrar' para sair): ");
                String mensagemString = scanner.nextLine();

                Mensagem mensagem = new Mensagem("P2", destinatario, mensagemString, tipoMensagem);
                saida.writeObject(mensagem);

                if (mensagemString.equalsIgnoreCase("Encerrar")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

