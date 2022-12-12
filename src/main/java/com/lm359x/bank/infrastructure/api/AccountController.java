package com.lm359x.bank.infrastructure.api;


import com.lm359x.bank.entity.Account;
import com.lm359x.bank.infrastructure.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping()
    public List<Account> getAllAccounts(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("accounts",accountService.showAccounts());
        return accountService.showAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id,Model model){

        return accountService.getUserAccountById(id);
    }

    @GetMapping("/{id}/block")
    public Account blockAccount(@PathVariable Long id){

        return accountService.blockAccount(id);
    }




}
