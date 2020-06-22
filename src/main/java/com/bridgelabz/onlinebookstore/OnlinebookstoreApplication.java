package com.bridgelabz.onlinebookstore;

import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class OnlinebookstoreApplication  {

    public static void main(String[] args) {
        SpringApplication.run(OnlinebookstoreApplication.class, args);
    }

}
