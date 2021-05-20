package com.epam.training.money.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springFramework.stereotype.Component;
import org.springFramework.beans.factory.annotation.Autowired;

@Component
public class DummyWarehouse implements Warehouse{

    private final static Logger LOGGER = LoggerFactory.getLogger(DummyWarehouse.class);

    @Autowired
    public void subscribeOnObservable(Observable observable) {
        observable.subscribe(this);
    }

    @Override
    public void registerOrderedProducts(List<Product> products) {
        LOGGER.info("I have registered the order of products {}", products);
    }

    @Override
    public void notify(Basket basket) {
        registerOrderedProducts(basket.getProductsFromBasket());+
    }
}
