package com.rabo.customer.statement.processor.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.rabo.customer.statement.processor")
@PropertySource("classpath:environment.properties")
public class StatementProcessorConfigurer extends WebMvcConfigurerAdapter {

	@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
	
	@Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver theResolver = new CommonsMultipartResolver();
        theResolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
        theResolver.setMaxUploadSizePerFile(5242880);
        return theResolver;
    }
}
