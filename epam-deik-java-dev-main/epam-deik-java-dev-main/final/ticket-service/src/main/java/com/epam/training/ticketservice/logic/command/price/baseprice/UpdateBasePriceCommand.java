package com.epam.training.ticketservice.logic.command.price.baseprice;

public class UpdateBasePriceCommand extends AdminAbstractBasePriceCommand {

    private final String base_price;

    public UpdateBasePriceCommand(String base_price) {
        this.base_price = base_price;
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
