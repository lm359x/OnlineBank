package com.lm359x.bank.infrastructure.service;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.infrastructure.jpa.AccountRepository;
import com.lm359x.bank.use_case.account.CreateAccountUseCase;
import com.lm359x.bank.use_case.account.GetAccountByIdUseCase;
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
    private final GetAccountByIdUseCase getAccUC;

    private final UserService userService;
    //subtitute userService to UC's????

    public AccountService(AccountRepository accountRepository, CreateAccountUseCase createAccountUC, GetAllAccountsByUserUseCase getAllAccountsUC, ValidateAccountCreationUseCase validateAccountUC, GetAccountByIdUseCase getAccUC, UserService userService) {
        this.accountRepository = accountRepository;
        this.createAccountUC = createAccountUC;
        this.getAllAccountsUC = getAllAccountsUC;
        this.validateAccountUC = validateAccountUC;
        this.getAccUC = getAccUC;
        this.userService = userService;
    }

    //R
    public List<Account> showAccounts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Account> accounts = getAllAccountsUC.execute(new GetAllAccountsByUserUseCase
                        .InputValues(accountRepository.findAll(),
                        userService.showUser(auth.getName())))
                .getAccounts();
        return accounts;
    }

    //C
    public Account createAccount(String accountType) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.showUser(auth.getName());
        List<Account> allAccs = accountRepository.findAll();
        List<Account> userAccs = getAllAccountsUC.execute(new GetAllAccountsByUserUseCase.InputValues(allAccs, currentUser)).getAccounts();
        Account account = createAccountUC.execute(new CreateAccountUseCase.InputValues(
                currentUser, AccountType.valueOf(accountType)
        )).getAccount();
        //validate

        if (!validateAccountUC.execute(new ValidateAccountCreationUseCase.InputValues(userAccs, account)).getResult()) {
            return null;
        }
        account.setActive(true);
        account.setUser(currentUser);
        accountRepository.save(account);
        addToUser(account, currentUser);
        return account;
    }

    public void addToUser(Account account, User user) {
        user.getAccountList().add(account);
        userService.update(user);
    }

    public Account getAccountById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.showUser(auth.getName());
        return getAccUC
                .execute(new GetAccountByIdUseCase.InputValues(currentUser.getAccountList(), id))
                .getAccount()
                .orElse(null);
    }

}
