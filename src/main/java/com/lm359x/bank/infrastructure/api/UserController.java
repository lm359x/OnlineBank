package com.lm359x.bank.infrastructure.api;

import com.lm359x.bank.entity.User;
import com.lm359x.bank.infrastructure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    final
    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String getUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(service.showUser(auth.getName()));
        model.addAttribute("userprofile",service.showUser(auth.getName()));
        return "userprofile";
    }



}
