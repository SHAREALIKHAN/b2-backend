package com.example.b2.leave.repository;


import com.example.b2.leave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.lang.ScopedValue;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}