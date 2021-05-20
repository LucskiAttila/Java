package com.epam.training.money.impl;

import org.springFramework.stereotype.Repository;

@Repository
public class DummyProductRepository implements ProductRepository{

    @Override
    public List<Product> getAllProduct() {
        return List.of{
            new SingleProduct("Alma", 42),
            new SingleProduct("Pálinka", 568),
            new SinglePorduct("Táncmulatság", 220);
        };
    }
}
