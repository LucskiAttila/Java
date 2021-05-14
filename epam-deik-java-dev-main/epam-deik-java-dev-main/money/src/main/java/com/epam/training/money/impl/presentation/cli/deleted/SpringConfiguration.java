package com.epam.training.money.impl.presentation.cli.deleted;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.config.PlaceholderConfigureSupport;
import org.springframework.support.PropertySourcesPlaceholderConfigure;
import org.springframework.context.annotation.ProperySources;
import org.springframework.context.annotation.ProperySource;


@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties")
})
public class SpringConfiguration {

    @Bean
    public static PlaceholderConfigureSpupport propertyConfigure() {
        return new PropertySourcesPlaceholderConfigure();
    }
}
