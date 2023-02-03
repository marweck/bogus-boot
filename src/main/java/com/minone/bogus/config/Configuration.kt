package com.minone.bogus.config

import com.minone.bogus.web.SubnetFilter
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
class Configuration {
    @Bean
    fun subnetFilter(): FilterRegistrationBean<SubnetFilter> =
        FilterRegistrationBean<SubnetFilter>().apply {
            this.filter = SubnetFilter()
            this.addUrlPatterns("/health")
        }
}