package com.lm359x.bank.use_case;

public abstract class UseCase<I extends UseCase.InputData, O extends UseCase.OutputData> {
    public abstract O execute(I input);

    public interface InputData{}
    public interface OutputData{}

}
