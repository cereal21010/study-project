package com.example.v2_board.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {

    private String location;

}
