package com.johurulislam.main.service;

import com.johurulislam.main.model.Authority;
import com.johurulislam.main.model.User;
import com.johurulislam.main.repo.AuthorityRepo;
import com.johurulislam.main.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepo, AuthorityRepo authorityRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public User findByUsername(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }
    public User saveUser(User user) {
        Authority authority = authorityRepo.findByAuthorityName("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public User saveAdmin(User user) {
        Authority authority = authorityRepo.findByAuthorityName("ROLE_ADMIN");
        user.setAuthorities(Set.of(authority));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

}
