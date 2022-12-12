package com.lm359x.bank.use_case.account;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase extends UseCase<CreateAccountUseCase.InputValues, CreateAccountUseCase.OutputValues> {

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(new Account(0L,input.type));
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private User user;
        private AccountType type;

    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final Account account;
    }
}
