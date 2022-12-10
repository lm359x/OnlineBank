package com.lm359x.bank.infrastructure.service;

import com.lm359x.bank.entity.User;
import com.lm359x.bank.infrastructure.jpa.UserRepository;
import com.lm359x.bank.use_case.user.FindUserByUserNameUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final FindUserByUserNameUseCase findUser;

    public UserService(UserRepository userRepository, FindUserByUserNameUseCase findUser) {
        this.userRepository = userRepository;
        this.findUser = findUser;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public User showUser(String userName){
        User user = findUser.execute(new FindUserByUserNameUseCase.InputValues(userName,userRepository.findAll())).getUser();
        return user;
    }
}
