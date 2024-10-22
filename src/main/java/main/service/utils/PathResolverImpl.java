package main.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class PathResolverImpl implements PathResolver {

    public void createFilePathIfNotExist(String path) throws IOException {
        String[] folders = path.split("/");
        StringBuilder currentPath = new StringBuilder();
        for (String folder : folders){
            currentPath.append(folder).append("/");
            createFolderIfNotExist(String.valueOf(currentPath));
        }
    }

    public void createFolderIfNotExist(String folderPath) throws IOException {
        File directory = new File(folderPath);
        if (!directory.exists() && !directory.getPath().isEmpty()) {
            log.info("Создание отсутствующей директории {}", directory.getAbsolutePath());
            boolean isCreated = directory.mkdir();
            if (!isCreated){
                throw new IOException("Ошибка создания директории " +  directory.getPath());
            }
        }
    }
}
