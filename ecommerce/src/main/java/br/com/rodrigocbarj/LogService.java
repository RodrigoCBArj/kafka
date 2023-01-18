package br.com.rodrigocbarj;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        var logService = new LogService();
        try (var consumer = new KafkaService(LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
             logService::parse)) {
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record){
        // "envio de email"
        System.out.println("##### LOG DA MENSAGEM: #####");
        System.out.println(record.topic() +
                "\nkey: " + record.key() +
                "\nvalue: " + record.value() +
                "\nparticao: " + record.partition() +
                " | offset: " + record.offset());
    }
}
