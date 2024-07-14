package kz.runtime.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class FileController {
    @GetMapping(value = "/images", produces = "image/jpeg")
    public byte[] getFiles(@RequestParam (name = "file") String filePath) throws IOException {
        String basePath = "C:\\Users\\Kasht\\IdeaProjects\\shop\\";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] arr = fileInputStream.readAllBytes();

        return arr;
    }

}
