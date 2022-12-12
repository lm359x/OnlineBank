package com.lm359x.bank.use_case.operation;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.AccountType;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidateOperationUseCase extends UseCase<ValidateOperationUseCase.InputValues,
        ValidateOperationUseCase.OutputValues>{

    @Override
    public OutputValues execute(InputValues input) {
        if(Objects.isNull(input.from)|| Objects.isNull(input.to))
            return new OutputValues(false);
        if(!input.from.getActive()||!input.to.getActive())
            return new OutputValues(false);
        if(input.from.getBalance()<input.amount&&input.from.getAccountType().equals(AccountType.DEBIT))
            return new OutputValues(false);
        return new OutputValues(true);
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private Account from;
        private Account to;
        private Long amount;
    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final Boolean result;
    }
}
