package com.gini.iordache.dao.iterfaces;

import com.gini.iordache.entity.order.PartOrder;
import com.gini.iordache.entity.order.ServiceOrder;

import java.util.Optional;

public interface PartOrderDao {
    void createPartOrder(PartOrder partServiceOrder);

    Optional<PartOrder> findPartOrderByPartName(String partNumber, ServiceOrder serviceOrder);

    int deletePartFromServiceOrder(String partNumber);

    int updatePartOrderCount(int id, int increment);
}
