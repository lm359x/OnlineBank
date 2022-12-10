package com.lm359x.bank.use_case.account;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.User;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAccountsByUserUseCase extends UseCase<GetAllAccountsByUserUseCase.InputValues,
        GetAllAccountsByUserUseCase.OutputValues>{

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(input.accounts
                .stream()
                .filter(x->x.getUser().equals(input.getUser()))
                .collect(Collectors.toList()));
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private List<Account> accounts;
        private User user;
    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final List<Account> accounts;
    }
}
