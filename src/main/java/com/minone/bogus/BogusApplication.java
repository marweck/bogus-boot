package com.minone.bogus;

import com.minone.bogus.web.SubnetFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BogusApplication {

    public static void main(String[] args) {
        SpringApplication.run(BogusApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<SubnetFilter> subnetFilter() {

        FilterRegistrationBean<SubnetFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new SubnetFilter());
        registration.addUrlPatterns("/health");

        return registration;
    }
}