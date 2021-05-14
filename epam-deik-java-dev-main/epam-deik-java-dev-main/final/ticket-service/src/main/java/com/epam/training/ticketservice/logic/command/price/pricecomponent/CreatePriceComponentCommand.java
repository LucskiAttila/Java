package com.epam.training.ticketservice.logic.command.price.pricecomponent;

public class CreatePriceComponentCommand extends AdminAbstractPriceComponentCommand{

    private final String name;
    private final String price;


    public CreatePriceComponentCommand(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    protected String operate() {
        if (isValid()) {
            update();
            return updateTrue();
        } else {
            return invalidUpdate(getBad_integer());
        }
    }
}
