package com.kh.EcomercebackEndspringboot.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreeMakerConfig {

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMakerConfig() {
        FreeMarkerConfigurationFactoryBean freeMakerConfig = new FreeMarkerConfigurationFactoryBean();
            freeMakerConfig.setTemplateLoaderPath("classpath:/templates");
        return freeMakerConfig;
    }
}
