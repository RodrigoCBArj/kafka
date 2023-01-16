package br.com.rodrigocbarj;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        try (var dispatcher = new KafkaDispatcher()) {
            var key = UUID.randomUUID().toString();
            var value = "3268,109.90";

            dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

            var email = "Obrigado por seu pedido! Estamos processando sua compra.";
            dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
        }
    }
}
