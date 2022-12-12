package com.lm359x.bank.use_case.operation;

import com.lm359x.bank.entity.Account;
import com.lm359x.bank.entity.Operation;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllOperationsByAccountUseCase extends UseCase<GetAllOperationsByAccountUseCase.InputValues,
        GetAllOperationsByAccountUseCase.OutputValues>{

    @Override
    public OutputValues execute(InputValues input) {
        List<Operation> operationList = new ArrayList<>();
        operationList.addAll(input.acc.getInOperations());
        operationList.addAll(input.acc.getOutOperations());
        return new OutputValues(operationList
                .stream()
                .sorted(Comparator.comparing(Operation::getDateTime))
                .collect(Collectors.toList()));
    }

    @AllArgsConstructor
    @Getter
    public static class InputValues implements UseCase.InputData{
        private Account acc;
    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final List<Operation> operationList;
    }
}
