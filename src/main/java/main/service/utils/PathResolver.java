package main.service.utils;

import java.io.IOException;

public interface PathResolver {
    void createFilePathIfNotExist(String path) throws IOException;
}
