package com.lm359x.bank.infrastructure.jpa;

import com.lm359x.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User getUserByUsername(String username);


}
