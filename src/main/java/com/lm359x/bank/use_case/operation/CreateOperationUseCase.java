package com.lm359x.bank.use_case.operation;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.Operation;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class CreateOperationUseCase extends UseCase<CreateOperationUseCase.InputValues, CreateOperationUseCase.OutputValues> {


    @Override
    public OutputValues execute(InputValues input) {
        Operation op = new Operation(input.from, input.to,input.amount);
        input.from.setBalance(input.from.getBalance()-input.amount);
        input.to.setBalance(input.to.getBalance()+input.amount);
        return new OutputValues(new Operation(input.from, input.to,input.amount));
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class InputValues implements UseCase.InputData{
        private Account from;
        private Account to;
        private Long amount;
    };

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final Operation op;
    }
}
