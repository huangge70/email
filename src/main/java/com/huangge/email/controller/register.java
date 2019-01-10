package com.huangge.email.controller;

import com.huangge.email.domain.User;
import com.huangge.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Contended;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class register {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmailService emailService;
    @RequestMapping(value = "/insert",method =RequestMethod.POST)
    public void insertUser(User user) throws MessagingException {
        user.setActive(0);
        String code=UUID.randomUUID().toString().replace("-","");

        sendEmail(user.getEmail(),code);
    }
    public void sendEmail(String to,String code) throws MessagingException {
        String content="<h3>点击下面链接激活邮箱</h3><a href='http://localhost:8080/register/deal?code="+code+"'>"+code+"</a>";
        emailService.senHtmlEmail(to,"激活邮箱",content);

    }
    @RequestMapping(value = "/deal",method = RequestMethod.GET)
    public void deal(HttpServletRequest request){
        String code=request.getParameter("code");
        System.out.print(code);
    }
}
