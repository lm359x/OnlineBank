package com.lm359x.bank.infrastructure.api;

import com.lm359x.bank.infrastructure.service.AccountService;
import com.lm359x.bank.infrastructure.service.OperationService;
import com.lm359x.bank.infrastructure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
   private final UserService userService;
    private final OperationService operationService;
    private final AccountService accountService;
    @Autowired
    public MainController(UserService userService, OperationService operationService, AccountService accountService) {
        this.userService = userService;
        this.operationService = operationService;
        this.accountService = accountService;
    }

//    @GetMapping("login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/")
    public String rofl(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.showUser(auth.getName()));
        model.addAttribute("accounts",accountService.showAccounts());
        model.addAttribute("operations",operationService.showOperations());
        return "rofl";
    }



}
