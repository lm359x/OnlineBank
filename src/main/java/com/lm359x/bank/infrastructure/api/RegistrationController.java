package com.lm359x.bank.infrastructure.api;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.entity.Role;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.infrastructure.service.AccountService;
import com.lm359x.bank.infrastructure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, AccountService accountService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        User userFromDB = userService.getUser(user.getUsername());

        if (userFromDB != null) {
            model.addAttribute("error", "Пользователь уже существует! Попробуйте другое имя.");
            return "registration";
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setAccountList(new ArrayList<>());
        userService.save(user);

        return "redirect:/login";
    }
}