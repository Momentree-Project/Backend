package com.momentree.global.config;

import com.momentree.global.config.property.EmailProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailProperty emailProperty;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperty.getHost());
        mailSender.setPort(emailProperty.getPort());
        mailSender.setUsername(emailProperty.getUsername());
        mailSender.setPassword(emailProperty.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(emailProperty.getProperties().getSmtp().isAuth()));
        props.put("mail.smtp.starttls.enable", String.valueOf(emailProperty.getProperties().getSmtp().getStarttls().isEnable()));
        props.put("mail.smtp.timeout", String.valueOf(emailProperty.getProperties().getSmtp().getTimeout()));

        return mailSender;
    }

}
