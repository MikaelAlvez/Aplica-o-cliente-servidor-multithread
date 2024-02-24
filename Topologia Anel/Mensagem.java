package PraticaOnline1;

import java.io.Serializable;

public class Mensagem implements Serializable {
    private int remetente;
    private int destino;
    private String conteudo;
    
    public Mensagem(int remetente, int destino, String conteudo) {
        this.remetente = remetente;
        this.destino = destino;
        this.conteudo = conteudo;
    }
    
    public int getRemetente() {
        return remetente;
    }
    
    public int getDestino() {
        return destino;
    }
    
    public String getConteudo() {
        return conteudo;
    }
}