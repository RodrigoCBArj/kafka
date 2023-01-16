package br.com.rodrigocbarj;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Pattern.compile("ECOMMERCE.*"));

        while (true) {
            var records = consumer.poll(Duration.ofMillis(2000));

            if (!records.isEmpty()) {
                for (var record : records) {
                    // "envio de email"
                    System.out.println("##### LOG DA MENSAGEM: #####");
                    System.out.println(record.topic() +
                            "\nkey: " + record.key() +
                            "\nvalue: " + record.value() +
                            "\nparticao: " + record.partition() +
                            " | offset: " + record.offset());
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());
        return properties;
    }
}
