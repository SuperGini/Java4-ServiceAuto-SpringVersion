package com.gini.iordache.exceptionhandlers.user;

import com.gini.errors.user.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExists.class)
    public String userAlreadyExists(UserAlreadyExists e){
        e.printStackTrace();
        return "redirect:/createUserPage?exists";
    }

    @ExceptionHandler(AccountAlreadyActiveException.class)
    public String accountAlreadyActive(AccountAlreadyActiveException e) {
        e.printStackTrace();
        return "redirect:/account?active";
    }


    @ExceptionHandler(TokenHasExpiredException.class)
    public String processTokenHasExpired(TokenHasExpiredException e) {
        e.printStackTrace();
        return "redirect:/token";
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public String userNotFound(UsernameNotFoundException e) {
        e.printStackTrace();
        return "redirect:/login?error";
    }


    @ExceptionHandler(BadCredentialsException.class)
    public String passwordNotValid(BadCredentialsException e) {
        e.printStackTrace();
        return "redirect:/login?error";
    }


    @ExceptionHandler(AccountIsNotActiveException.class)
    public String accountIsNotActive(AccountIsNotActiveException e){
        e.printStackTrace();
        return "redirect:/token";
    }

    @ExceptionHandler(EmailIsNotRegisteredException.class)
    public String emailIsNotRegistered(EmailIsNotRegisteredException e){
        e.printStackTrace();
        return "redirect:/token?error";
    }


    @ExceptionHandler(InvalidTokenException.class)
    public String invalidToken(InvalidTokenException e){
        e.printStackTrace();
        return "redirect:/account?invalidToken";
    }


}
