package com.lm359x.bank.use_case.account;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class BlockAccountUseCase extends UseCase<BlockAccountUseCase.InputValues,
        BlockAccountUseCase.OutputValues> {

    @Override
    public OutputValues execute(InputValues input) {
        input.account.setActive(false);
        return new OutputValues(input.account);
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private Account account;
    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final Account account;
    }
}
