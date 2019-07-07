package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.PracticeApplicationTests;
import com.cyl.it.practice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.File;

@Slf4j
public class MailServiceImplTest extends PracticeApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public  void sendSimpleMail(){
        mailService.sendSimpleMail("2911317064@qq.com","测试简单文本文件","HELLO_WORLD MAIL");

    }

    @Test
    public  void sendHtmlMail() throws MessagingException {
        String to = "2911317064@qq.com";
        String subject = "测试HTML文件";
        String content = "<html><body>" +
                "<div>这是一个HTML邮件:接收方" +  to +
                "</div>" +
                "</body></html>";

        mailService.sendHtmlMail(to,subject,content);

    }

    @Test
    public  void sendTemplateMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("myName", "cheng_ger");
        log.info("context:{}", context.getVariable("myName"));
        String to = "2911317064@qq.com";
        String subject = "测试tempalte邮件";
        String mailContext = templateEngine.process("emailTemplate", context);
        log.info("sendTemplateMail==mailContext:{}", mailContext);
        mailService.sendHtmlMail(to,subject,mailContext);

    }

    @Test
    public  void subStringLast(){

        String path = "D" + File.separator + "idea" + File.separator + "test.html";

        log.info("path:{}", path);

        String repath = "D:\\idea\\test.html";

        String fileName = path.substring(path.lastIndexOf(File.separator));
        String reFilename = repath.substring(repath.lastIndexOf(File.separator) + 1);
        log.info("fileName:{}====reFilename:{}", fileName,reFilename );

    }




}