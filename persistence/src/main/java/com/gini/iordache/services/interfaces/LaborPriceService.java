package com.gini.iordache.services.interfaces;

import com.gini.iordache.entity.labor.LaborPrice;
import org.springframework.transaction.annotation.Transactional;



public interface LaborPriceService {



    @Transactional
    void findLaborPrices();

    @Transactional
    LaborPrice findAllPrices();

    @Transactional
    void createAllLaborPrices(LaborPrice laborPrice);

    @Transactional
    void updatePrices(double newPrice, String laborCategory);

}
