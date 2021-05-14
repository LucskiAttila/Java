package com.epam.training.money.impl;

import org.springFramework.stereotype.Component;

//@Component
public class EmailConfirmationServiceAdapter extends EmailConfirmationService implements OrderConfirmationService{

//    public EmailConfirmationServiceAdapter(Observable basket) {
//        basket.subscribe(this);
//    }

    @Override
    public void sendOrderConfirmation(Basket basket) {
        this.sendConfirmationMessageAbout(basket.getProductsFromBasket());
    }

    @Override
    public void notify(Basket basket) {
        sendOrderConfirmation(basket);
    }
}
