package main.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MVCConfig implements WebMvcConfigurer {
    @Value("${upload.images.path}")
    private String imageUploadFolder;
    @Value("${upload.files.path}")
    private String fileUploadFolder;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/images/favicon.ico");
        registry.addResourceHandler("/storage/images/**")
                .addResourceLocations("file://"+imageUploadFolder + "/");
        registry.addResourceHandler("/storage/files/**")
                .addResourceLocations("file://"+fileUploadFolder + "/");

    }

}
