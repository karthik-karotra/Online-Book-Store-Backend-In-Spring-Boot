package com.bridgelabz.onlinebookstore.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private String uploadDirectory;
    private String jwtSecret;
    private Integer jwtLoginExpiration;

}
