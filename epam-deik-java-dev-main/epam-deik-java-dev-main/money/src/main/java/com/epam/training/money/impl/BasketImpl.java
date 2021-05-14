package com.epam.training.money.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springFramework.beans.factory.annotation.Autowired;

@Component
//@Scope("portotype")
public class BasketImpl implements Basket{

    private List<Obserer> observers;

    private List<Product> products;
    private List<Coupon> coupons;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public BasketImpl(/*OrderRepository orderRepository*/) {
        products = new ArrayList<>();
        coupons = new ArrayList<>();
        observers = new LinkedList<>();
//        this.orderRepository = orderRepository;
    }

//    @Autowired
//    public void setOrderRepository(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    @PostConstruct
    public void afterInit() {
        //orderRepository //...
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getProductsFromBasket() {
        return products;
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromBasket() {
        return coupons;
    }

    @Override
    public double getTotalValue() {
        double basePrice = getBasePrice();
        double discount = getDiscountForCoupons();
        return basePrice - discount;
    }

    @Override
    public double getBasePrice() {
        double valueOfBasket = 0;
//        for(Product currentProduct : products) {
//            valueOfBasket += currentProduct.getValue();
//        }
        return products.stream()
                .map(Product::getValue)
                .reduce(0.0, Double::sum);
//        return valueOfBasket;
    }

    @Override
    public double getDiscountForCoupons() {
        return 0;
    }

    @Override
    public void order() {
        orderRepository.saveOrder(this);
        observers.forEach(observer -> observer.notify(this));
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
}
