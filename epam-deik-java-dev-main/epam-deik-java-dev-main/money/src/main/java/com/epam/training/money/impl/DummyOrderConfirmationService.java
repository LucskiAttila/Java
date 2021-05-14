package com.epam.training.money.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springFramework.stereotype.Service;
import org.springFramework.beans.factory.annotation.Autowired;

// bean neve : dumpyOrderConfirmationService
@Service
public class DummyOrderConfirmationService implements OrderConfirmationService{

    private final static Logger LOGGER = LoggerFactory.getLogger(DummyOrderConfirmationService.class);

    @Autowired
    public DummyOrderConfirmationService(/*@Qualifier("vatProvidingBasketDeclarator")*/ Observable observable) {
        observable.subscribe(this);
    }

    @Override
    public void sendOrderConfirmation(Basket basket) {
        LOGGER.info("And order confirmation for basket {} has been sent.", basket.toString());
    }

    @Override
    public void notify(Basket basket) {
        sendOrderConfirmation(basket);
    }
}
