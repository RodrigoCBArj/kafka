# Estudos e projetos usando o KAFKA

Esse repositório é destinado a estudos e projetos usando a ferramenta de mensageria Apache Kafka.

---
## Baixar o Kafka

https://kafka.apache.org/downloads

## Configurações necessárias para rodar o Kafka no Windows

https://www.geeksforgeeks.org/how-to-install-and-run-apache-kafka-on-windows/

## Startar o Kafka no Windows

1. Primeiro, devemos estar na pasta raiz do Kafka no terminal.

        cd c:/kafka_2.13-3.3.1

2. Antes devemos startar o Zookeeper:

        ./bin/windows/zookeeper-server-start.bat ./config/zookeeper.properties

3. Agora startamos o Kafka:

        ./bin/windows/kafka-server-start.bat ./config/server.properties

## Alterar a quantidade de partições de um canal

    ./bin/windows/kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3

Para verificar as partições dos canais:

        ./bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --describe

---

### Kafka no Docker

https://cursos.alura.com.br/forum/topico-como-instalar-e-usar-o-kafka-no-windows-facil-195183

### Kafka no WSL 2
**TO-DO**