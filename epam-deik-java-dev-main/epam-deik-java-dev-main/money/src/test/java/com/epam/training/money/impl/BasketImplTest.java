package com.epam.training.money.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mocikito.Mockito;
//import org.mocikito.Mock;
//import org.mocikito.MockitoAnnotations;
import static org.mockito.BDDMockito.given;

public class BasketImplTest {

    private static final double PRODUCT_VALUE = 12.5;
//    @Mock
//    private Product productLnBasket;

//    @BeforeEach
//    private void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void testAddProductShouldAddASingleProductToTheBasket() {
        // Given
        BasketImpl undertest = new BasketImpl();
        Product productToAdd = Mockito.mock(Product.class);
        // When
        undertest.addProduct(productToAdd);
        // Then
        assertThat(undertest.getProductsFromBasket(), equalTo(List.of(productToAdd)));
    }

    @Test
    public void testRemoveProductShouldRemoveSingleProductFromTheBasket() {
        // Given
        BasketImpl undertest = new BasketImpl();
        Product productToRemove = Mockito.mock(Product.class);
        undertest.addProduct(productToRemove);

        // When
        undertest.removeProduct(productToRemove);

        //Then
        assertThat(undertest.getProductsFromBasket(productToRemove), equalTo(Collections.emptyList()))
    }

    @Test
    public void testAddCouponShouldAddSingleCouponToTheBasket() {
        // Given
        BasketImpl undertest = new BasketImpl();
        Coupon couponToAdd = Mockito.mock(Coupon.class);
        
        // When
        undertest.addCoupon(couponToAdd);
        
        // Then
        assertThat(undertest.getCouponsFromBasket(), equalTo(List.of(couponToAdd)))
    }

    @Test
    public void testgetValuesFromBasketShouldReturnBasketValueWhenASingleProductIsInTheBasket() {
        // Given
        BasketImpl undertest = new BasketImpl();
        Product product = Mockito.mock(Product.class);
        undertest.addProduct(product);
        given(product.getValue().willReturn(PRODUCT_VALUE));

        // When
        double result = undertest.getValuesFromBasket();

        // Then
        assertThat(result, equalTo(PRODUCT_VALUE));
    }
}
