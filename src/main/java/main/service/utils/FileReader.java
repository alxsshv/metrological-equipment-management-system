package main.service.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
    public static byte[] readBytesFromFile(String fileStoragePath, String fileName) throws IOException {
        byte[] fileBytes;
        File file = new File(fileStoragePath + fileName);
        if (!file.exists()){
            throw new FileNotFoundException("Файл " + fileName + " не найден");
        }
        try {
            fileBytes = Files.readAllBytes(Path.of(file.toURI()));
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла " + fileName);
        }
        return fileBytes;
    }
}
