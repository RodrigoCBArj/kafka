package br.com.rodrigocbarj;

import br.com.rodrigocbarj.entity.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {

    public static void main(String[] args) {
        FraudDetectorService fraudDetectorService = new FraudDetectorService();
        try (var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudDetectorService::parse,
                Order.class,
                Map.of())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        // "abstração da validação do pedido"
        System.out.println("##### Validando possível fraude no pedido: #####");
        System.out.println("key: " + record.key() +
                "\nvalue: " + record.value() +
                "\nparticao: " + record.partition() +
                "\noffset: " + record.offset());
        System.out.println("...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Pedido validado!");
    }
}
