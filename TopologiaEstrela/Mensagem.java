package TopologiaEstrela;

import java.io.Serializable;

public class Mensagem implements Serializable {
    private String remetente;
    private String destinatario;
    private String mensagem;
    private String operacao;

    public Mensagem(String remetente, String destinatario, String mensagem, String operacao) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.operacao = operacao;
    }

    public String toString() {
        return "Mensagem enviada de " + remetente + " para " + destinatario + ": " + mensagem;
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getOperacao() {
        return operacao;
    }
}
