package com.fengjie.courseprogram.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author fengjie
 * @date 2019:04:14
 */
public class EmailClient {

    private String from = "921480587@qq.com";

    private String host = "smtp.qq.com";

    private static volatile EmailClient emailClient;

    private EmailClient() {
    }

    public static EmailClient getInstance() {
        if (emailClient == null) {
            synchronized (String.class) {
                if (emailClient == null) {
                    emailClient = new EmailClient();
                    return emailClient;
                }
            }
        }
        return emailClient;
    }

    private Properties getDefaultProperties() {
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", host);
        return properties;
    }

    public synchronized boolean sendTextEmail(String to, String sub, String text) {
        Session session = Session.getDefaultInstance(this.getDefaultProperties());

        try {
            // 创建默认的 MimeMessage 对象。
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set Subject: 头字段
            message.setSubject(sub);

            message.setText(text);

            Transport transport = session.getTransport();
            transport.connect(from, "utdlrdcidisnbfha");
            transport.sendMessage(message, new InternetAddress[]{new InternetAddress(to)});
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
