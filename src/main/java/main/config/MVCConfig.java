package main.config;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
@AllArgsConstructor
@Setter
public class MVCConfig implements WebMvcConfigurer {
    @Autowired
    private AppUploadPaths appUploadPaths;


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
                .addResourceLocations("file://"+ appUploadPaths.getImagesPath() + "/");
        registry.addResourceHandler("/storage/files/**")
                .addResourceLocations("file://"+ appUploadPaths.getDocumentsPath() + "/");

    }



}
