package com.lm359x.bank.use_case.account;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetAccountByIdUseCase extends UseCase<GetAccountByIdUseCase.InputValues,
        GetAccountByIdUseCase.OutputValues>{

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(input.accountList.stream().filter(x->x.getId().equals(input.id)).findAny());
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private List<Account> accountList;
        private Long id;
    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final Optional<Account> account;
    }
}
