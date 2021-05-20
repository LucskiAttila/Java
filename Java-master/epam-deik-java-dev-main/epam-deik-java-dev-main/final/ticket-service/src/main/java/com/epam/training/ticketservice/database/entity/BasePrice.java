package com.epam.training.ticketservice.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class BasePrice {

    @Id
    int base_price;

    public BasePrice(int base_price) {
        this.base_price = base_price;
    }

    public BasePrice() {

    }

    public int getBase_price() {
        return base_price;
    }

    public void setBase_price(int base_price) {
        this.base_price = base_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasePrice basePrice = (BasePrice) o;
        return base_price == basePrice.base_price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(base_price);
    }
}
