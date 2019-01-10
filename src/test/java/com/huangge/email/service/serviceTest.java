package com.huangge.email.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class serviceTest {
    @Resource
    EmailService emailService;
    @Resource
    TemplateEngine templateEngine;

    @Test
    public void Test(){
        emailService.sendSimpleEmail("17801093123@163.com","这是第一封邮件","大家好，这是我的第一封邮件");
    }

    @Test
    public void Test2() throws MessagingException {
        String content="<html>\n"+
                "<body>\n"+
                "<h3>hello,world</h3>\n"+
                "</body>\n"+
                "</html>";
        emailService.senHtmlEmail("17801093123@163.com","HTML邮件",content);
    }

    @Test
    public void Test3() throws MessagingException {
        String filePath="C:\\Users\\huangge70\\Desktop\\aaa.jpg";
        String content="<html>\n"+
                "<body>\n"+
                "<h3>hello,world</h3>\n"+
                "</body>\n"+
                "</html>";
        emailService.sendAttachmentMail("17801093123@163.com","带附件的邮件",content,filePath);
    }

    @Test
    public void Test4() throws MessagingException {
        String imagePath="C:\\Users\\huangge70\\Desktop\\aaa.jpg";
        String rscId="001";
        String content="<html>\n"+
                "<body>\n"+
                "<h3>hello,world</h3>\n"+
                "<img src='cid:"+rscId+"'></img>\n"+
                "</body>\n"+
                "</html>";
        emailService.sendInlineResourceMail("17801093123@163.com","带图片的邮件",content,imagePath,rscId);
    }

    @Test
    public void Test5() throws MessagingException {
        Context context=new Context();
        context.setVariable("id","006");
        String emailContent=templateEngine.process("emailTemplate",context);
        emailService.senHtmlEmail("17801093123@163.com","模板邮件",emailContent);
    }
}
