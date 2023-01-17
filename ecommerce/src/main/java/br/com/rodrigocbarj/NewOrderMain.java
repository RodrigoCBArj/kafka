package br.com.rodrigocbarj;

import br.com.rodrigocbarj.entity.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
                var orderId = UUID.randomUUID().toString();
                var userId = UUID.randomUUID().toString();
                var amount = new BigDecimal(Math.random() * 5000 + 1);
                var order = new Order(orderId, userId, amount);

                orderDispatcher.send("ECOMMERCE_NEW_ORDER", orderId, order);

            try (var emailDispatcher = new KafkaDispatcher<String>()) {
                var email = "Obrigado por seu pedido! Estamos processando sua compra.";
                emailDispatcher.send("ECOMMERCE_SEND_EMAIL", orderId, email);
            }
        }
    }
}
