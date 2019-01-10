package com.huangge.email.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.logging.Logger;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    public void sendSimpleEmail(String to,String subject,String content){

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
    }
    public void senHtmlEmail(String to,String subject,String content) throws MessagingException {

        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content,true);
        mailSender.send(mimeMessage);
    }
    public void sendAttachmentMail(String to,String subject,String content,String filepath) throws MessagingException {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        FileSystemResource file=new FileSystemResource(new File(filepath));
        String fileName=file.getFilename();
        mimeMessageHelper.addAttachment(fileName,file);
        mailSender.send(mimeMessage);
    }
    public void sendInlineResourceMail(String to,String subject,String content,String rscPath,String rscId) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        FileSystemResource resource=new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,resource);
        mailSender.send(message);
    }
}
