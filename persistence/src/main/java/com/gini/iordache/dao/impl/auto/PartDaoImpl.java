package com.gini.iordache.dao.impl.auto;

import com.gini.iordache.dao.iterfaces.PartDao;
import com.gini.iordache.entity.auto.Part;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@AllArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public class PartDaoImpl implements PartDao {

    private final EntityManager entityManager;

    @Override
    public void createPart(Part part) {
        entityManager.persist(part);
    }

    @Override
    public Optional<Part> findPartByName(String partName){

        String jpql = "SELECT p FROM Part p WHERE p.partName =: partName";

        return entityManager.createQuery(jpql, Part.class)
                            .setParameter("partName", partName)
                            .getResultStream()
                            .findFirst();
    }


    @Override
    public Optional<Part> findPartByPartNumber(String partNumber){

        String jpql = "SELECT p FROM Part p WHERE p.partNumber =: partNumber";

        return entityManager.createQuery(jpql, Part.class)
                            .setParameter("partNumber", partNumber)
                            .getResultStream()
                            .findFirst();

    }


    @Override
    public int updatePartCount(int increment, String partNumber){

        String jpql = "UPDATE Part p SET p.count = p.count + :increment WHERE p.partNumber =: partNumber";

        return entityManager.createQuery(jpql)
                            .setParameter("increment", increment)
                            .setParameter("partNumber", partNumber)
                            .executeUpdate();
    }

    @Override
    public int updatePartCountAndPrice(int increment, double price, String partNumber){

        String jpql = "UPDATE Part p SET p.count = p.count + :increment, p.price =: price WHERE p.partNumber =: partNumber";

        return entityManager.createQuery(jpql)
                            .setParameter("increment", increment)
                            .setParameter("partNumber", partNumber)
                            .setParameter("price", price)
                            .executeUpdate();

    }


    @Override
    public int decreasePartCount(int decrement, String partNumber){

        String jpql = "UPDATE Part p SET p.count = p.count - :decrement WHERE p.partNumber =: partNumber";

        return entityManager.createQuery(jpql)
                            .setParameter("decrement", decrement)
                            .setParameter("partNumber", partNumber)
                            .executeUpdate();
    }
}
