package com.epam.training.money.impl.presentation.cli.configuration;

import org.springFramework.context.annotation.Configuration;
import org.springFramework.context.annotation.Bean;
import org.springFramework.context.annotation.Lazy;
import org.springFramework.context.annotation.ComponentScan;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

@Configuration
//@ComponentScan("com.epam.training.money.impl")
//@Import(ConfirmationConfiguration.class)
public class ApplicationConfiguration {

//    @Bean
//    public CommandParser commandLineParser(/*@Lazy*/ CliInterpreter cliInterpreter, ProductRepository productRepository, Basket basket) {
//        ExitCommandParser commandParser = new ExitCommandParser(cliInterpreter);
//        commandParser.setSuccessor(new AddProductCommandParser(basket, productRepository));
//        commandParser.setSuccessor(new OrderCommandParser(basket));
//        return commandParser;
//    }

//    @Bean
//    public Reader input() {
//        return new InputStreamReader(System.in);
//    }

//    @Bean
//    public Writer output() {
//        return new OutputStreamWriter(System.out);
//    }

//    @Bean
//    public Basket basket(OrderRepostiroy orderRepostiroy) {
//        return new BasketImpl(orderRepostiroy);
//    }

//    @Bean
//    public OrderRepository orderRepository() {
//        return new DummyOrderRepository();
//    }

//    @Bean
//    public Warehouse warehouse(Basket basket) {
//         DummyWarehouse warehouse = new DummyWarehouse();
//         basket.subscribe(warehouse);
//         return warehouse;
//    }

//    @Bean
//    public OrderConfirmationService orderConfirmationService(Basket basket) {
//        OrderConfirmationService orderConfirmationService = new DummyOrderConfirmationService();
//        basket.subscribe(orderConfirmationService);
//        return orderConfirmationService;
//    }
//}
