package com.epam.training.money.impl;

import java.util.List;

public interface ConfirmationService {
    void sendConfirmationMessageAbout(List<Product> products);
}
