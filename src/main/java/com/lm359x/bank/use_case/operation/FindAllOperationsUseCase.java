package com.lm359x.bank.use_case.operation;

import com.lm359x.bank.use_case.UseCase;

public class FindAllOperationsUseCase extends UseCase<FindAllOperationsUseCase.InputValues,FindAllOperationsUseCase.OutputValues>{


    @Override
    public OutputValues execute(InputValues input) {
        return null;
    }

    public static class InputValues implements UseCase.InputData{

    }

    public static class OutputValues implements UseCase.OutputData{

    }
}
