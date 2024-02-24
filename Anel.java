package PraticaOnline1;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Anel {
    static class Processo implements Runnable {
        private final int id;
        private final BlockingQueue<String> filaEntrada;
        private final BlockingQueue<String> filaSaida;
        private Processo proximo;

        public Processo(int id, Processo proximo) {
            this.id = id;
            this.filaEntrada = new LinkedBlockingQueue<>();
            this.filaSaida = new LinkedBlockingQueue<>();
            this.proximo = proximo;
        }

        public void enviarMensagem(String mensagem) {
            filaSaida.offer(mensagem);
        }

        public void receberMensagem(String mensagem) {
            filaEntrada.offer(mensagem);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String mensagem = filaEntrada.poll(100, TimeUnit.MILLISECONDS);
                    if (mensagem != null) {
                        System.out.println("Processo " + id + " recebeu mensagem: " + mensagem);
                        if (proximo != null) {
                            System.out.println("Chegou em " + proximo.id);
                            proximo.receberMensagem(mensagem);
                        }
                    }
                    mensagem = filaSaida.poll(100, TimeUnit.MILLISECONDS);
                    if (mensagem != null) {
                        if (proximo != null) {
                        	System.out.println("\nProcesso " + id + " enviou mensagem para processo " + proximo.proximo.proximo.id + ": " + mensagem);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Processo p1 = new Processo(1, null);
        Processo p2 = new Processo(2, null);
        Processo p3 = new Processo(3, null);
        Processo p4 = new Processo(4, null);

        p1.proximo = p2;
        p2.proximo = p3;
        p3.proximo = p4;
        p4.proximo = p1;

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(p1);
        executor.execute(p2);
        executor.execute(p3);
        executor.execute(p4);

        System.out.println("Informe o ID do processo remetente:");
        int remetente = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Informe o ID do processo destino:");
        int destino = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Informe a mensagem:");
        String mensagem = scanner.nextLine();

        Processo processoRemetente = obterProcessoPorId(remetente, p1);
        Processo processoDestino = obterProcessoPorId(destino, p1);
        if (processoRemetente != null && processoDestino != null) {
            processoRemetente.enviarMensagem(mensagem);
            while (processoRemetente != processoDestino) {
                processoRemetente = processoRemetente.proximo;
                if (processoRemetente != null) {
                    System.out.println("\nEnviando mensagem para processo " + processoRemetente.id);
                    System.out.println("\nChegou no processo " + processoRemetente.id + "...");
                } else {
                    System.out.println("\nProcesso remetente não encontrado.");
                    break;
                }
            }
        } else {
            System.out.println("\nProcesso remetente ou destino não encontrado.");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdownNow();
        scanner.close();
    }

    private static Processo obterProcessoPorId(int id, Processo processo) {
        Processo atual = processo;
        do {
            if (atual.id == id) {
                return atual;
            }
            atual = atual.proximo;
        } while (atual != processo);
        return null;
    }
}
