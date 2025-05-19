package com.johurulislam.main.repo;

import com.johurulislam.main.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    Authority findByAuthorityName(String authorityName);
}
