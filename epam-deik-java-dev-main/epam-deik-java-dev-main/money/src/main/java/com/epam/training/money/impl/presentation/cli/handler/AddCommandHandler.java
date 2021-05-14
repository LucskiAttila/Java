package com.epam.training.money.impl.presentation.cli.handler;

import com.epam.training.money.impl.Basket;
import com.epam.training.money.impl.ProductRepository;
import com.epam.training.money.impl.command.AddProductCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.beans.factory.annotation.Autowired;

@ShellComponent
public class AddCommandHandler {

    private Basket basket;
    private ProductRepository productRepository;

    @Autowired
    public AddCommandHandler(Basket basket, ProductRepository productRepository) {
        this.basket = basket;
        this.productRepository = productRepository;
    }

    @ShellMethod(value = "Add a product to the basket", key = "add product")
    public String addProduct(String productName) {
        AddProductCommand command = new AddProductCommand(productName, basket, productRepository);
        return command.execute();
    }
}
