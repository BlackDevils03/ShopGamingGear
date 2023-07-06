package com.example.asm.sevice.impl;

import com.example.asm.entity.Account;
import com.example.asm.repository.AcountDAO;
import com.example.asm.sevice.AccountService;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class AccountServiceImpl implements AccountService {
    private final static String emailGui = "conghuy29062003@gmail.com";
    private final static String matKhau = "rzzppuyzrjugwvpp";
    @Autowired
    private AcountDAO acountDAO;

    @Override
    public Account saveorUpdate(Account account) {
        return acountDAO.save(account);
    }

    @Override
    public Account findOne(String username) {
        return acountDAO.getOne(username);
    }

    @Override
    public Account login(String username, String password) {
        return acountDAO.findAccountByUsernameAndPassword(username, password);
    }

    @Override
    public Page<Account> getAll(Pageable pageable) {
        return acountDAO.findAll(pageable);
    }

    public String guiMail(String emailNhan,
                          String tieuDe, String noiDung)
            throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailGui, matKhau);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailGui));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(emailNhan));

        // Tiêu đề
        message.setSubject(tieuDe);

        // Nội dung
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(noiDung, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
        return noiDung;
    }
}
