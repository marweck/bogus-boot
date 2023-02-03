package com.minone.bogus.config;

import com.minone.bogus.web.SubnetFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class Configuration {

    @Bean
    public FilterRegistrationBean<SubnetFilter> subnetFilter() {

        FilterRegistrationBean<SubnetFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new SubnetFilter());
        registration.addUrlPatterns("/health");

        return registration;
    }
}
