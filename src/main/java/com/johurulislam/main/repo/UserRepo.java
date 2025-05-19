package com.johurulislam.main.repo;

import com.johurulislam.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
}
