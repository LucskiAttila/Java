package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BasePrice {

    @Id
    int basePriceValue;

    public BasePrice(int basePriceValue) {
        this.basePriceValue = basePriceValue;
    }

    public BasePrice() {

    }

    public int getBasePriceValue() {
        return basePriceValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasePrice basePrice = (BasePrice) o;
        return basePriceValue == basePrice.basePriceValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(basePriceValue);
    }
}
