package kz.runtime.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {
    @GetMapping(value = "/images", produces = "image/jpeg")
    public byte[] getFiles(@RequestParam (name = "file") String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (InputStream fileInputStream = Files.newInputStream(path)) {
           return fileInputStream.readAllBytes();
        }

    }

}
