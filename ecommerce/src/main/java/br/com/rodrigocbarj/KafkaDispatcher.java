package br.com.rodrigocbarj;

import br.com.rodrigocbarj.util.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    void send(String topic, String key, T value)
            throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<>(topic, key, value);

        for (int i = 0; i < 5; i++) { // teste - enviar 5 compras
            Callback callback = (data, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                    return;
                }
                System.out.println("MENSAGEM ENVIADA COM SUCESSO PARA: " + data.topic() +
                        "\ntimestamp: " + data.timestamp() +
                        "\npartição: " + data.partition() +
                        " | offset: " + data.offset());
            };
            producer.send(record, callback).get();
        }
    }

    @Override
    public void close() {
        producer.close();
    }
}
