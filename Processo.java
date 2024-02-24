package PraticaOnline1;

public class Processo {
    private int id;
    private int porta;
    private Processo proximo;

    public Processo(int id, int porta) {
        this.id = id;
        this.porta = porta;
    }

    public void setProximo(Processo proximo) {
        this.proximo = proximo;
    }

    public void enviarMensagem(Processo destino, String mensagem) {
        System.out.println("Processo " + id + " enviando mensagem para processo " + destino.getId() + ": " + mensagem);
        destino.receberMensagem(this, mensagem);
    }

    public void enviarMensagemBroadcast(String mensagem) {
        System.out.println("Processo " + id + " enviando mensagem broadcast: " + mensagem);
        proximo.receberMensagem(this, mensagem);
    }

    public void receberMensagem(Processo remetente, String mensagem) {
        System.out.println("Processo " + id + " recebeu mensagem do processo " + remetente.getId() + ": " + mensagem);
    }

    public int getId() {
        return id;
    }

    public int getPorta() {
        return porta;
    }
}
