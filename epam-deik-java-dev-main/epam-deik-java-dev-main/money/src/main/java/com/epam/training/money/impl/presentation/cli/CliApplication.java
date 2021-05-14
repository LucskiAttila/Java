package com.epam.training.money.impl.presentation.cli;

import java.io.IOException;

import com.epam.training.money.impl.presentation.cli.configuration.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(scanBasePackages = "com.epam.training.money")
public class CliApplication {

    public static void main(String[] args) throws IOException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
//        //ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.training.money");
//        CliInterpreter cliInterpreter = context.getBean(CliInterpreter.class);
//        cliInterpreter.run();
        SpringApplication.run(CliApplication.class, args);
    }
}
