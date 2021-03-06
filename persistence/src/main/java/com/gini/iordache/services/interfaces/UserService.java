package com.gini.iordache.services.interfaces;

import com.gini.iordache.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.Future;

public interface UserService {

    Future<User> createUser(User user);

  //  int enableUserAccount(String username, String token);


    void updateUserToken(User user);


    Optional<User> findUserWithToken(String email);


    int activateUserAccount(User user);

    @Transactional
    User findUseByUsername(String username);
}
