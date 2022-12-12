package com.lm359x.bank.use_case.account;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ValidateAccountCreationUseCase extends UseCase<ValidateAccountCreationUseCase.InputValues,
        ValidateAccountCreationUseCase.OutputValues>{

    @Override
    public OutputValues execute(InputValues input) {
        //some other checks may be added depending on concrete bank policies
        //here I only check if user has credit overdues
        Boolean result = true;
        Account failureReason =  input.getUserAccs()
                .stream()
                .filter(x->x.getAccountType()== AccountType.CREDIT&&x.getBalance()<0).findAny().orElse(null);
        result = Objects.isNull(failureReason);
        return new OutputValues(result);
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private List<Account> userAccs;
        private Account account;
    }
    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final Boolean result;
    }
}
