package com.util;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.swing.*;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
//    private JavaMailSenderImpl mailSender;

    @Autowired
    VelocityEngine velocityEngine;

    public boolean sendWithHtmlTemplate(String to, String subject, String template, Map<String, Object> model) {
        try {

            String from = "zhifougf@163.com";
            String smtpHost = "smtp.163.com";//smtp.qq.com/smtp.sohu.com
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
            props.setProperty("mail.smtp.port", "25"); // 请求认证，参数名称与具体实现有关
            props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关
            // 创建Session实例对象
            Session session = Session.getDefaultInstance(props);
            // 创建MimeMessage实例对象
            MimeMessage message = new MimeMessage(session);

            String result = VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, template, "utf8", model);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "utf8");
            // 设置发件人
            mimeMessageHelper.setFrom(from);
            // 设置收件人
            mimeMessageHelper.setTo(to);
            // 设置邮件主题
            mimeMessageHelper.setSubject(subject);
            // 设置纯文本内容的邮件正文
            mimeMessageHelper.setText(result, true);
            // 获取Transport对象
            Transport transport = session.getTransport("smtp");
            // 第2个参数需要填写的是QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？
            transport.connect(from, "zhifouceshi1");
            // 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;

        } catch (Exception e) {
            logger.error("邮件发送失败"+e.getMessage());
            return false;
        }
    }



    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
