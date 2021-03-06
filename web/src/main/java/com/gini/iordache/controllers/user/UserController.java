package com.gini.iordache.controllers.user;


import com.gini.errors.user.UserAlreadyExists;
import com.gini.iordache.entity.user.Authorities;
import com.gini.iordache.entity.user.User;
import com.gini.iordache.services.interfaces.UserService;

import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;


@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login-user";
    }


    @PostMapping("/login-processing")
    public String loginProcessing() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {

            return "redirect:/login";
        }

        return "redirect:/app/main";
    }


    @GetMapping("/createUserPage")
    public String showCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("authority", Authorities.values());
        return "user/create-user";
    }


    @PostMapping("/create-user")
    public String createUser(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("authority", Authorities.values());
            return "user/create-user";
        }

        try {
            userService.createUser(user).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new UserAlreadyExists("User already exists");
        }

        return "redirect:/login";
    }

    @GetMapping("/activate")
    public String activateAccount() {
        return "activation/confirmAccount-activation";
    }

}


