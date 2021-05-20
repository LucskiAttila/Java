package com.epam.training.ticketservice.logic.command.price.baseprice;

import com.epam.training.ticketservice.database.entity.BasePrice;
import com.epam.training.ticketservice.database.repository.BasePriceRepository;
import com.epam.training.ticketservice.logic.command.AdminAbstract;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AdminAbstractBasePriceCommand extends AdminAbstract {

    @Autowired
    BasePriceRepository basePriceRepository;

    @Autowired
    String base_price;

    private String bad_integer;

    private String type = "base price";

    public boolean isValid() {
        String bad_integer = validConvertToInt(base_price);
        return emptyString.equals(bad_integer);
    }

    public String getBad_integer() {
        return bad_integer;
    }

    public void delete() {
        basePriceRepository.deleteAll();
    }

    public void save() {
        basePriceRepository.save(new BasePrice(convertToInt(base_price)));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected String modifiedProperties() {
        return BasePriceProperties.base_price.toString();
    }
}
