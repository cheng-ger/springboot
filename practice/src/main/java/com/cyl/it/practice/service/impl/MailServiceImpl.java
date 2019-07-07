package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-22
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /*简单的邮件*/
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        log.info("sendSimpleMail===mailFrom:{}", mailFrom);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(mailFrom);
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        log.info("sendHtmlMail start===mailFrom:{}", mailFrom);
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        messageHelper.setFrom(mailFrom);

        javaMailSender.send(message);
        log.info("sendSimpleMail end===");

    }

    @Override
    public void sendTemplateMail(String to, String subject, String content, String templateName) throws MessagingException {
        Context context = new Context();
        context.setVariable("myName", "cyl");
        String mailContext = templateEngine.process(templateName, context);
        sendHtmlMail(to, subject ,mailContext);
    }

    /**
     * 发送邮件
     *
     * @param subject        主题
     * @param body           内容
     * @param html           是否为html格式
     * @param from           发件人
     * @param cc             抄送人[多个]
     * @param to             收件人[多个]
     * @param attachPathList 附件路径
     */
    public void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to,
                         List<String> attachPathList) throws Exception {

            MimeMessage parentMimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(parentMimeMessage,true,"utf-8");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(new InternetAddress(from));
            mimeMessageHelper.setSentDate(new Date());

            // 设置收件人地址
            mimeMessageHelper.setTo(to);

            // 设置抄送人
            if (!StringUtils.isEmpty(cc)) {
                mimeMessageHelper.setCc(cc);
            }

            // 设置正文
            mimeMessageHelper.setText(body, html);

            // 设置附件方法一
            if (!CollectionUtils.isEmpty(attachPathList)) {
                Multipart multipart = new MimeMultipart();
                for (String attachPath : attachPathList) {
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    DataHandler dataHandler = new DataHandler(new FileDataSource(attachPath));
                    mimeBodyPart.setDataHandler(dataHandler);
                    mimeBodyPart.setFileName(dataHandler.getName());
                    multipart.addBodyPart(mimeBodyPart);
                }
                parentMimeMessage.setContent(multipart);
            }

//        // 添加附件二
//        if (valueMap.get("filePathList") != null) {
//            String[] filePathList = (String[]) valueMap.get("filePathList");
//            for(String filePath: filePathList) {
//                FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
//                String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
//                helper.addAttachment(fileName, fileSystemResource);
//            }
//        }

            // 正式发送邮件
            javaMailSender.send(parentMimeMessage);

    }



}
