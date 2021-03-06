package com.gini.iordache.dao.impl.user;

import com.gini.iordache.dao.iterfaces.TokenDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@AllArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public class TokenDaoImpl implements TokenDao {

    private final EntityManager entityManager;

    @Override
    public int updateToken(int userId, String token){

        String jpql = "UPDATE ActivationToken a SET a.token =: token, a.createdAt =: createdAt, a.expiredAt =: expiredAt WHERE a.user.id =: userId";

        return entityManager.createQuery(jpql)
                            .setParameter("token", token)
                            .setParameter("createdAt", LocalDateTime.now())
                            .setParameter("expiredAt", LocalDateTime.now().plusMinutes(2))
                            .setParameter("userId", userId)
                            .executeUpdate();
    }
}
