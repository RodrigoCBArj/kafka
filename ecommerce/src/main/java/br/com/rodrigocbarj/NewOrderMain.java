package br.com.rodrigocbarj;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        var key = "155558912";
        var value = "3268,109.90";
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);
        producer.send(record, (data, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            System.out.println("MENSAGEM ENVIADA COM SUCESSO PARA: " + data.topic() +
                    "\npartição: " + data.partition() +
                    "\noffset: " + data.offset() +
                    "\ntimestamp: " + data.timestamp());
        }).get();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}