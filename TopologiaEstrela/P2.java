package TopologiaEstrela;

public class P2 {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("localhost", 4096);
        cliente.enviarMensagem(new Mensagem("P2", "P1", "Olá, P1!", "unicast"));
        Mensagem resposta = cliente.receberMensagem();
        System.out.println("Resposta de P1: " + resposta.getConteudo());
        cliente.fecharConexao();
    }
}
