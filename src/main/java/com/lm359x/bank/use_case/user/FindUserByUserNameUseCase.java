package com.lm359x.bank.use_case.user;

import com.lm359x.bank.entity.User;
import com.lm359x.bank.use_case.UseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindUserByUserNameUseCase extends UseCase<FindUserByUserNameUseCase.InputValues, FindUserByUserNameUseCase.OutputValues>{


    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(input
                .getUsers()
                .stream()
                .filter(x->x.getUsername().equals(input.userName))
                .findFirst()
                .orElse(null));
    }

    @Getter
    @AllArgsConstructor
    public static class InputValues implements UseCase.InputData{
        private String userName;
        private List<User> users;
    }

    @AllArgsConstructor
    @Getter
    public static class OutputValues implements UseCase.OutputData{
        private final User user;
    }
}
