package com.email.pop3api.service;

import cn.hutool.core.util.NumberUtil;
import com.email.pop3api.config.EmailConfig;
import com.email.pop3api.exception.BusinessException;
import com.email.pop3api.params.EmailParam;
import com.sun.mail.pop3.POP3SSLStore;
import jakarta.mail.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

/**
 * pop3接收邮件服务
 *
 * @author serical 2022-01-18 07:41:43
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    /**
     * pop3协议通过邮箱和授权码登录获取最后一封邮件
     *
     * @param param 参数
     */
    public Object getLastEmail(EmailParam param) {
        POP3SSLStore store = null;
        Folder folder = null;
        try {
            final String suffix = param.getUserEmail().split("@")[1];
            final Properties properties = EmailConfig.getEmailConfig(suffix);

            final Session session = Session.getDefaultInstance(properties);
            // session.setDebug(true);

            final URLName urlName = new URLName("pop3", properties.getProperty("mail.pop3.host"),
                    NumberUtil.parseInt(properties.getProperty("mail.pop3.port")), "",
                    param.getUserEmail(), param.getAuthorizeCode());
            store = new POP3SSLStore(session, urlName);
            store.connect();

            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            final int count = folder.getMessageCount();
            final Message message = folder.getMessage(count);
            final String body = getBody(message);

//            System.out.println("邮件主题：" + message.getSubject());
//            System.out.println("发件人：");
//            showAddress(message.getFrom());
//            System.out.println("收件人：");
//            showAddress(message.getRecipients(Message.RecipientType.TO));
//            System.out.println("抄送人：");
//            showAddress(message.getRecipients(Message.RecipientType.CC));
//            System.out.println("密送人：");
//            showAddress(message.getRecipients(Message.RecipientType.BCC));
//            System.out.println("邮件内容：" + body);

            return body;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        } finally {
            if (folder != null) {
                try {
                    folder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showAddress(Address[] address) {
        if (null == address) {
            return;
        }
        for (Address add : address) {
            System.out.println(add.toString());
        }
    }

    private String getBody(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/*")) {
            return part.getContent().toString();
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String body = getBody(bodyPart);
                if (!body.isEmpty()) {
                    return body;
                }
            }
        }
        return "";
    }
}
