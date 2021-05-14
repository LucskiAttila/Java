package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.*;

public class CliConfiguration {
    public static CliInterpreter cliInterpreter() {

        Basket basket = basket();
        CliInterpreter interpreter = new CliInterpreter(cliReader(), cliWriter());
        ExitCommandParser commandParser = new ExitCommandParser(interpreter);
        commandParser.setSuccessor(new AddProductCommandParser(basket, productRepository()));
        commandParser.setSuccessor(new OrderCommandParser(basket));
        interpreter.setCommandParser(commandParser);
        return interpreter;

        public static Writer cliWriter() {
            new OutputStreamWriter(System.out);
        }

        public static Reader cliReader() {
            new InputStreamReader(System.in));
        }

        public static Basket basket() {
            BasketImpl basket = BasketImpl(orderRepository);
            basket.subscribe(warehouse);
            basket.subscribe(orderConfirmationService);
            return basket;
        }

        public static ProductRepository productRepository() {
            return new DummyProductRepository();
        }

        public static OrderRepository orderRepository() {
            return new DummyOrderRepository();
        }

        public static Warehouse warehouse() {
            return new DummyWarehouse();

        }
        public static OrderConfirmationService orderConfirmationService() {
            return new DummyOrderConfirmationService();
        }
    }
}
