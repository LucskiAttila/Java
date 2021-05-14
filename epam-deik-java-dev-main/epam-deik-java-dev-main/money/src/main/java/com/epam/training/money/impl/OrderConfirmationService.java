package com.epam.training.money.impl;

public interface OrderConfirmationService extends Observer {
    void sendOrderConfirmation(Basket basket);
}
