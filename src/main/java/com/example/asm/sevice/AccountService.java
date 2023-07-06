package com.example.asm.sevice;

import com.example.asm.entity.Account;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Account saveorUpdate(Account account);

    Account findOne(String username);

    Account login(String username, String password);

    Page<Account> getAll(Pageable pageable);

    String guiMail(String emailNhan, String tieuDe, String noiDung) throws MessagingException;
}
