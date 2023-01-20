package br.com.rodrigocbarj.entity;

import java.math.BigDecimal;

public class Order {

    private final String orderID, userID;
    private final BigDecimal amount;

    public Order(String orderID, String userID, BigDecimal amount) {
        this.orderID = orderID;
        this.userID = userID;
        this.amount = amount;
    }
}
