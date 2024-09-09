package main.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "storage")
public class AppUploadPaths {
    private String imagesPath;
    private String documentsPath;
    private String reportsPath;
    private String tempPath;
}
