package com.example.v2_board;

import com.example.v2_board.configuration.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
@EnableConfigurationProperties(
        {FileUploadProperties.class}
)
public class V2BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(V2BoardApplication.class, args);
    }

}
