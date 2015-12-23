package de.tim.schwalbe.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author tim.schwalbe
 */
@Configuration
public class MailConfig {

        @Autowired
        Environment env;

        //    <!-- SET default mail properties -->
        @Bean(name = "mailSender")
        public JavaMailSenderImpl getMailSender() {

                JavaMailSenderImpl jmsImpl = new JavaMailSenderImpl();
                jmsImpl.setHost(env.getProperty("mail.smtp.host"));
                jmsImpl.setPort(Integer.parseInt(env.getProperty("mail.smtp.port")));
                jmsImpl.setUsername(env.getProperty("mail.smtp.username"));
                jmsImpl.setPassword(env.getProperty("mail.smtp.password"));

                Properties properties = new Properties();
                properties.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
                properties.put("mail.smtp.starttls.enable", Boolean.parseBoolean(env.getProperty("mail.smtp.starttls.enable")));
                properties.put("mail.debug", Boolean.parseBoolean(env.getProperty("mail.debug")));

                jmsImpl.setJavaMailProperties(properties);

                return jmsImpl;
        }
}
