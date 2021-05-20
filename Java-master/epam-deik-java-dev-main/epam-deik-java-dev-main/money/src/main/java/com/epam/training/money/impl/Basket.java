package com.epam.training.money.impl;

import java.util.List;

/*
* Background:
*
* At the moment, we only allow ordering product via e-mail.
* This ticket is to introduce a shopping basket functionality
* to make shopping more simple for our customers as well as to
* enable having dicsounts.
*
* Accoetance criteria:
*
*It is possible to:
* 1. add products to the basket
* 2. list product in the basket
* 3. remove a single product from the basket
* 4. add coupons to the basket
* 4.1 A coupon should change the total value of the basket with a fixed amount
* 5. list coupons in the basket
* 6. remove coupons from the basket
* 7. query the value of the besket in HUF and USD currencies
* 7.1. this should include the price of the products, cupon discount, and VAT
* 7.2. VAT is 27% of the price of the products without any discount
*/

public interface Basket extends Observable{

    public void addProduct(Product product);
    List<Product> getProductsFromBasket();
    void removeProduct(Product product);
    void addCoupon(Coupon coupon);
    List<Coupon> getCouponsFromBasket();
    void order();
    double getTotalValue();
    double getBasePrice();
    double getDiscountForCoupons();
}
