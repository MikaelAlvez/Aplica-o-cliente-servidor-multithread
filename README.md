# Aplicacao cliente servidor multithread
Aplicação cliente/servidor multithread, com TCP, para simular a comunicação de processos em topologia em anel e em topologia estrela, considerando 4 processos: P1, P2, P3 e P4. 
As mensagens podem ser enviadas por qualquer processo. Suporta duas operações: Unicast: emissor enviar mensagem a um receptor específico. Broadcast: emissor envia para todos os receptores ativos.

- Topologia Anel: 
P1 deve estar conectado a P2 e P4;
P2 deve estar conectado a P1 e P3;
P3 deve estar conectado a P2 e P4;
P4 deve estar conectado a P3 e P1.

- Topologia Estrela:
O elemento central dessa aplicação é o processo P1.
P2, P3 e P4 devem estar conectados a P1.
Todas as mensagens entre os processos P2, P3 e P4 devem passar por P1.
