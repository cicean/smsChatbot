package com.lambdanum.smsbackend.filesystem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
@Scope(scopeName = "singleton")
public class ResourceRepository {

    private ClassLoader classLoader = getClass().getClassLoader();

    public File getResourceFile(String resourcePath) {
        return new File(classLoader.getResource(resourcePath).getFile());
    }

    public FileInputStream getResourceFileInputStream(String resourcePath) {
        try {
            return new FileInputStream(classLoader.getResource(resourcePath).getFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File not found %s.",resourcePath));
        }
    }

}
