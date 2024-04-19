package com.likelion.oegaein.domain.email.service;

import com.likelion.oegaein.domain.email.dto.EmailMessage;
import com.likelion.oegaein.domain.email.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final String EMAIL_SEND_MSG_ERR = "메일 전송에 실패하였습니다.";
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public void sendMail(EmailMessage emailMessage, String type){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(setContext(emailMessage.getMessage(), type), true);
            javaMailSender.send(mimeMessage);
            log.info("Succeed send email");
        }catch (MessagingException e){
            log.error("Fail send mail");
            throw new EmailException(EMAIL_SEND_MSG_ERR);
        }
    }

    private String setContext(String message, String type){
        Context context = new Context();
        context.setVariable("message", message);
        return springTemplateEngine.process(type, context);
    }
}
