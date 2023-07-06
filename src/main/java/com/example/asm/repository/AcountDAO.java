package com.example.asm.repository;

import com.example.asm.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcountDAO extends JpaRepository<Account, String> {
    Account findAccountByUsernameAndPassword(String username, String passwprd);

    Account findAccountByUsername(String username);
}
