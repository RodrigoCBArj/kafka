package br.com.rodrigocbarj;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        var service = new KafkaService(EmailService.class.getSimpleName(),
                            "ECOMMERCE_SEND_EMAIL",
                            emailService::parse);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
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
