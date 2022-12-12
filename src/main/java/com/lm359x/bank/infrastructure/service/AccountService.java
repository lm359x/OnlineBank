package com.lm359x.bank.infrastructure.service;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.infrastructure.jpa.AccountRepository;
import com.lm359x.bank.use_case.account.CreateAccountUseCase;
import com.lm359x.bank.use_case.account.GetAllAccountsByUserUseCase;
import com.lm359x.bank.use_case.account.ValidateAccountCreationUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CreateAccountUseCase createAccountUC;
    private final GetAllAccountsByUserUseCase getAllAccountsUC;

    private final ValidateAccountCreationUseCase validateAccountUC;

    private final UserService userService;
    //subtitute userService to UC's????

    public AccountService(AccountRepository accountRepository, CreateAccountUseCase createAccountUC, GetAllAccountsByUserUseCase getAllAccountsUC, ValidateAccountCreationUseCase validateAccountUC, UserService userService) {
        this.accountRepository = accountRepository;
        this.createAccountUC = createAccountUC;
        this.getAllAccountsUC = getAllAccountsUC;
        this.validateAccountUC = validateAccountUC;
        this.userService = userService;
    }
    public List<Account> showAccounts(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Account> accounts = getAllAccountsUC.execute(new GetAllAccountsByUserUseCase
                .InputValues(accountRepository.findAll(),
                userService.showUser(auth.getName())))
                .getAccounts();
        return accounts;
    }

    public Account createAccount(String accountType){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = createAccountUC.execute(new CreateAccountUseCase.InputValues(
                userService.showUser(auth.getName()),AccountType.valueOf(accountType)
        )).getAccount();
        //validate

        //save in DB

        return account;
    }
}
