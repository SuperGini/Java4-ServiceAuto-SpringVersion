package com.gini.iordache.dao.impl.labor;

import com.gini.iordache.entity.labor.LaborPrice;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@AllArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class LaborPriceDaoImpl implements com.gini.iordache.dao.LaborPriceDao {

    private final EntityManager entityManager;

    @Override
    public void createLaborPrices(LaborPrice laborPrice){
        entityManager.persist(laborPrice);

    }

    @Override
    public int updateMechanicalLaborPrice(double mechanicalLaborPrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.mechanicalLaborPrice =: mechanicalLaborPrice WHERE l.id =: id ";

        return entityManager.createQuery(jpql)
                                .setParameter("mechanicalLaborPrice", mechanicalLaborPrice)
                                .setParameter("id", id)
                                .executeUpdate();
    }


    @Override
    public int updateElectricalLaborPrice(double electricalLaborPrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.electricalLaborPrice =: electricalLaborPrice WHERE l.id =: id";

        return entityManager.createQuery(jpql)
                                .setParameter("electricalLaborPrice", electricalLaborPrice)
                                .setParameter("id", id)
                                .executeUpdate();
    }


    @Override
    public int updateNormalLaborPrice(double normalLaborPrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.normalLaborPrice =: normalLaborPrice WHERE l.id = :id ";

        return entityManager.createQuery(jpql)
                                .setParameter("normalLaborPrice", normalLaborPrice)
                                .setParameter("id", id)
                                .executeUpdate();
    }


    @Override
    public int updateItpDieselEnginePrice(double itpDieselEnginePrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.itpDieselEnginePrice =: itpDieselEnginePrice WHERE l.id =: id";

        return entityManager.createQuery(jpql)
                                .setParameter("itpDieselEnginePrice", itpDieselEnginePrice)
                                .setParameter("id", id)
                                .executeUpdate();
    }


    @Override
    public int updateItpGasolineEnginePrice(double itpGasolineEnginePrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.itpGasolineEnginePrice =: itpGasolineEnginePrice WHERE l.id = :id ";

        return entityManager.createQuery(jpql)
                                .setParameter("itpGasolineEnginePrice", itpGasolineEnginePrice)
                                .setParameter("id", id)
                                .executeUpdate();
    }


    @Override
    public int updateItpSuvPrice(double itpSuvPrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.itpSuvPrice =: itpSuvPrice WHERE l.id =: id ";

        return entityManager.createQuery(jpql)
                                .setParameter("itpSuvPrice", itpSuvPrice)
                                .setParameter("id", id)
                                .executeUpdate();
    }


    @Override
    public int updateItpTruckPrice(double itpTruckPrice, int id){

        String jpql = "UPDATE LaborPrice l SET l.itpTruckPrice =: itpTruckPrice WHERE l.id =: id";

        return entityManager.createQuery(jpql)
                                .setParameter("itpTruckPrice", itpTruckPrice)
                                .setParameter("id", id)
                                .executeUpdate();

    }

}