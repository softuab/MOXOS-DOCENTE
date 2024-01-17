package com.moxos.uab;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.moxos.uab.servlet.CaptchaGenServlet;

import java.util.Properties;

@Configuration
public class ServletConfig {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ServletRegistrationBean<CaptchaGenServlet> helloServletRegistrationBean() {
        ServletRegistrationBean<CaptchaGenServlet> registrationBean = new ServletRegistrationBean<>(
                new CaptchaGenServlet());
        registrationBean.addUrlMappings("/captcha.jpg");
        return registrationBean;
    }


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5242880); // 5MB
        return multipartResolver;
    }
}
