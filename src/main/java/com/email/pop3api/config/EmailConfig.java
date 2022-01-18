package com.email.pop3api.config;

import cn.hutool.core.util.ObjectUtil;
import com.email.pop3api.exception.BusinessException;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * 邮箱配置
 *
 * @author serical 2022-01-18 19:02:31
 */
@UtilityClass
public class EmailConfig {

    private final static Map<String, Properties> configMap = new HashMap<>();

    static {
        final Properties commonProperties = new Properties();
        commonProperties.put("mail.store.protocol", "pop3");
        commonProperties.put("mail.pop3.port", "995");
        commonProperties.put("mail.pop3.connectiontimeout", "10000");
        commonProperties.put("mail.pop3.timeout", "10000");
        commonProperties.put("mail.pop3.writetimeout", "10000");
        commonProperties.put("mail.pop3.ssl.enable", true);
        commonProperties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // gmail配置
        final Properties gmailProperties = ObjectUtil.clone(commonProperties);
        gmailProperties.put("mail.pop3.host", "pop.gmail.com");
        configMap.put("gmail.com", gmailProperties);

        // Yahoo配置
        final Properties yahooProperties = ObjectUtil.clone(commonProperties);
        yahooProperties.put("mail.pop3.host", "pop.mail.yahoo.com");
        configMap.put("yahoo.com", yahooProperties);

        // Mail配置
        final Properties mailProperties = ObjectUtil.clone(commonProperties);
        mailProperties.put("mail.pop3.host", "pop.mail.ru");
        configMap.put("mail.ru", mailProperties);

        // 21配置
        final Properties cnProperties = ObjectUtil.clone(commonProperties);
        cnProperties.put("mail.pop3.host", "pop.21cn.com");
        configMap.put("21cn.com", cnProperties);

        // Hotmail
        final Properties hotmailProperties = ObjectUtil.clone(commonProperties);
        hotmailProperties.put("mail.pop3.host", "pop-mail.outlook.com");
        configMap.put("hotmail.com", hotmailProperties);

        // Outlook
        final Properties outlookProperties = ObjectUtil.clone(commonProperties);
        outlookProperties.put("mail.pop3.host", "pop-mail.outlook.com");
        configMap.put("outlook.com", outlookProperties);

        // 新浪配置
        final Properties sinaProperties = ObjectUtil.clone(commonProperties);
        sinaProperties.put("mail.pop3.host", "pop.sina.com");
        configMap.put("sina.com", sinaProperties);

        // 163配置
        final Properties neteaseProperties = ObjectUtil.clone(commonProperties);
        neteaseProperties.put("mail.pop3.host", "pop.163.com");
        configMap.put("163.com", neteaseProperties);
    }

    /**
     * 根据邮箱后缀获取pop3配置
     *
     * @param suffix 邮箱后缀
     */
    public Properties getEmailConfig(String suffix) {
        return Optional.ofNullable(configMap.get(suffix)).orElseThrow(() -> new BusinessException("邮箱后缀不匹配"));
    }
}
