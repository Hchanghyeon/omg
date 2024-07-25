package com.chang.omg.domain.mail.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chang.omg.domain.mail.service.dto.EmailDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(final EmailDetails emailDetails) {
        final SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailDetails.receiver());
        message.setSubject(emailDetails.subject());
        message.setText(emailDetails.message());

        mailSender.send(message);
    }
}
