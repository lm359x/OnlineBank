package com.lm359x.bank.infrastructure.service;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.infrastructure.jpa.AccountRepository;
import com.lm359x.bank.use_case.account.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CreateAccountUseCase createAccountUC;
    private final GetAllAccountsByUserUseCase getAllAccountsUC;

    private final ValidateAccountCreationUseCase validateAccountUC;
    private final GetAccountByIdUseCase getAccUC;
    private final BlockAccountUseCase blockAccUC;

    private final UserService userService;
    //subtitute userService to UC's????

    public AccountService(AccountRepository accountRepository, CreateAccountUseCase createAccountUC, GetAllAccountsByUserUseCase getAllAccountsUC, ValidateAccountCreationUseCase validateAccountUC, GetAccountByIdUseCase getAccUC, BlockAccountUseCase blockAccUC, UserService userService) {
        this.accountRepository = accountRepository;
        this.createAccountUC = createAccountUC;
        this.getAllAccountsUC = getAllAccountsUC;
        this.validateAccountUC = validateAccountUC;
        this.getAccUC = getAccUC;
        this.blockAccUC = blockAccUC;
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
    public Account createUserAccount(String accountType,String userName) {
        User currentUser = userService.showUser(userName);
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

    public Account getAccountById(Long id){
        List<Account> accountList = accountRepository.findAll();
        return getAccUC
                .execute(new GetAccountByIdUseCase.InputValues(accountList,id))
                .getAccount()
                .orElse(null);
    }

    public Account getUserAccountById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.showUser(auth.getName());
        return getAccUC
                .execute(new GetAccountByIdUseCase.InputValues(currentUser.getAccountList(), id))
                .getAccount()
                .orElse(null);
    }

    public Account blockAccount(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.showUser(auth.getName());
        Optional<Account> accountToBlockOptional = getAccUC.execute(new GetAccountByIdUseCase.InputValues(currentUser.getAccountList(),id)).getAccount();
        if (accountToBlockOptional.isPresent()){
            Account accountTOBlock = accountToBlockOptional.get();
            accountTOBlock = blockAccUC.execute(new BlockAccountUseCase.InputValues(accountTOBlock)).getAccount();
            accountRepository.save(accountTOBlock);
            return accountTOBlock;
        }
        return null;
    }

    public Account updateAccount(Long id,Account updatedData){
        Account account = accountRepository.getById(id);
        account.setBalance(updatedData.getBalance());
        return accountRepository.save(account);
    }

}
