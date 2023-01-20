package br.com.rodrigocbarj;

import br.com.rodrigocbarj.entity.Email;
import br.com.rodrigocbarj.entity.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {

                for (int i = 0; i < 5; i++) { // teste - enviar 5 compras
                    var orderId = UUID.randomUUID().toString();
                    var userId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    var order = new Order(orderId, userId, amount);

                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", orderId, order);

                    Email email = new Email("Compra efetuada!", "Obrigado por seu pedido! Estamos processando sua compra.");
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", orderId, email);
                }
            }
        }
    }
}
