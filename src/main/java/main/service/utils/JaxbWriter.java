package main.service.utils;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.config.AppUploadPaths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;

@Component
@Getter
@Setter
@Slf4j
public abstract class JaxbWriter {
    @Autowired
    private AppUploadPaths appUploadPaths;

    public void createFolderIfNotExist(){
        File uploadFolder = new File(appUploadPaths.getReportsPath());
        if (!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
    }
}
