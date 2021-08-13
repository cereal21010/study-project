package com.example.v2_board;

import com.example.v2_board.configuration.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
        {FileUploadProperties.class}
)
public class V2BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(V2BoardApplication.class, args);
    }

}
