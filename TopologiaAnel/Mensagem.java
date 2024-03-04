package TopologiaAnel;

import java.io.Serializable;

public class Mensagem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String remetente, destinatario, mensagem, operacao;
	
	public Mensagem(String remetente, String destinatario, String mensagem, String operacao) {
		this.remetente = remetente;
		this.destinatario = destinatario;
		this.mensagem = mensagem;
		this.operacao = operacao;
	}
	
	public String toString() {
		return " de " + this.remetente + ": " + this.mensagem;
	}
	
	public String getRemetente() {
		return this.remetente;
	}
	
	public void setRemetente() {
		this.remetente = remetente;
	}
	
	public String getDestinatario() {
		return this.destinatario;
	}
	
	public void setDestinatario() {
		this.destinatario = destinatario;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	public void setMensagem() {
		this.mensagem = mensagem;
	}
	
	public String getOperacao() {
		return this.operacao;
	}
	
	public void setOperacao() {
		this.operacao = operacao;
	}
}