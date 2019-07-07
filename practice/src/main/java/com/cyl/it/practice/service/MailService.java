package com.cyl.it.practice.service;

import javax.mail.MessagingException;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-22
 */
public interface MailService {


    /*发送简单的文本邮件*/
    void sendSimpleMail(String to , String subject , String content);


    /*发送HTML文件*/
    void sendHtmlMail(String to , String subject , String content) throws MessagingException;

    /*发送HTML文件*/
    void sendTemplateMail(String to , String subject , String content , String templateName) throws MessagingException;


}
