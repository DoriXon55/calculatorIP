package com.netcalc.IPCalculator.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private static final String UPLOAD_DIR = "uploads";

    static {
        new File(UPLOAD_DIR).mkdirs();
    }

    public static void writeFile(String fileName, String content) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                writer.write(content);
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas zapisywania pliku", e);
        }
    }

    public static Resource getFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas odczytu pliku", e);
        }
    }
}