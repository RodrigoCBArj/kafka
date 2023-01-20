package br.com.rodrigocbarj;

import br.com.rodrigocbarj.entity.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {

    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        try (var service = new KafkaService<>(EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse,
                Email.class,
                Map.of())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record) {
        // "abstração do envio de email"
        System.out.println("##### Enviando email referente ao pedido: #####");
        System.out.println("key: " + record.key() +
                "\nvalue: " + record.value() +
                "\nparticao: " + record.partition() +
                " | offset: " + record.offset());
        System.out.println("...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Email enviado!");
    }
}
