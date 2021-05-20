package com.epam.training.money.impl.command;

import com.epam.training.money.impl.Basket;
import com.epam.training.money.impl.ProductRepository;

public class AddProductCommand implements Command {

    private final ProductRepository repository;
    private final Basket basket;
    private final String productName;

    public AddProductCommand(String productName, Basket basket, ProductRepository repository) {
        this.productName = productName;
        this.basket = basket;
        this.repository = repository;
    }

    @Override
    public String execute() {
        Optional<Product> productToAdd = repository.getAllProduct().stream()
                .filter(product -> product.getName.equals(productName))
                .findFirst();
        if (productToAdd.isEmpty()) {
            return "Can not find requested product.";
        }
        productToAdd.ifPresent(basket::addProduct);
        return "Ok.";
    }
}
