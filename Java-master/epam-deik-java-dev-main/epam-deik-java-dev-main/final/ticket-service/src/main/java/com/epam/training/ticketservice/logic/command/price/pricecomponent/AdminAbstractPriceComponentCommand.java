package com.epam.training.ticketservice.logic.command.price.pricecomponent;


import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.logic.command.AdminAbstract;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AdminAbstractPriceComponentCommand extends AdminAbstract {

    @Autowired
    PriceComponentRepository priceComponentRepository;

    @Autowired
    String name;

    @Autowired
    String price;

    private String bad_integer;

    private String type = "component price";

    public boolean isValid() {
        String bad_integer = validConvertToInt(price);
        return emptyString.equals(bad_integer);
    }

    public String getBad_integer() {
        return bad_integer;
    }

    public void delete() {
        priceComponentRepository.delete(priceComponentRepository.findByName(name));
    }

    public void save() {
        priceComponentRepository.save(new PriceComponent(name ,convertToInt(price)));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected String modifiedProperties() {
        return PriceComponentProperties.price_component.toString();
    }
}
