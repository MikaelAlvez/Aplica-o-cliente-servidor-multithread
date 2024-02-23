package PraticaOnline1;

public class Processo {
    private int id;
    private Processo proximo;

    public Processo(int id) {
        this.id = id;
    }

    public void setProximo(Processo proximo) {
        this.proximo = proximo;
    }

    public void enviarMensagem(Processo destino, String mensagem) {
        System.out.println("Processo " + id + " enviando mensagem para processo " + destino.getId() + ": " + mensagem);
        destino.receberMensagem(mensagem);
    }

    public void enviarMensagemBroadcast(String mensagem) {
        System.out.println("Processo " + id + " enviando mensagem broadcast: " + mensagem);
        proximo.receberMensagem(mensagem);
    }

    public void receberMensagem(String mensagem) {
        System.out.println("Processo " + id + " recebeu mensagem: " + mensagem);
    }

    public int getId() {
        return id;
    }
}
