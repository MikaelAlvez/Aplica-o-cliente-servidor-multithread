package TopologiaEstrela;

import java.io.Serializable;

public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String emissor;
    private String destinatario;
    private String conteudo;
    private String tipo;

    public Mensagem(String emissor, String destinatario, String conteudo, String tipo) {
        this.emissor = emissor;
        this.destinatario = destinatario;
        this.conteudo = conteudo;
        this.tipo = tipo;
    }

    public String getEmissor() {
        return emissor;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Mensagem de " + emissor + ": " + conteudo;
    }
}
