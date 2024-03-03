package TopologiaEstrela;

public class P1 {
    public static void main(String[] args) {
        ServidorCentral servidorCentral = new ServidorCentral(4096);
        Thread threadServidor = new Thread(threadServidor);
        threadServidor.start();
        
        Cliente clienteP1 = new Cliente("P1", 4096);
        Thread threadCliente = new Thread(threadCliente);
        threadCliente.start();
    }
}
