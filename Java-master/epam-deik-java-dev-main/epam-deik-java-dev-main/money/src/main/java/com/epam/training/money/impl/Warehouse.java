package com.epam.training.money.impl;

import java.util.List;

public interface Warehouse extends Observer{
    void registerOrderedProducts(List<Product> products);
}
